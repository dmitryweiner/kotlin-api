package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }
    val apiService = APIService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = mutableListOf<User>()
        val adapter = RecyclerAdapter(list) {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra(EXTRA_ID, it)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        thread {
            val users = apiService.getAllUsers()
            list.addAll(users)
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}