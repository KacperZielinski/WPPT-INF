package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var watchWord = ""
    private var currentWatchWord = ""
    private var imageLevel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        watchWord = generateWatchWord()
        reset.setOnClickListener { resetGame() }
        check.setOnClickListener { checkWatchWord() }
    }

    private fun generateWatchWord(): String {
        val array = resources.getStringArray(R.array.words)
        val random = Random()
        val randomNumber = random.nextInt(array.size)

        val word = array[randomNumber].toLowerCase()

        currentWatchWord = ""
        for (i in 0 until word.length) {
            currentWatchWord += "?"
        }

        current_watchword.text = currentWatchWord

//        Toast.makeText(this, word,  Toast.LENGTH_SHORT).show()

        return word
    }

    private fun checkWatchWord() {
        var guessedChar = ' '

        if(!edit_text.text.isNullOrEmpty())
            guessedChar = (edit_text?.text.toString()[0])

        if(watchWord.contains(guessedChar, true)) {
            val builder = StringBuilder()

            for (i in 0 until watchWord.length) {
                if(guessedChar == watchWord[i]) {
                    builder.append(guessedChar)
                } else {
                    if(currentWatchWord[i] == '?') {
                        builder.append('?')
                    } else {
                        builder.append(currentWatchWord[i])
                    }
                }
            }
            currentWatchWord = builder.toString()
            current_watchword.text = currentWatchWord

            if(!currentWatchWord.contains('?')) {
                check.isEnabled = false
                setImage("you_win")
            }

        } else {
            if(imageLevel >= 10) {
                check.isEnabled = false
                current_watchword.text = watchWord
                setImage("you_lose")
            } else {
                imageLevel++
                setImage("hangman$imageLevel")
            }
        }

        edit_text.setText("")
    }

    private fun resetGame() {
        check.isEnabled = true
        imageLevel = 0
        watchWord = generateWatchWord()

        current_watchword.text = currentWatchWord
        setImage("hangman0")
    }

    private fun setImage(imageName: String) {
        val res = resources.getIdentifier(imageName, "drawable", packageName)
        image.setImageResource(res)
    }
}
