package com.example.simpleapp

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Random

class MainActivity : AppCompatActivity() {

    private var areViewsBolded = false
    private var attemptsCounter = 0
    private var number = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guess_button.setOnClickListener { guess() }
        reset.setOnClickListener { resetGame() }
        bolder.setOnClickListener { boldViews() }

        if(number == -1) {
            number = rand(1, 100)
        }
    }

    private fun rand(from: Int, to: Int) : Int {
        val random = Random()
        return random.nextInt(to - from) + from
    }


    private fun guess() {
        val guessInput = guess_input.text.toString()

        try {
            val parsedInput = guessInput.toInt()

            when {
                parsedInput == number -> {
                    cleanUpAfterWon("That was '$number'. Congrats! You've won in $attemptsCounter attempts!")
                }
                parsedInput > number -> {
                    result.text = "Number is smaller than you're thinking :)"
                    incrementCounter()
                }
                else -> {
                    result.text = "Number is a little bit greater than you're thinking :)"
                    incrementCounter()
                }
            }

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please pass proper values...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cleanUpAfterWon(youWon: String) {
        result.text = youWon
        counter.text = ""
        guess_input.setText("")
        guess_button.isClickable = false
    }

    private fun incrementCounter() {
        attemptsCounter++
        setAttemptsView(attemptsCounter)
    }

    private fun resetGame() {
        number = rand(1, 100)
        attemptsCounter = 0
        guess_button.isClickable = true
        result.text = ""
        setAttemptsView(0)
    }

    private fun setAttemptsView(attemptsCounter: Int) {
        val attemptsStr = "Attempts: $attemptsCounter"
        counter.text = attemptsStr
    }

    private fun boldViews() {
        if(areViewsBolded) {
            guess_text.setTypeface(null, Typeface.NORMAL)
            result.setTypeface(null, Typeface.NORMAL)
            counter.setTypeface(null, Typeface.NORMAL)
        } else {
            guess_text.setTypeface(null, Typeface.BOLD)
            result.setTypeface(null, Typeface.BOLD)
            counter.setTypeface(null, Typeface.BOLD)
        }
        areViewsBolded = !areViewsBolded
    }

}
