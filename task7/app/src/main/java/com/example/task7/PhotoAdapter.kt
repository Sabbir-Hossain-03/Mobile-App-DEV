package com.example.task7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class PhotoAdapter(
    private val context: Context,
    private var photos: ArrayList<Photo>
) : BaseAdapter() {

    var selectionMode = false

    override fun getCount(): Int = photos.size
    override fun getItem(position: Int): Any = photos[position]
    override fun getItemId(position: Int): Long = photos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)

        val img = view.findViewById<ImageView>(R.id.imgPhoto)
        val title = view.findViewById<TextView>(R.id.tvPhotoTitle)
        val check = view.findViewById<CheckBox>(R.id.checkSelect)

        val photo = photos[position]
        img.setImageResource(photo.resourceId)
        title.text = photo.title
        check.visibility = if (selectionMode) View.VISIBLE else View.GONE
        check.isChecked = photo.isSelected

        return view
    }

    fun updateData(newPhotos: ArrayList<Photo>) {
        photos = newPhotos
        notifyDataSetChanged()
    }
}