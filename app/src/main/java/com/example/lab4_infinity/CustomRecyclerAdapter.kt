package com.example.lab4_infinity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class CustomRecyclerAdapter(private val names: ArrayList<Pair<String, Boolean>>): RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personName: TextView = itemView.findViewById(R.id.recycler_person_name)
        //val check: ImageView = itemView.findViewById(R.id.recycler_person_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.personName.text = names[position].first
        if(!names[position].second){
            holder.itemView.setBackgroundColor(R.color.black)
        }
        else{
            holder.itemView.setBackgroundColor(R.color.white)
        }
        //holder.check.visibility = if (names[position].second) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener {
            when (names[position].second) {
                true -> names[position] = Pair(names[position].first, false)
                false -> names[position] = Pair(names[position].first, true)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = names.size
}