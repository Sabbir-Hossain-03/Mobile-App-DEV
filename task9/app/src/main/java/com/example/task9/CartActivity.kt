package com.example.task9

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recycler = findViewById<RecyclerView>(R.id.cartRecycler)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ProductAdapter(CartStore.cartItems.toMutableList(), false) {}

        val total = CartStore.cartItems.sumOf { it.price }
        tvTotal.text = "Total: $${"%.2f".format(total)}"

        btnCheckout.setOnClickListener {
            Toast.makeText(this, "Checkout complete", Toast.LENGTH_SHORT).show()
        }
    }
}

object CartStore {
    val cartItems = ArrayList<Product>()
}