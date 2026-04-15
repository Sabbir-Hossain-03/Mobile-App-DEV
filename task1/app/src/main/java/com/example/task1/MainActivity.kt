package com.example.task1

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etAge: EditText

    private lateinit var radioGroup: RadioGroup

    private lateinit var cbFootball: CheckBox
    private lateinit var cbCricket: CheckBox
    private lateinit var cbBasketball: CheckBox
    private lateinit var cbBadminton: CheckBox

    private lateinit var spinnerCountry: Spinner

    private lateinit var btnDate: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button

    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etId = findViewById(R.id.etId)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etAge = findViewById(R.id.etAge)

        radioGroup = findViewById(R.id.radioGroup)

        cbFootball = findViewById(R.id.cbFootball)
        cbCricket = findViewById(R.id.cbCricket)
        cbBasketball = findViewById(R.id.cbBasketball)
        cbBadminton = findViewById(R.id.cbBadminton)

        spinnerCountry = findViewById(R.id.spinnerCountry)

        btnDate = findViewById(R.id.btnDate)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)

        val countries = arrayOf("Bangladesh", "India", "USA", "UK", "Canada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spinnerCountry.adapter = adapter

        btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m + 1}/$y"
                btnDate.text = selectedDate
            }, year, month, day)

            datePickerDialog.show()
        }

        btnSubmit.setOnClickListener {
            val id = etId.text.toString().trim()
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val ageText = etAge.text.toString().trim()

            val selectedGenderId = radioGroup.checkedRadioButtonId

            if (id.isEmpty() ||
                name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                ageText.isEmpty() ||
                selectedDate.isEmpty() ||
                selectedGenderId == -1 ||
                !email.contains("@") ||
                ageText.toIntOrNull() == null ||
                ageText.toInt() <= 0
            ) {
                Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_SHORT).show()
            } else {
                val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

                val sportsList = ArrayList<String>()
                if (cbFootball.isChecked) sportsList.add("Football")
                if (cbCricket.isChecked) sportsList.add("Cricket")
                if (cbBasketball.isChecked) sportsList.add("Basketball")
                if (cbBadminton.isChecked) sportsList.add("Badminton")

                val sports = if (sportsList.isEmpty()) "None" else sportsList.joinToString(", ")
                val country = spinnerCountry.selectedItem.toString()

                val message = """
                    ID: $id
                    Name: $name
                    Gender: $gender
                    Sports: $sports
                    Country: $country
                    DOB: $selectedDate
                """.trimIndent()

                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        btnReset.setOnClickListener {
            etId.text.clear()
            etName.text.clear()
            etEmail.text.clear()
            etPassword.text.clear()
            etAge.text.clear()

            radioGroup.clearCheck()

            cbFootball.isChecked = false
            cbCricket.isChecked = false
            cbBasketball.isChecked = false
            cbBadminton.isChecked = false

            spinnerCountry.setSelection(0)

            selectedDate = ""
            btnDate.text = "Select Date"
        }
    }
}