package com.example.task5

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ContactAdapter(
    context: Context,
    private var contacts: ArrayList<Contact>
) : ArrayAdapter<Contact>(context, 0, contacts) {

    class ViewHolder(view: View) {
        val avatar: TextView = view.findViewById(R.id.tvAvatar)
        val name: TextView = view.findViewById(R.id.tvName)
        val phone: TextView = view.findViewById(R.id.tvPhone)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val contact = contacts[position]

        holder.avatar.text = contact.initial
        holder.name.text = contact.name
        holder.phone.text = contact.phone

        holder.avatar.setBackgroundColor(getColor(contact.initial))

        return view!!
    }

    private fun getColor(letter: String): Int {
        return when (letter.uppercase()) {
            "A","B","C" -> Color.RED
            "D","E","F" -> Color.BLUE
            "G","H","I" -> Color.GREEN
            "J","K","L" -> Color.MAGENTA
            else -> Color.GRAY
        }
    }
}