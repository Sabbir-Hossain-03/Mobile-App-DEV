package com.example.task10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnBrowse).setOnClickListener {
            startActivity(Intent(this, EventsListActivity::class.java))
        }

        findViewById<Button>(R.id.btnRegisterFeatured).setOnClickListener {
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", SampleEvents.getEvents()[0])
            startActivity(intent)
        }
    }
}