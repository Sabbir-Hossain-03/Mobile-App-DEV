package com.example.task6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private lateinit var nestedScroll: NestedScrollView
    private lateinit var btnBookmark: ImageButton
    private var bookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nestedScroll = findViewById(R.id.nestedScroll)
        btnBookmark = findViewById(R.id.btnBookmark)

        val btnIntro = findViewById<Button>(R.id.btnIntro)
        val btnKeyPoints = findViewById<Button>(R.id.btnKeyPoints)
        val btnAnalysis = findViewById<Button>(R.id.btnAnalysis)
        val btnConclusion = findViewById<Button>(R.id.btnConclusion)
        val btnTop = findViewById<Button>(R.id.btnTop)
        val btnShare = findViewById<ImageButton>(R.id.btnShare)

        val secIntro = findViewById<android.widget.TextView>(R.id.secIntroTitle)
        val secKey = findViewById<android.widget.TextView>(R.id.secKeyTitle)
        val secAnalysis = findViewById<android.widget.TextView>(R.id.secAnalysisTitle)
        val secConclusion = findViewById<android.widget.TextView>(R.id.secConclusionTitle)

        btnIntro.setOnClickListener {
            nestedScroll.post { nestedScroll.smoothScrollTo(0, secIntro.top) }
        }
        btnKeyPoints.setOnClickListener {
            nestedScroll.post { nestedScroll.smoothScrollTo(0, secKey.top) }
        }
        btnAnalysis.setOnClickListener {
            nestedScroll.post { nestedScroll.smoothScrollTo(0, secAnalysis.top) }
        }
        btnConclusion.setOnClickListener {
            nestedScroll.post { nestedScroll.smoothScrollTo(0, secConclusion.top) }
        }

        btnTop.setOnClickListener {
            nestedScroll.smoothScrollTo(0, 0)
        }

        btnBookmark.setOnClickListener {
            bookmarked = !bookmarked
            if (bookmarked) {
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "AI Tools Are Changing the Way Students Learn")
            startActivity(Intent.createChooser(intent, "Share Article"))
        }
    }
}