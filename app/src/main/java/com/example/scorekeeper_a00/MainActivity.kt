package com.example.scorekeeper_a00

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    // declaring the variables for the text views and buttons
    private lateinit var team1Score: TextView
    private lateinit var team2Score: TextView
    private lateinit var scoreIncrease: TextView
    private lateinit var errorMessage: TextView
    private lateinit var btnReset: Button

    // Shared Preference node
    private lateinit var sharedPrefs: SharedPreferences
    val storeScoreDataSettings = "StoreScoreDataSettings"

    // this method is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setting the layout file for the activity
        setContentView(R.layout.activity_main)

        // initiating preference manager to get default shared preferences
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        // initializing the variables with the corresponding view elements
        team1Score = findViewById(R.id.team1Score)
        team2Score = findViewById(R.id.team2Score)
        scoreIncrease = findViewById(R.id.ScoreIncreaseLbl)
        errorMessage = findViewById(R.id.textErrorMessage)
        btnReset = findViewById(R.id.btnReset)

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

        btnReset.setOnClickListener(){
            team1Score.text = "0"
            team2Score.text = "0"
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

    // Inflate the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // Handling menu click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.action_about -> {
                var name = "Rounak"
                var courseCode = "IOT-1009"
                var semester = 4
                Toast.makeText(applicationContext, "Name: $name\nSemester: $semester\nCourse Code: $courseCode",Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Method: onPause, saves data to shared preference if saveScoreDataMode is on
    override fun onPause() {
        super.onPause()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefs =  getSharedPreferences(storeScoreDataSettings, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        if(prefs.getBoolean("saveScoreDataMode", false)){
            editor.putString("team1Score", team1Score.text.toString())
            editor.putString("team2Score", team2Score.text.toString())
            editor.putBoolean("prefs_save_values", true)
        } else {
            editor.clear()
            editor.putBoolean("prefs_save_values", false)
        }
        editor.apply()
    }

    // Method: onResume, reloads the data to user interface if prefs_save_values is set to true, and only if user wishes to reload.
    override fun onResume() {
        super.onResume()
        sharedPrefs =  getSharedPreferences(storeScoreDataSettings, Context.MODE_PRIVATE)
        if(sharedPrefs.getBoolean("prefs_save_values", false)){
            team1Score.text = sharedPrefs.getString("team1Score", "0")
            team2Score.text = sharedPrefs.getString("team2Score", "0")
        }
    }
}