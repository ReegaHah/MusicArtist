package com.example.musicartist

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // запускаем splash screen
        Thread.sleep(3000)
        installSplashScreen()

        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)
        var email = sharedPreferences.getString("email", null)
        var password = sharedPreferences.getString("password", null)

        if (email != null || password != null) {
            val intent = Intent(this, main_menu::class.java)
            startActivity(intent)
        }

        // убираем шапку
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        //переход на окно входа
        val button = findViewById<Button>(R.id.signin)
        button.setOnClickListener{
            val intent = Intent(this, signin::class.java)
            startActivity(intent)
        }

        //переход на окно регистрации
        val button1 = findViewById<Button>(R.id.signup)
        button1.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
    }

}