package com.example.musicartist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.Exception

class Home : Fragment() {
    val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    val supabase = SupaBaseClient().ClientSB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_popular_artist)
        recyclerView.layoutManager= LinearLayoutManager(context)

        lifecycleScope.launch {
            try {
                val artists = mutableListOf<DataArtist>()
                val artist = supabase.from("Исполнители").select(columns = Columns.list("id, Имя, Аватар")).data
                val jsonArray = JSONArray(artist)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val name = jsonObject.getString("Имя")
                    val ava = jsonObject.getString("Аватар")
                    val dataArtist = DataArtist(Имя = name, Аватар = ava)
                    Log.e("DAndahsda", dataArtist.toString())
                    artists.add(dataArtist)
                }
                val adapter = AdapterArtists(artists, coroutineScope)
                recyclerView.adapter = adapter
            }catch (e: Exception){
                Log.e("!!!!!!!", e.toString())
            }
        }
        return view
    }
}


