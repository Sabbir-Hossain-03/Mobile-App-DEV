package com.example.task10

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private var events: ArrayList<Event>,
    private val activity: androidx.appcompat.app.AppCompatActivity
) : RecyclerView.Adapter<EventAdapter.EventHolder>() {

    class EventHolder(parent: android.view.View) : RecyclerView.ViewHolder(parent) {
        val img: ImageView = parent.findViewById(R.id.imgEvent)
        val title: TextView = parent.findViewById(R.id.tvTitle)
        val date: TextView = parent.findViewById(R.id.tvDate)
        val venue: TextView = parent.findViewById(R.id.tvVenue)
        val seats: TextView = parent.findViewById(R.id.tvSeats)
        val price: TextView = parent.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = events[position]
        holder.img.setImageResource(event.imageRes)
        holder.title.text = event.title
        holder.date.text = "${event.date} ${event.time}"
        holder.venue.text = event.venue
        holder.seats.text = "Available Seats: ${event.availableSeats}"
        holder.price.text = "Price: $${event.price}"

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra("event", event)
            activity.startActivity(intent)
        }
    }

    fun updateData(newEvents: ArrayList<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}