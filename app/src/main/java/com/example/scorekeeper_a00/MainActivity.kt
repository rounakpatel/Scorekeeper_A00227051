package com.example.scorekeeper_a00

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // declaring the variables for the text views and buttons
    private lateinit var team1Score: TextView
    private lateinit var team2Score: TextView
    private lateinit var scoreIncrease: TextView
    private lateinit var errorMessage: TextView

    // this method is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setting the layout file for the activity
        setContentView(R.layout.activity_main)

        // initializing the variables with the corresponding view elements
        team1Score = findViewById(R.id.team1Score)
        team2Score = findViewById(R.id.team2Score)
        scoreIncrease = findViewById(R.id.ScoreIncreaseLbl)
        errorMessage = findViewById(R.id.textErrorMessage)

        // setting up the four buttons (two for adding and two for subtracting scores)
        val team1Add: Button = findViewById(R.id.team1AddButton)
        team1Add.setOnClickListener {
            // clearing the error message
            errorMessage.text = ""

            // calculating the new score and updating the corresponding text view
            val result = addMethod(
                team1Score.text.toString().toInt(),
                scoreIncrease.text.toString().toInt()
            )
            team1Score.text = result.toString()
        }

        val team2Add: Button = findViewById(R.id.team2addbutton)
        team2Add.setOnClickListener {
            // clearing the error message
            errorMessage.text = ""

            // calculating the new score and updating the corresponding text view
            val result = addMethod(
                team2Score.text.toString().toInt(),
                scoreIncrease.text.toString().toInt()
            )
            team2Score.text = result.toString()
        }

        val team1Subtract: Button = findViewById(R.id.team1SubtractButton)
        team1Subtract.setOnClickListener {
            // subtracting the score and checking if the result is negative
            val result = subtractMethod(
                team1Score.text.toString().toInt(),
                scoreIncrease.text.toString().toInt()
            )

            if (result < 0) {
                // displaying an error message if the result is negative
                errorMessage.text = "Value cannot be negative"
            } else if (result >= 0) {
                // clearing the error message and updating the corresponding text view
                errorMessage.text = ""
                team1Score.text = result.toString()
            }
        }

        val team2SubtractButton: Button = findViewById(R.id.team2SubtractButton)
        team2SubtractButton.setOnClickListener {
            // subtracting the score and checking if the result is negative
            val result = subtractMethod(
                team2Score.text.toString().toInt(),
                scoreIncrease.text.toString().toInt()
            )

            if (result < 0) {
                // displaying an error message if the result is negative
                errorMessage.text = "Value cannot be negative"
            } else if (result >= 0) {
                // clearing the error message and updating the corresponding text view
                errorMessage.text = ""
                team2Score.text = result.toString()
            }
        }
    }

    // method to subtract two numbers and return the result
    private fun subtractMethod(oldNumber: Int, newNumber: Int): Int {
        val result = oldNumber - newNumber
        return result
    }

    // method to add two numbers and return the result
    private fun addMethod(oldNumber: Int, newNumber: Int): Int {
        val result = oldNumber + newNumber
        return result
    }
}