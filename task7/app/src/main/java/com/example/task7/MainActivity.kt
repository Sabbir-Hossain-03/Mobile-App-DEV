package com.example.task7

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: PhotoAdapter
    private lateinit var selectionBar: LinearLayout
    private lateinit var tvSelectedCount: TextView

    private val allPhotos = ArrayList<Photo>()
    private val filteredPhotos = ArrayList<Photo>()

    private var selectionMode = false
    private var nextId = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        selectionBar = findViewById(R.id.selectionBar)
        tvSelectedCount = findViewById(R.id.tvSelectedCount)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnShare = findViewById<Button>(R.id.btnShare)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        seedPhotos()
        filteredPhotos.addAll(allPhotos)

        adapter = PhotoAdapter(this, filteredPhotos)
        gridView.adapter = adapter

        findViewById<Button>(R.id.btnAll).setOnClickListener { filterBy("All") }
        findViewById<Button>(R.id.btnNature).setOnClickListener { filterBy("Nature") }
        findViewById<Button>(R.id.btnCity).setOnClickListener { filterBy("City") }
        findViewById<Button>(R.id.btnAnimals).setOnClickListener { filterBy("Animals") }
        findViewById<Button>(R.id.btnFood).setOnClickListener { filterBy("Food") }
        findViewById<Button>(R.id.btnTravel).setOnClickListener { filterBy("Travel") }

        gridView.setOnItemClickListener { _, _, position, _ ->
            val photo = filteredPhotos[position]
            if (selectionMode) {
                photo.isSelected = !photo.isSelected
                adapter.notifyDataSetChanged()
                updateSelectionBar()
            } else {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("imageRes", photo.resourceId)
                startActivity(intent)
            }
        }

        gridView.setOnItemLongClickListener { _, _, position, _ ->
            selectionMode = true
            adapter.selectionMode = true
            filteredPhotos[position].isSelected = true
            adapter.notifyDataSetChanged()
            updateSelectionBar()
            true
        }

        btnDelete.setOnClickListener {
            val selected = allPhotos.filter { it.isSelected }.toList()
            allPhotos.removeAll(selected)
            filterBy("All")
            Toast.makeText(this, "${selected.size} photos deleted", Toast.LENGTH_SHORT).show()
            exitSelectionMode()
        }

        btnShare.setOnClickListener {
            val count = allPhotos.count { it.isSelected }
            Toast.makeText(this, "$count photos selected for sharing", Toast.LENGTH_SHORT).show()
        }

        fabAdd.setOnClickListener {
            val drawables = listOf(
                android.R.drawable.ic_menu_camera,
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_report_image,
                android.R.drawable.ic_menu_compass
            )
            val categories = listOf("Nature", "City", "Animals", "Food", "Travel")
            val res = drawables.random()
            val cat = categories.random()
            allPhotos.add(Photo(nextId++, res, "New $cat", cat, false))
            filterBy("All")
            Toast.makeText(this, "Photo added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun seedPhotos() {
        val list = listOf(
            Photo(1, android.R.drawable.ic_menu_gallery, "Forest", "Nature"),
            Photo(2, android.R.drawable.ic_menu_camera, "Street", "City"),
            Photo(3, android.R.drawable.ic_menu_report_image, "Lion", "Animals"),
            Photo(4, android.R.drawable.ic_menu_crop, "Pizza", "Food"),
            Photo(5, android.R.drawable.ic_menu_compass, "Beach", "Travel"),
            Photo(6, android.R.drawable.ic_menu_gallery, "Mountain", "Nature"),
            Photo(7, android.R.drawable.ic_menu_camera, "Night City", "City"),
            Photo(8, android.R.drawable.ic_menu_report_image, "Bird", "Animals"),
            Photo(9, android.R.drawable.ic_menu_crop, "Burger", "Food"),
            Photo(10, android.R.drawable.ic_menu_compass, "Airport", "Travel"),
            Photo(11, android.R.drawable.ic_menu_gallery, "River", "Nature"),
            Photo(12, android.R.drawable.ic_menu_camera, "Bridge", "City")
        )
        allPhotos.addAll(list)
    }

    private fun filterBy(category: String) {
        filteredPhotos.clear()
        if (category == "All") {
            filteredPhotos.addAll(allPhotos)
        } else {
            filteredPhotos.addAll(allPhotos.filter { it.category == category })
        }
        adapter.updateData(filteredPhotos)
        updateSelectionBar()
    }

    private fun updateSelectionBar() {
        val count = allPhotos.count { it.isSelected }
        selectionBar.visibility = if (selectionMode) android.view.View.VISIBLE else android.view.View.GONE
        tvSelectedCount.text = "$count selected"
        if (count == 0 && selectionMode) exitSelectionMode()
    }

    private fun exitSelectionMode() {
        selectionMode = false
        adapter.selectionMode = false
        allPhotos.forEach { it.isSelected = false }
        adapter.notifyDataSetChanged()
        selectionBar.visibility = android.view.View.GONE
    }
}