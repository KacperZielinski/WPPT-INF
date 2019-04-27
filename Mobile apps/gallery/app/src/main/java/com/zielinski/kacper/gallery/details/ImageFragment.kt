package com.zielinski.kacper.gallery.details

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zielinski.kacper.gallery.R
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File

class ImageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_image_imageview.setImageURI(Uri.fromFile(File(activity!!.intent.extras.getString("filePathName"))))
    }
}
