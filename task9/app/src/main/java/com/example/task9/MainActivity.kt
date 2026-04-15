package com.example.task9

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var tvCartBadge: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var skeletonLayout: View

    private val allProducts = mutableListOf<Product>()
    private var currentProducts = mutableListOf<Product>()
    private var isGrid = false
    private var currentCategory = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        tvCartBadge = findViewById(R.id.tvCartBadge)
        tvEmpty = findViewById(R.id.tvEmpty)
        skeletonLayout = findViewById(R.id.skeletonLayout)

        seedProducts()
        currentProducts = allProducts.toMutableList()

        adapter = ProductAdapter(currentProducts, isGrid) { product ->
            if (!product.inCart) {
                product.inCart = true
                CartStore.cartItems.add(product)
                updateCartBadge()
                adapter.notifyDataSetChanged()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        Handler(Looper.getMainLooper()).postDelayed({
            skeletonLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }, 1000)

        findViewById<Button>(R.id.btnToggle).setOnClickListener {
            isGrid = !isGrid
            recyclerView.layoutManager = if (isGrid) GridLayoutManager(this, 2) else LinearLayoutManager(this)
            adapter.updateViewMode(isGrid)
        }

        findViewById<Button>(R.id.btnCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(currentCategory, newText ?: "")
                return true
            }
        })

        findViewById<Button>(R.id.btnAll).setOnClickListener { currentCategory = "All"; filter("All", searchView.query.toString()) }
        findViewById<Button>(R.id.btnElectronics).setOnClickListener { currentCategory = "Electronics"; filter("Electronics", searchView.query.toString()) }
        findViewById<Button>(R.id.btnClothing).setOnClickListener { currentCategory = "Clothing"; filter("Clothing", searchView.query.toString()) }
        findViewById<Button>(R.id.btnBooks).setOnClickListener { currentCategory = "Books"; filter("Books", searchView.query.toString()) }
        findViewById<Button>(R.id.btnFood).setOnClickListener { currentCategory = "Food"; filter("Food", searchView.query.toString()) }
        findViewById<Button>(R.id.btnToys).setOnClickListener { currentCategory = "Toys"; filter("Toys", searchView.query.toString()) }

        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.swap(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removed = adapter.removeAt(position)
                Snackbar.make(recyclerView, "${removed.name} removed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        adapter.addAt(position, removed)
                    }.show()
            }
        })
        helper.attachToRecyclerView(recyclerView)

        updateCartBadge()
    }

    private fun seedProducts() {
        allProducts.addAll(
            listOf(
                Product(1, "Phone", 699.0, 4.5f, "Electronics", android.R.drawable.ic_menu_call),
                Product(2, "Laptop", 1200.0, 4.8f, "Electronics", android.R.drawable.ic_menu_manage),
                Product(3, "T-Shirt", 25.0, 4.0f, "Clothing", android.R.drawable.ic_menu_edit),
                Product(4, "Jeans", 40.0, 4.1f, "Clothing", android.R.drawable.ic_menu_edit),
                Product(5, "Novel", 15.0, 4.3f, "Books", android.R.drawable.ic_menu_agenda),
                Product(6, "Cookbook", 18.0, 4.2f, "Books", android.R.drawable.ic_menu_agenda),
                Product(7, "Chocolate", 6.0, 4.7f, "Food", android.R.drawable.ic_menu_crop),
                Product(8, "Coffee", 10.0, 4.4f, "Food", android.R.drawable.ic_menu_crop),
                Product(9, "Toy Car", 12.0, 4.1f, "Toys", android.R.drawable.ic_menu_compass),
                Product(10, "Puzzle", 20.0, 4.5f, "Toys", android.R.drawable.ic_menu_compass)
            )
        )
    }

    private fun filter(category: String, query: String) {
        val filtered = allProducts.filter {
            (category == "All" || it.category == category) &&
                    it.name.contains(query, ignoreCase = true)
        }.toMutableList()

        adapter.updateData(filtered)
        tvEmpty.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun updateCartBadge() {
        tvCartBadge.text = CartStore.cartItems.size.toString()
    }
}