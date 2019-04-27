package com.zielinski.kacper.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zielinski.kacper.gallery.details.DetailsActivity
import com.zielinski.kacper.gallery.model.Photo
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private val photoListJsonKey = "galleryAppPhotoList"
    private val fileList: ArrayList<File> = ArrayList()
    private var photoDetailsList: ArrayList<Photo> = ArrayList()
    private val fullPath = File(Environment.getExternalStorageDirectory().absolutePath + "/MyGallery")
    private lateinit var adapter: GridViewAdapter
    private var filePathName = ""
    private var description = ""
    private var rating = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoDetailsList = getPhotoDetailsListFromJson()
        loadImagesFromDirectory()
        loadImagesIntoGridView()

        val mFab = findViewById<FloatingActionButton>(R.id.fab)
        mFab.setOnClickListener {
            val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (pictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(pictureIntent, 1337)
            }
        }
    }

    private fun getPhotoDetailsByPath(path: String): Int {
        photoDetailsList.forEachIndexed { index, photo ->
            if(photo.filePathName == path) {
                return index
            }
        }
        return -1
    }

    private fun getPhotoDetailsListFromJson(): ArrayList<Photo> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val json = prefs.getString(photoListJsonKey, null) ?: return ArrayList()
        val type = object : TypeToken<ArrayList<Photo>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun loadImagesFromDirectory() {
        val listAllFiles = fullPath.listFiles()
        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpg")) {
                    fileList.add(currentFile.absoluteFile)
                }
            }
        }
    }

    private fun loadImagesIntoGridView() {
        fileList.forEach { path ->
            if(getPhotoDetailsByPath(path.absolutePath) == -1) {
                val newPhotoDetails = Photo(path.absolutePath, "", 0.0)
                photoDetailsList.add(newPhotoDetails)
            }
        }

        adapter = GridViewAdapter(this, photoDetailsList,
                windowManager.defaultDisplay.width, windowManager.defaultDisplay.height)

        grid_view.adapter = adapter
        grid_view.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetailsActivity::class.java)
            val photoDetails = photoDetailsList[position]
            intent.putExtra("filePathName", photoDetails.filePathName)
            intent.putExtra("description", photoDetails.description)
            intent.putExtra("rating", photoDetails.rating)
            startActivityForResult(intent, 8080)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 8080 && data != null) {
            val extras = data.extras
            val filePathName = extras!!.getString("filePathName")
            val description = extras.getString("description")
            val rating = extras.getFloat("rating")

            val photoDetailsIndex = getPhotoDetailsByPath(filePathName)
            if (photoDetailsIndex != -1) {
                val photo = photoDetailsList[photoDetailsIndex]
                photo.description = description
                photo.rating = rating.toDouble()
            }
        } else if(requestCode == 1337 && resultCode == Activity.RESULT_OK) {
            if (data?.extras != null) {
                val uuid = UUID.randomUUID().toString()
                val file = File(fullPath, "CAMERA_$uuid.jpg")
                val fOut: OutputStream = FileOutputStream(file)

                val pictureBitmap: Bitmap = data.extras.get("data") as Bitmap
                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
                fOut.flush()
                fOut.close()

                MediaStore.Images.Media.insertImage(contentResolver, file.absolutePath, file.name, file.name)

                photoDetailsList.add(Photo(file.absolutePath, "", 0.0))
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
//        destroyList()
        savePhotoListToJson()
        super.onDestroy()
    }

    private fun destroyList() {
        photoDetailsList = ArrayList()
    }

    private fun savePhotoListToJson() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val json = Gson().toJson(photoDetailsList)
        editor.putString(photoListJsonKey, json)
        editor.apply()
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("filePathName", filePathName)
        savedInstanceState.putString("description", description)
        savedInstanceState.putDouble("rating", rating)
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        filePathName = savedInstanceState.getString("filePathName")
        description = savedInstanceState.getString("description")
        rating = savedInstanceState.getDouble("rating")
    }
}
