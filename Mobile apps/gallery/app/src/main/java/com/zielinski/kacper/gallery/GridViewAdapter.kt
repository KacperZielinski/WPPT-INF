package com.zielinski.kacper.gallery

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import com.zielinski.kacper.gallery.model.Photo
import java.io.File

class GridViewAdapter(context: Context, var data: ArrayList<Photo>, var width: Int, var height: Int) :
        ArrayAdapter<Photo>(context, R.layout.grid_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.grid_item, parent, false)

            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view!!.tag as ViewHolder
        }
        viewHolder.gridImageView.layoutParams = LinearLayout.LayoutParams(width, width/2)
        viewHolder.gridImageView.setImageURI(Uri.fromFile(File(data[position].filePathName)))

        return view!!
    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var gridImageView = view.findViewById<ImageView>(R.id.grid_item_image_view)!!
    }
}