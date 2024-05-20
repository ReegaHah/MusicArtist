package com.example.musicartist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRec(private val recList = ArrayList<DataPlaylist>(), private val context: Context): RecyclerView.Adapter<AdapterRec.ViewHolder>()
{
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var rec_text: TextView = itemView.findViewById(R.id.rec_text)
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterRec.ViewHolder, position: Int) {
        val rec = recList[position]
        holder.rec_text.text = rec.name
        holder.imageView.setImageResource(rec.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
        }
    }

}