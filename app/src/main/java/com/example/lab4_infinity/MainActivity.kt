package com.example.lab4_infinity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        preferences = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        val persons = ArrayList(loadAll().map { Pair(it, false) })

        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomRecyclerAdapter(persons)
        recyclerView.adapter = adapter
        val newButton: Button = findViewById(R.id.person_new)
        val editButton: Button = findViewById(R.id.person_edit)
        val dropButton: Button = findViewById(R.id.person_drop)
        val playButton: Button = findViewById(R.id.play)
        val input: EditText = findViewById(R.id.person_name)
        newButton.setOnClickListener {
            if (input.text.isNotEmpty()) {
                persons.add(Pair(input.text.toString(), false))
                input.text.clear()
                adapter.notifyDataSetChanged()
                save(persons.map { it.first })
            }
        }
        editButton.setOnClickListener {
            if (input.text.isNotEmpty()) {
                val index = persons.indexOfFirst { it.second }
                persons[index] = Pair(input.text.toString(), false)
                input.text.clear()
                adapter.notifyDataSetChanged()
                save(persons.map { it.first })
            }
        }
        dropButton.setOnClickListener {
            persons.mapIndexedNotNull { index, pair ->
                if (pair.second)
                    index
                else
                    null
            }.reversed().forEach {
                persons.removeAt(it)
            }
            input.text.clear()
            adapter.notifyDataSetChanged()
            save(persons.map { it.first })
        }
        playButton.setOnClickListener {
            val checked = ArrayList(persons.mapNotNull { if (it.second) it.first else null })
            if (checked.isNotEmpty()) {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("msg", checked)
                startActivity(intent)
            }
        }
    }

    private fun save(persons: List<String>) {
        val editor = preferences.edit()
        editor.clear().apply()
        persons.forEachIndexed { i, it ->
            editor.putString("Person $i", it).apply()
        }
    }

    private fun loadAll(): List<String> {
        val persons = mutableListOf<String>()
        var i = 0
        var found = preferences.getString("Person " + Integer.toString(i), null)
        while (found != null) {
            persons.add(found)
            found = preferences.getString("Person " + Integer.toString(++i), null)
        }
        return persons
    }
}