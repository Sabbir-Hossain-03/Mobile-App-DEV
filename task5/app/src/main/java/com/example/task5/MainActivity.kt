package com.example.task5

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var searchView: SearchView
    lateinit var fab: FloatingActionButton
    lateinit var tvEmpty: TextView

    lateinit var adapter: ContactAdapter

    var contactList = ArrayList<Contact>()
    var filteredList = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        listView = findViewById(R.id.listViewContacts)
        searchView = findViewById(R.id.searchView)
        fab = findViewById(R.id.fabAdd)
        tvEmpty = findViewById(R.id.tvEmpty)

        // Sample data
        contactList.add(Contact("Ehsan", "01711111111", "e@gmail.com", "E"))
        contactList.add(Contact("Sabbir", "01822222222", "s@gmail.com", "S"))

        filteredList.addAll(contactList)

        adapter = ContactAdapter(this, filteredList)
        listView.adapter = adapter

        updateEmpty()

        // ADD CONTACT
        fab.setOnClickListener {
            val name = EditText(this)
            name.hint = "Name"

            val phone = EditText(this)
            phone.hint = "Phone"

            val email = EditText(this)
            email.hint = "Email"

            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(name)
            layout.addView(phone)
            layout.addView(email)

            AlertDialog.Builder(this)
                .setTitle("Add Contact")
                .setView(layout)
                .setPositiveButton("Add") { _, _ ->
                    val n = name.text.toString()
                    val p = phone.text.toString()
                    val e = email.text.toString()

                    if (n.isNotEmpty() && p.isNotEmpty()) {
                        val contact = Contact(n, p, e, n[0].toString())
                        contactList.add(contact)
                        filter("")
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // CLICK
        listView.setOnItemClickListener { _, _, pos, _ ->
            val c = filteredList[pos]
            Toast.makeText(this,
                "Name: ${c.name}\nPhone: ${c.phone}\nEmail: ${c.email}",
                Toast.LENGTH_LONG).show()
        }

        // DELETE
        listView.setOnItemLongClickListener { _, _, pos, _ ->
            val c = filteredList[pos]

            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete ${c.name}?")
                .setPositiveButton("Yes") { _, _ ->
                    contactList.remove(c)
                    filter("")
                }
                .setNegativeButton("No", null)
                .show()

            true
        }

        // SEARCH
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean = false
        })
    }

    private fun filter(text: String) {
        filteredList.clear()

        for (c in contactList) {
            if (c.name.lowercase().contains(text.lowercase())) {
                filteredList.add(c)
            }
        }

        adapter.notifyDataSetChanged()
        updateEmpty()
    }

    private fun updateEmpty() {
        if (filteredList.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
        } else {
            tvEmpty.visibility = View.GONE
        }
    }
}