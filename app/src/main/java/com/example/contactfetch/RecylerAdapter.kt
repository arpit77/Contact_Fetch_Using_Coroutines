package com.example.contactfetch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecylerAdapter(private val context: Context, private val list: ArrayList<Contacts>) : RecyclerView.Adapter<RecylerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyler_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var contact = list[position]
        holder.name.text = contact.name
        holder.number.text = contact.number
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.name)
        var number = view.findViewById<TextView>(R.id.number)
    }
}