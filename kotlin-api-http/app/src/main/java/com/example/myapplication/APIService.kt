package com.example.myapplication

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

class APIService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    private fun getContent(url: String, timeout: Int = 10000): String? {
        var c: HttpURLConnection? = null
        try {
            val u = URL(url)
            c = u.openConnection() as HttpURLConnection
            c.setRequestMethod("GET")
            c.setRequestProperty("Content-length", "0")
            c.setUseCaches(false)
            c.setAllowUserInteraction(false)
            c.setConnectTimeout(timeout)
            c.setReadTimeout(timeout)
            c.connect()
            val status: Int = c.getResponseCode()
            when (status) {
                200, 201 -> {
                    val streamReader = InputStreamReader(c.inputStream)
                    var text = ""
                    streamReader.use {
                        text = it.readText()
                    }
                    return text
                }
            }
        } catch (ex: MalformedURLException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } finally {
            if (c != null) {
                try {
                    c.disconnect()
                } catch (ex: Exception) {
                    Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
                }
            }
        }
        return null
    }

    fun getAllUsers(): List<User> {
        val result = mutableListOf<User>()
        val response = getContent(
            "${BASE_URL}/users",
            1000
        )
        var jsonArray: JSONArray? = null
        try {
            jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray[i] as JSONObject
                val user = covertToUser(jsonObject)
                result.add(user)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return result
    }

    fun getUserById(id: Int): User? {
        val response = getContent(
            "${BASE_URL}/users/$id",
            1000
        )
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(response)
            val user = covertToUser(jsonObject)
            return user
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun covertToUser(jsonObject: JSONObject): User {
        return User(
            jsonObject.getInt("id"),
            jsonObject.getString("name"),
            jsonObject.getString("username"),
            jsonObject.getString("email"),
            jsonObject.getString("phone"),
            jsonObject.getString("website"),
        )
    }
}