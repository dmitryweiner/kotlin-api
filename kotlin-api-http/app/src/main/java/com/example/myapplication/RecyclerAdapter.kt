package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val list: List<User>,
    private val onClick: (id: Int) -> Unit
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = list[position].name
        holder.textViewUsername.text = list[position].email
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
        val textViewUsername = itemView.findViewById<TextView>(R.id.textViewUsername)

        init {
            itemView.setOnClickListener {
                onClick(list[adapterPosition].id)
            }
        }
    }
}