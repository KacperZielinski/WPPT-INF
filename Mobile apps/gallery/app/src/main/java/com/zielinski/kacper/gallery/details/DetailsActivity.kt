package com.zielinski.kacper.gallery.details

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zielinski.kacper.gallery.R

class DetailsActivity : AppCompatActivity() {

    private var filePathName = ""
    private var description = ""
    private var rating = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if(intent != null) {
            filePathName = intent!!.extras.getString("filePathName")
            description = intent.extras.getString("description")
            rating = intent.extras.getDouble("rating")
        }
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

    // pokaz zdjecie na prawie caly ekran, opis, i rating bar, jakis button save, ktory zapisze i przekaze dane do mainActivity

    // ta bedzie miala fragmenty przy zmianie ekranu, a dlaczego by i nie ;p
    // np. z lewej zdjecie w landscape i reszta z innej strony
}
