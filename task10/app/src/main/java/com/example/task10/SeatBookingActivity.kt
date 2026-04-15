package com.example.task10

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SeatBookingActivity : AppCompatActivity() {

    private val seats = ArrayList<Seat>()
    private lateinit var adapter: SeatAdapter
    private lateinit var event: Event
    private lateinit var tvSummary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)

        event = intent.getSerializableExtra("event") as Event
        tvSummary = findViewById(R.id.tvSummary)
        val grid = findViewById<GridView>(R.id.gridSeats)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        for (i in 1..48) {
            val booked = Random.nextInt(100) < 30
            seats.add(Seat(i, if (booked) "booked" else "available"))
        }

        adapter = SeatAdapter(seats, this)
        grid.adapter = adapter

        grid.setOnItemClickListener { _, _, position, _ ->
            val seat = seats[position]
            if (seat.state == "booked") return@setOnItemClickListener
            seat.state = if (seat.state == "available") "selected" else "available"
            adapter.notifyDataSetChanged()
            updateSummary()
        }

        btnConfirm.setOnClickListener {
            val selected = seats.count { it.state == "selected" }
            Toast.makeText(this, "Booked $selected seats", Toast.LENGTH_SHORT).show()
            finish()
        }

        updateSummary()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val selected = seats.count { it.state == "selected" }
                if (selected > 0) {
                    AlertDialog.Builder(this@SeatBookingActivity)
                        .setTitle("Leave booking?")
                        .setMessage("You have selected seats. Leave without booking?")
                        .setPositiveButton("Yes") { _, _ -> finish() }
                        .setNegativeButton("No", null)
                        .show()
                } else {
                    finish()
                }
            }
        })
    }

    private fun updateSummary() {
        val selected = seats.count { it.state == "selected" }
        val total = selected * event.price
        tvSummary.text = "$selected seats selected | Total: $$total"
    }
}