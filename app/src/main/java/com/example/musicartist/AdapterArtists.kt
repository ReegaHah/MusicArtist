package com.example.musicartist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.InputStream

class AdapterArtists (private val artistsList: List<DataArtist>, private val coroutineScope: CoroutineScope):RecyclerView.Adapter<AdapterArtists.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterArtists.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.popular_artist_item, parent, false)
        return ViewHolder(v, coroutineScope)
    }

    override fun onBindViewHolder(holder: AdapterArtists.ViewHolder, position: Int) {
        val artists = artistsList[position]
        holder.bind(artists)
    }

    override fun getItemCount(): Int {
        return artistsList.size
    }

    class ViewHolder(private val itemView: View, private val coroutineScope: CoroutineScope):RecyclerView.ViewHolder(itemView) {
    val artistText: TextView = itemView.findViewById(R.id.artistText)
        val artistView: ImageView = itemView.findViewById(R.id.artistView)

        fun bind(artist: DataArtist) {
            val supabase = SupaBaseClient().ClientSB
            artistText.text = artist.Имя
            val photo = artist.Аватар
            val bucket = supabase.storage["Performers"]
            coroutineScope.launch {
                val bytes = bucket.downloadPublic(photo.toString())
                val is1: InputStream = ByteArrayInputStream(bytes)
                val bmp: Bitmap = BitmapFactory.decodeStream(is1)
                val dr = BitmapDrawable(itemView.context.resources, bmp)
                artistView.background = dr
            }
        }
    }

}