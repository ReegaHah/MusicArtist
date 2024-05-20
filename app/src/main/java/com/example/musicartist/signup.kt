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
import com.google.android.material.snackbar.Snackbar
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class signup : AppCompatActivity() {
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var nameText: EditText
    private lateinit var regButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // убираем шапку
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_signup)

        //переход на приветственное окно
        val imageView = findViewById<ImageView>(R.id.Back)
        imageView.setOnClickListener {
            val intent = Intent(this@signup, MainActivity::class.java)
            startActivity(intent)
        }

        //бинд элементов
        regButton = findViewById(R.id.registr1)
        emailText = findViewById(R.id.Email1)
        passwordText = findViewById(R.id.Password1)
        nameText = findViewById(R.id.Name1)

        //регистрация
        regButton.setOnClickListener {
            lifecycleScope.launch {
                    val supabase = SupaBaseClient().ClientSB
                    val emailPattern = emailText.text.toString().trim()
                    val passwordPattern = passwordText.text.toString().trim()
                    val namePattern = nameText.text.toString().trim()


                    if (!emailPattern.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())){
                        Toast.makeText(this@signup, "Неправильный формат почты", Toast.LENGTH_LONG).show()
                    }

                    if (emailPattern.isEmpty()){
                        Snackbar.make(it, "Поле электронной почты должно быть заполнено", Snackbar.LENGTH_LONG).show()
                    }

                    if(passwordPattern.length < 6){
                        Snackbar.make(it, "Длина пароля должна быть не менее 6 символов", Snackbar.LENGTH_LONG).show()
                        return@launch
                    }

                    if(passwordPattern.isEmpty()){
                        Snackbar.make(it, "Поле пароля должно быть заполнено", Snackbar.LENGTH_LONG).show()
                        return@launch
                    }

                    if(namePattern.isEmpty()){
                        Snackbar.make(it, "Поле имени должно быть заполнено", Snackbar.LENGTH_LONG).show()
                        return@launch
                    }

                    else {
                        //запрос на получение почты пользователя
                        val userCheck = supabase.from("Пользователи").select(){
                            filter{
                                eq("Email", emailText.text.toString().trim())
                            }
                        }.decodeList<DataUser>().count()

                        //проверка на существование пользователя
                        if (userCheck == 0){
                            supabase.auth.signUpWith(Email) {
                                email = emailText.text.toString().trim()
                                password = passwordText.text.toString().trim()
                            }

                            saveShared()

                            //добавление пользователя в БД
                            val user = supabase.auth.currentUserOrNull()
                            val userAdd = DataUser(
                                id = user?.id.toString(),
                                Имя = nameText.text.toString(),
                                Email = user?.email.toString()
                            )
                            supabase.from("Пользователи").insert(userAdd)

                        val intent = Intent(this@signup, main_menu::class.java)
                        startActivity(intent)
                        }
                        else{
                            Snackbar.make(it, "Такой пользователь уже существует!", Snackbar.LENGTH_LONG).show()
                            return@launch
                        }
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
