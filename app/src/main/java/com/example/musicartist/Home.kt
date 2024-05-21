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
import android.view.Window
import android.view.WindowManager
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
    // Создаем новый экземпляр CoroutineScope, который будет использоваться для запуска корутин в основном потоке.
    val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    // Создаем ссылку на объект SupaBaseClient, который предоставляет доступ к базе данных Supabase.
    val supabase = SupaBaseClient().ClientSB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Инициализируем корневой вид фрагмента, используя layout файл fragment_home.
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Находим RecyclerView в разметке и устанавливаем горизонтальный макетщик.
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_popular_artist)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Запускаем корутину для получения данных из базы данных Supabase.
        lifecycleScope.launch {
            try {
                // Создаем пустой список для хранения данных артистов.
                val artists = mutableListOf<DataArtist>()

                // Получаем данные артистов из базы данных.
                val artist = supabase.from("Исполнители").select(columns = Columns.list("id, Имя, Аватар")).data

                // Преобразуем полученные данные в JSONArray.
                val jsonArray = JSONArray(artist)

                // Перебираем элементы JSONArray и добавляем их в список artists.
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    // Получаем значения ID, имени и аватара артиста из JSONObject.
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("Имя")
                    val ava = jsonObject.getString("Аватар")
                    // Создаем экземпляр DataArtist и добавляем его в список artists.
                    val dataArtist = DataArtist(id = id, Имя = name, Аватар = ava)
                    Log.e("DAndahsda", dataArtist.toString())
                    artists.add(dataArtist)
                }
                // Создаем адаптер для списка artists и устанавливаем его в RecyclerView.
                val adapter = AdapterArtists(artists, coroutineScope)
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                // Логируем любую возникшую ошибку.
                Log.e("!!!!!!!", e.toString())
            }
        }
        // Возвращаем корневой вид фрагмента.
        return view
    }
}



