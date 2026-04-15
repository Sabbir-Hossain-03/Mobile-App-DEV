package com.example.task9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class ProductAdapter(
    private var products: MutableList<Product>,
    private var isGrid: Boolean,
    private val onCartClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_LIST = 0
        private const val TYPE_GRID = 1
    }

    class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgProduct)
        val name: TextView = view.findViewById(R.id.tvName)
        val category: TextView = view.findViewById(R.id.tvCategory)
        val rating: RatingBar = view.findViewById(R.id.ratingBar)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val btnCart: Button = view.findViewById(R.id.btnCart)
    }

    class GridHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgProduct)
        val name: TextView = view.findViewById(R.id.tvName)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val btnCart: ImageButton = view.findViewById(R.id.btnCartGrid)
    }

    override fun getItemViewType(position: Int): Int = if (isGrid) TYPE_GRID else TYPE_LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_GRID) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_grid, parent, false)
            GridHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
            ListHolder(view)
        }
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        if (holder is ListHolder) {
            holder.img.setImageResource(product.imageRes)
            holder.name.text = product.name
            holder.category.text = product.category
            holder.rating.rating = product.rating
            holder.price.text = "$${product.price}"
            holder.btnCart.text = if (product.inCart) "Added" else "Add"
            holder.btnCart.setOnClickListener { onCartClick(product) }
        } else if (holder is GridHolder) {
            holder.img.setImageResource(product.imageRes)
            holder.name.text = product.name
            holder.price.text = "$${product.price}"
            holder.btnCart.setOnClickListener { onCartClick(product) }
        }
    }

    fun updateViewMode(grid: Boolean) {
        isGrid = grid
        notifyDataSetChanged()
    }

    fun updateData(newProducts: MutableList<Product>) {
        val diff = object : DiffUtil.Callback() {
            override fun getOldListSize() = products.size
            override fun getNewListSize() = newProducts.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                products[oldItemPosition].id == newProducts[newItemPosition].id
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                products[oldItemPosition] == newProducts[newItemPosition]
        }
        val result = DiffUtil.calculateDiff(diff)
        products = newProducts
        result.dispatchUpdatesTo(this)
    }

    fun removeAt(position: Int): Product {
        val removed = products.removeAt(position)
        notifyItemRemoved(position)
        return removed
    }

    fun addAt(position: Int, product: Product) {
        products.add(position, product)
        notifyItemInserted(position)
    }

    fun swap(from: Int, to: Int) {
        Collections.swap(products, from, to)
        notifyItemMoved(from, to)
    }

    fun currentItems(): MutableList<Product> = products
}