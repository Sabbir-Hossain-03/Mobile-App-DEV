package com.example.task10

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SeatAdapter(private val seats: ArrayList<Seat>, private val context: android.content.Context) : BaseAdapter() {
    override fun getCount(): Int = seats.size
    override fun getItem(position: Int): Any = seats[position]
    override fun getItemId(position: Int): Long = seats[position].number.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tv = TextView(context)
        tv.text = seats[position].number.toString()
        tv.gravity = android.view.Gravity.CENTER
        tv.layoutParams = android.widget.AbsListView.LayoutParams(100, 100)
        tv.setTextColor(Color.WHITE)
        when (seats[position].state) {
            "available" -> tv.setBackgroundColor(Color.GREEN)
            "booked" -> tv.setBackgroundColor(Color.RED)
            "selected" -> tv.setBackgroundColor(Color.BLUE)
        }
        return tv
    }
}