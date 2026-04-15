package com.example.task3

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var tvDate: TextView
    lateinit var tvStepsValue: TextView
    lateinit var progressBar: ProgressBar
    lateinit var tvProgressPercent: TextView
    lateinit var btnUpdate: Button

    var dailyGoal = 10000
    var currentSteps = 4500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDate = findViewById(R.id.tvDate)
        tvStepsValue = findViewById(R.id.tvStepsValue)
        progressBar = findViewById(R.id.progressBar)
        tvProgressPercent = findViewById(R.id.tvProgressPercent)
        btnUpdate = findViewById(R.id.btnUpdate)

        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        tvDate.text = dateFormat.format(Date())

        updateProgress()

        btnUpdate.setOnClickListener {
            val editText = EditText(this)
            editText.hint = "Enter new step count"

            AlertDialog.Builder(this)
                .setTitle("Update Step Count")
                .setView(editText)
                .setPositiveButton("Update") { _, _ ->
                    val input = editText.text.toString().trim()

                    if (input.isEmpty() || input.toIntOrNull() == null || input.toInt() < 0) {
                        Toast.makeText(this, "Please enter a valid step count", Toast.LENGTH_SHORT).show()
                    } else {
                        currentSteps = input.toInt()
                        tvStepsValue.text = currentSteps.toString()
                        updateProgress()

                        if (currentSteps >= dailyGoal) {
                            Toast.makeText(this, "Excellent! You reached 100% of your goal!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun updateProgress() {
        var percent = (currentSteps * 100) / dailyGoal
        if (percent > 100) {
            percent = 100
        }

        progressBar.progress = percent
        tvProgressPercent.text = "$percent%"
    }
}