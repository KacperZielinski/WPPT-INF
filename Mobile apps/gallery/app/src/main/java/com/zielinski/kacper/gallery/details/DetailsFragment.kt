package com.zielinski.kacper.gallery.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zielinski.kacper.gallery.MainActivity
import com.zielinski.kacper.gallery.R
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private var isEditable: Boolean = false
    private var filePathName: String = ""
    private var description: String = ""
    private var rating: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var extras = savedInstanceState
        if(savedInstanceState == null) {
            extras = activity!!.intent.extras
        } else {
            isEditable = savedInstanceState.getBoolean("isEditable")
            val editedText = savedInstanceState.getString("editedText")

            if(isEditable) {
                fragment_details_description_edit_text.setText(editedText)
                fragment_details_description_edit_text.visibility = View.VISIBLE
                fragment_details_description.visibility = View.GONE
                save_button.isEnabled = false
            }
        }

        filePathName = extras!!.getString("filePathName")
        description = extras.getString("description")
        rating = extras.getDouble("rating")
        fragment_details_description.text = description
        fragment_details_ratingbar.rating = rating.toFloat()

        save_button.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("filePathName", filePathName)
            intent.putExtra("description", fragment_details_description.text.toString())
            intent.putExtra("rating", fragment_details_ratingbar.rating)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }

        edit_button.setOnClickListener {
            if(isEditable) {
                description = fragment_details_description_edit_text.text.toString()
                fragment_details_description.text = description
                fragment_details_description_edit_text.visibility = View.GONE
                fragment_details_description.visibility = View.VISIBLE
                save_button.isEnabled = true
            } else {
                fragment_details_description_edit_text.setText(fragment_details_description.text)
                fragment_details_description_edit_text.visibility = View.VISIBLE
                fragment_details_description.visibility = View.GONE
                save_button.isEnabled = false
            }
            isEditable = !isEditable
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("filePathName", filePathName)
        savedInstanceState.putString("description", description)
        savedInstanceState.putDouble("rating", rating)
        savedInstanceState.putBoolean("isEditable", isEditable)
        savedInstanceState.putString("editedText", fragment_details_description_edit_text.text.toString())
    }
}
