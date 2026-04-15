package com.example.task2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var imgLogo: ImageView
    lateinit var tvTitle: TextView
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var tvForgotPassword: TextView
    lateinit var btnLogin: Button
    lateinit var profileCard: LinearLayout
    lateinit var btnLogout: Button
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgLogo = findViewById(R.id.imgLogo)
        tvTitle = findViewById(R.id.tvTitle)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        btnLogin = findViewById(R.id.btnLogin)
        profileCard = findViewById(R.id.profileCard)
        btnLogout = findViewById(R.id.btnLogout)
        progressBar = findViewById(R.id.progressBar)

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username == "admin" && password == "1234") {
                progressBar.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.GONE

                    imgLogo.visibility = View.GONE
                    tvTitle.visibility = View.GONE
                    etUsername.visibility = View.GONE
                    etPassword.visibility = View.GONE
                    tvForgotPassword.visibility = View.GONE
                    btnLogin.visibility = View.GONE

                    profileCard.visibility = View.VISIBLE
                }, 1200)
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogout.setOnClickListener {
            profileCard.visibility = View.GONE

            imgLogo.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            etUsername.visibility = View.VISIBLE
            etPassword.visibility = View.VISIBLE
            tvForgotPassword.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE

            etUsername.text.clear()
            etPassword.text.clear()
        }
    }
}