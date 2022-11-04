package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.concurrent.thread

class UserActivity : AppCompatActivity() {
    val apiService = APIService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val name = findViewById<TextView>(R.id.textViewName)
        val email = findViewById<TextView>(R.id.textViewEmail)
        val username = findViewById<TextView>(R.id.textViewUsername)
        val website = findViewById<TextView>(R.id.textViewWebsite)
        val phone = findViewById<TextView>(R.id.textViewPhone)

        val id = intent.getIntExtra(MainActivity.EXTRA_ID, 0)
        thread {
            val user = apiService.getUserById(id)
            runOnUiThread {
                name.text = user?.name
                username.text = user?.username
                email.text = user?.email
                website.text = user?.website
                phone.text = user?.phone
            }
        }
    }
}