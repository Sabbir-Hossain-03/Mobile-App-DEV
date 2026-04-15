package com.example.task4

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var tableLayout: TableLayout
    lateinit var etSubject: EditText
    lateinit var etObtained: EditText
    lateinit var etTotal: EditText
    lateinit var btnAdd: Button
    lateinit var btnShare: Button
    lateinit var tvSummary: TextView
    lateinit var tvGpa: TextView

    var totalSubjects = 0
    var passedSubjects = 0
    var failedSubjects = 0
    var totalGradePoints = 0.0
    var dataRowCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        etSubject = findViewById(R.id.etSubject)
        etObtained = findViewById(R.id.etObtained)
        etTotal = findViewById(R.id.etTotal)
        btnAdd = findViewById(R.id.btnAdd)
        btnShare = findViewById(R.id.btnShare)
        tvSummary = findViewById(R.id.tvSummary)
        tvGpa = findViewById(R.id.tvGpa)

        addSubjectRow("Mathematics", 95, 100)
        addSubjectRow("Physics", 84, 100)
        addSubjectRow("Chemistry", 76, 100)
        addSubjectRow("Biology", 67, 100)
        addSubjectRow("English", 55, 100)
        addSubjectRow("History", 38, 100)

        btnAdd.setOnClickListener {
            val subject = etSubject.text.toString().trim()
            val obtainedText = etObtained.text.toString().trim()
            val totalText = etTotal.text.toString().trim()

            if (subject.isEmpty() || obtainedText.isEmpty() || totalText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (obtainedText.toIntOrNull() == null || totalText.toIntOrNull() == null) {
                Toast.makeText(this, "Marks must be valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val obtained = obtainedText.toInt()
            val total = totalText.toInt()

            if (total <= 0 || obtained < 0 || obtained > total) {
                Toast.makeText(this, "Please enter valid marks", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addSubjectRow(subject, obtained, total)

            etSubject.text.clear()
            etObtained.text.clear()
            etTotal.text.clear()
        }

        btnShare.setOnClickListener {
            val shareText = "${tvSummary.text}\n${tvGpa.text}"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            startActivity(Intent.createChooser(intent, "Share Grade Report"))
        }
    }

    private fun addSubjectRow(subject: String, obtained: Int, total: Int) {
        val percentage = (obtained * 100.0) / total
        val grade = getGrade(percentage)
        val gradePoint = getGradePoint(grade)

        val row = TableRow(this)

        if (grade == "F") {
            row.setBackgroundColor(Color.parseColor("#FFCDD2"))
            failedSubjects++
        } else {
            row.setBackgroundColor(
                if (dataRowCount % 2 == 0) {
                    Color.parseColor("#E8F5E9")
                } else {
                    Color.parseColor("#F1F8E9")
                }
            )
            passedSubjects++
        }

        val tvSubject = TextView(this)
        tvSubject.text = subject
        tvSubject.setPadding(8, 8, 8, 8)

        val tvObtained = TextView(this)
        tvObtained.text = obtained.toString()
        tvObtained.setPadding(8, 8, 8, 8)

        val tvTotal = TextView(this)
        tvTotal.text = total.toString()
        tvTotal.setPadding(8, 8, 8, 8)

        val tvGrade = TextView(this)
        tvGrade.text = grade
        tvGrade.setPadding(8, 8, 8, 8)
        tvGrade.setTextColor(
            if (grade == "F") Color.RED else Color.parseColor("#1B5E20")
        )

        row.addView(tvSubject)
        row.addView(tvObtained)
        row.addView(tvTotal)
        row.addView(tvGrade)

        tableLayout.addView(row)

        totalSubjects++
        totalGradePoints += gradePoint
        dataRowCount++

        updateSummary()
    }

    private fun updateSummary() {
        tvSummary.text = "Total Subjects: $totalSubjects | Passed: $passedSubjects | Failed: $failedSubjects"
        val gpa = totalGradePoints / totalSubjects
        tvGpa.text = "GPA: ${String.format(Locale.getDefault(), "%.2f", gpa)}"
    }

    private fun getGrade(percentage: Double): String {
        return when {
            percentage >= 90 -> "A+"
            percentage >= 80 -> "A"
            percentage >= 70 -> "B+"
            percentage >= 60 -> "B"
            percentage >= 50 -> "C"
            percentage >= 40 -> "D"
            else -> "F"
        }
    }

    private fun getGradePoint(grade: String): Double {
        return when (grade) {
            "A+" -> 4.0
            "A" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "C" -> 2.0
            "D" -> 1.0
            else -> 0.0
        }
    }
}