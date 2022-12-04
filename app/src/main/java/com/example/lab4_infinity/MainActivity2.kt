package com.example.lab4_infinity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val intent: ArrayList<String> = this.intent.getStringArrayListExtra("msg")!!
        val teams = intent.map {
            if (Random.nextBoolean())
                Pair(it, "красная")
            else
                Pair(it, "зелёная")
        }
        val adapter = CustomRecyclerAdapter2(teams)
        val recyclerView = findViewById<RecyclerView>(R.id.play_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}