package com.example.task10

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(private val images: List<Int>) : RecyclerView.Adapter<GalleryAdapter.Holder>() {
    class Holder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val iv = ImageView(parent.context)
        iv.layoutParams = ViewGroup.LayoutParams(240, 120)
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        return Holder(iv)
    }
    override fun getItemCount(): Int = images.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }
}