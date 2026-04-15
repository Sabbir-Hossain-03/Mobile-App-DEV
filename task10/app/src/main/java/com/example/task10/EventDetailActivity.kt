package com.example.task10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val event = intent.getSerializableExtra("event") as Event

        findViewById<ImageView>(R.id.imgHeader).setImageResource(event.imageRes)
        findViewById<TextView>(R.id.tvTitle).text = event.title
        findViewById<TextView>(R.id.tvMeta).text = "${event.date} ${event.time}\n${event.venue}\nOrganizer: University Club"
        findViewById<TextView>(R.id.tvDescription).text =
            "${event.description}\n\nThis is a longer description for the selected event. It explains the purpose, highlights, guest speakers, and what students can expect from joining. You can expand this text further for your submission."

        val gallery = findViewById<RecyclerView>(R.id.galleryRecycler)
        gallery.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gallery.adapter = GalleryAdapter(listOf(event.imageRes, event.imageRes, event.imageRes))

        val speakers = findViewById<LinearLayout>(R.id.speakerContainer)
        repeat(3) { index ->
            val tv = TextView(this)
            tv.text = "Speaker ${index + 1} - Senior Lecturer"
            tv.setPadding(8, 8, 8, 8)
            speakers.addView(tv)
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val intent = Intent(this, SeatBookingActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
    }
}