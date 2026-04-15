package com.example.task10

import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventsListActivity : AppCompatActivity() {

    private lateinit var adapter: EventAdapter
    private val allEvents = SampleEvents.getEvents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_list)

        val recycler = findViewById<RecyclerView>(R.id.eventRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(allEvents, this)
        recycler.adapter = adapter

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filter("All", newText ?: "")
                return true
            }
        })

        findViewById<Button>(R.id.btnAll).setOnClickListener { filter("All", searchView.query.toString()) }
        findViewById<Button>(R.id.btnTech).setOnClickListener { filter("Tech", searchView.query.toString()) }
        findViewById<Button>(R.id.btnSports).setOnClickListener { filter("Sports", searchView.query.toString()) }
        findViewById<Button>(R.id.btnCultural).setOnClickListener { filter("Cultural", searchView.query.toString()) }
        findViewById<Button>(R.id.btnAcademic).setOnClickListener { filter("Academic", searchView.query.toString()) }
        findViewById<Button>(R.id.btnSocial).setOnClickListener { filter("Social", searchView.query.toString()) }
    }

    private fun filter(category: String, query: String) {
        val filtered = ArrayList(allEvents.filter {
            (category == "All" || it.category == category) &&
                    it.title.contains(query, true)
        })
        adapter.updateData(filtered)
    }
}