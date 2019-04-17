package com.example.todo2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.Calendar
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private val listOfTasksKey = "listOfTasksTodo2App"
    private var listOfTasks = ArrayList<Task>()
    private lateinit var taskAdapter : TaskArrayAdapter
    private lateinit var newTaskLayout : View
    private lateinit var dialog: AlertDialog
    private lateinit var prioritySpinner: Spinner
    private lateinit var taskTypeSpinner: Spinner
    private var lastChosenDate: String = "2019-01-01"
    private var lastChosenTime: String = "00:00"
    private var sortByDateAscOrder = true
    private var sortByPriorityAscOrder = true
    private var sortByTaskTypeAscOrder = true

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.sort_by_date -> {

                if(sortByDateAscOrder) {
                    listOfTasks.sortWith(compareBy({it.date}, {it.priority}))
                } else {
                    listOfTasks.sortWith(compareByDescending<Task> {it.date}
                            .thenByDescending { it.priority })
                }

                sortByDateAscOrder = !sortByDateAscOrder
                taskAdapter.notifyDataSetChanged()
                return@OnNavigationItemSelectedListener true
            }
            R.id.sort_by_task_type -> {

                if(sortByTaskTypeAscOrder) {
                    listOfTasks.sortWith(compareBy({it.taskType}, {it.priority}, { it.date }))
                } else {
                    listOfTasks.sortWith(compareByDescending<Task> {it.taskType}
                            .thenByDescending { it.priority }
                            .thenBy { it.date })
                }

                sortByTaskTypeAscOrder = !sortByTaskTypeAscOrder
                taskAdapter.notifyDataSetChanged()
                return@OnNavigationItemSelectedListener true
            }
            R.id.sort_by_priority -> {

                if(sortByPriorityAscOrder) {
                    listOfTasks.sortWith(compareBy({it.priority}, {it.date}))
                } else {
                    listOfTasks.sortWith(compareByDescending<Task> {it.priority}
                            .thenBy { it.date })
                }

                sortByPriorityAscOrder = !sortByPriorityAscOrder
                taskAdapter.notifyDataSetChanged()
                return@OnNavigationItemSelectedListener true
            }
            R.id.add_new_task -> {
                dialog.show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newTaskLayout = layoutInflater.inflate(R.layout.new_task, null, false)
    }

    override fun onStart() {
        super.onStart()
        listOfTasks = getArrayList(listOfTasksKey)

        setAdapters()
        setListeners()
        createAlertDialog()

        if(!listOfTasks.isEmpty()) {
            empty_view.visibility = View.GONE
        }
    }

    override fun onStop() {
        saveArrayList(listOfTasks, listOfTasksKey)
        super.onStop()
    }

    private fun getArrayList(key: String): ArrayList<Task> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val json = prefs.getString(key, null) ?: return ArrayList<Task>()
        val type = object : TypeToken<ArrayList<Task>>() {

        }.type
        return Gson().fromJson(json, type)
    }


    private fun saveArrayList(list: ArrayList<Task>, key: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val json = Gson().toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    private fun setAdapters() {
        taskTypeSpinner = newTaskLayout.findViewById(R.id.new_task_type_spinner)
        taskTypeSpinner.adapter = ArrayAdapter<TaskType>(this, android.R.layout.simple_spinner_item, TaskType.values())

        prioritySpinner = newTaskLayout.findViewById(R.id.new_task_priority_spinner)
        prioritySpinner.adapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, arrayOf(1, 2, 3, 4, 5, 6, 7, 8))

        taskAdapter = TaskArrayAdapter(this, listOfTasks)
        list_view.adapter = taskAdapter

    }

    private fun setListeners() {
        list_view.setOnItemLongClickListener { parent, view, position, id ->
            listOfTasks.removeAt(position)
            taskAdapter.notifyDataSetChanged()

            if(listOfTasks.isEmpty()) {
                empty_view.visibility = View.VISIBLE
            }
            true
        }

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun createAlertDialog() {
        if(newTaskLayout.parent != null) {
            return
        }

        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle("Add new task")
        builder.setMessage("Enter new task:")
        builder.setView(newTaskLayout)
        val editText = newTaskLayout.findViewById<EditText>(R.id.new_task_edit_text)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

        builder.setPositiveButton("ADD") { _, _ ->
            when {
                editText.text.toString().isEmpty() ->
                    Toast.makeText(this, "Task cannot be empty!", Toast.LENGTH_LONG).show()
                editText.text.length > 255 ->
                    Toast.makeText(this, "Task description too long!", Toast.LENGTH_LONG).show()
                else -> {
                    if(listOfTasks.isEmpty()) {
                        empty_view.visibility = View.GONE
                    }

                    listOfTasks.add(Task(formatter.parse("$lastChosenDate $lastChosenTime"),
                                    editText.text.toString(),
                                    TaskType.valueOf(taskTypeSpinner.selectedItem.toString()),
                                    prioritySpinner.selectedItem.toString().toInt()))

                    editText.setText("")
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }

        builder.setNeutralButton("CANCEL") { _, _ -> }

        registerAlertDialogDateHandler()
        dialog = builder.create()
    }

    private fun registerAlertDialogDateHandler() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
            v, hourOfDay, minute ->

            lastChosenTime = "$hourOfDay:$minute"
        }, hours, minutes, true)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
            view, chosenYear, monthOfYear, dayOfMonth ->

            val correctedMonth = monthOfYear + 1
            lastChosenDate = "$chosenYear-$correctedMonth-$dayOfMonth"
            timePickerDialog.show()
        }, year, month, day)

        val imageButton = newTaskLayout.findViewById<ImageView>(R.id.new_task_date_time_button)
        imageButton.setOnClickListener {
            datePickerDialog.show()
        }
    }
}
