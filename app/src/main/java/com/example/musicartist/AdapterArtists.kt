package com.example.musicartist

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicartist.databinding.PopularArtistItemBinding
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.InputStream

class AdapterArtists (private val artistList: List<DataArtist>, private val coroutineScope: CoroutineScope): RecyclerView.Adapter<AdapterArtists.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = PopularArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, coroutineScope)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist: DataArtist = artistList[position]
        holder.bind(artist)
    }

    class ViewHolder(private val itemBinding: PopularArtistItemBinding, private val coroutineScope: CoroutineScope): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(artist: DataArtist) { with(itemBinding)
        {

            val supabase = SupaBaseClient().ClientSB
            artistText.text = artist.Имя
            val bucket = supabase.storage["Performers"]

            coroutineScope.launch {
                try {
                    val bytes = bucket.downloadPublic(artist.Аватар!!)
                    val is1: InputStream = ByteArrayInputStream(bytes)
                    val bmp: Bitmap = BitmapFactory.decodeStream(is1)
                    val dr = BitmapDrawable(itemView.context.resources, bmp)
                    artistView.setImageDrawable(dr)
                }
                catch (e: Throwable) {
                    Log.e("AdapterArtists!!!", e.toString())
                }

            }
        }
        }
    }
}