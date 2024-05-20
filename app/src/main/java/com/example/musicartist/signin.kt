package com.example.musicartist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class signin : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var logButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // убираем шапку
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_signin)

        //переход на приветственное окно
            val imageView = findViewById<ImageView>(R.id.image)
            imageView.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }

        //бинд элементов
        emailText = findViewById(R.id.Email)
        passwordText = findViewById(R.id.Password)
        logButton = findViewById(R.id.Log)

        //авторизация
        logButton.setOnClickListener {
            lifecycleScope.launch {
                val emailPattern = emailText.text.toString().trim()
                val passwordPattern = passwordText.text.toString().trim()
                val supabase = SupaBaseClient().ClientSB

                if (emailPattern.isEmpty() || passwordPattern.isEmpty()) {
                    Toast.makeText(this@signin, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                try {
                    supabase.auth.signInWith(Email) {
                        email = emailText.text.toString().trim()
                        password = passwordText.text.toString().trim()
                    }

                    saveShared()

                    val intent = Intent(this@signin, main_menu::class.java)
                    startActivity(intent)
                }

                catch (e: Exception){
                    Toast.makeText(this@signin, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveShared(){
        val sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", emailText.text.toString())
        editor.putString("password", passwordText.text.toString())
        editor.apply()
    }
}