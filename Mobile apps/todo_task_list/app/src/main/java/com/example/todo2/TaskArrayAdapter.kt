package com.example.todo2

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class TaskArrayAdapter(context: Context, var data: ArrayList<Task>) :
        ArrayAdapter<Task>(context, R.layout.list_item, data) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    private val timeFormatter = SimpleDateFormat("HH:mm")
    private val primaryDarkColor = Color.parseColor("#a30000")

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder : ViewHolder
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item, parent, false)

            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view!!.tag as ViewHolder
        }

        if(data[position].taskType == TaskType.DEADLINE) {
            viewHolder.taskImageView.setImageResource(R.drawable.ic_deadline)
            setViewsColor(viewHolder, primaryDarkColor)
        } else {
            viewHolder.taskImageView.setImageResource(R.drawable.ic_check_circle_black)
            setViewsColor(viewHolder, Color.BLACK)
        }

        val dateString = dateFormatter.format(data[position].date)
        val timeString = timeFormatter.format(Date((data[position].date.time)))

        viewHolder.dateTextView.text = dateString
        viewHolder.timeTextView.text = timeString
        viewHolder.taskContentTextView.text = data[position].content
        viewHolder.priorityView.text = data[position].priority.toString()

        return view!!
    }

    private fun setViewsColor(viewHolder: ViewHolder, color: Int) {
        viewHolder.dateTextView.setTextColor(color)
        viewHolder.timeTextView.setTextColor(color)
        viewHolder.taskContentTextView.setTextColor(color)
        viewHolder.priorityView.setTextColor(color)
    }

    private class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var taskImageView = view.findViewById<ImageView>(R.id.task_image)!!
        var dateTextView = view.findViewById<TextView>(R.id.date)!!
        var timeTextView = view.findViewById<TextView>(R.id.time)!!
        var priorityView = view.findViewById<TextView>(R.id.priority)!!
        var taskContentTextView = view.findViewById<TextView>(R.id.task_content)!!
    }
}