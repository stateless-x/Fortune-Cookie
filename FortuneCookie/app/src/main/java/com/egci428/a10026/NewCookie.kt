package com.egci428.a10026

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_cookie.*
import kotlinx.android.synthetic.main.main_row.*
import okhttp3.Cookie
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NewCookie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cookie)
        supportActionBar!!.setTitle("                 Fortune Cookie")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        wishBtn.setOnClickListener {
            fetchJson()
        }
    }

    fun saveData(cookieData: CookieData){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", cookieData.message)
//        intent.putExtra("datelog", "Date: ${cookieData.datelog}")
        intent.putExtra("status", cookieData.status)
        startActivity(intent)
        Log.d("saveData","saveData called successfully")
    }

    fun fetchJson(){
        val num = kotlin.random.Random.nextInt(0, 9).toString()
        val jsonURL = "https://egco428-json.firebaseio.com/fortunecookies/"+num+".json"

        val client = OkHttpClient()
        var asyncTask = object : AsyncTask<String, String, String>(){
            override fun onPreExecute(){
                super.onPreExecute()
                Toast.makeText(this@NewCookie,"Call Success",Toast.LENGTH_SHORT).show()
            }

            override fun doInBackground(vararg arg: String?): String {
                val builder = Request.Builder()
                builder.url(arg[0].toString())
                val request = builder.build()
                try {
                    val response = client.newCall(request).execute()
                    return response.body!!.string()
                }catch (e: Exception){
                    e.printStackTrace()
                }
                return ""
            }

            override fun onPostExecute(result: String?) {
                val cookie = Gson().fromJson(result, CookieData::class.java)
                resultText.text = "Result: ${cookie.message}"
                var formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")
                dateText.text = "Date: ${formatter.format(LocalDateTime.now())}"
                wishBtn.text = "Save"
                imageView.setImageResource(R.drawable.opened_cookie)
                wishBtn.setOnClickListener {
                    Log.d("onPostExe","${cookie}")
                    saveData(cookie)
                }

            }
        }
        asyncTask.execute(jsonURL)
    }



}