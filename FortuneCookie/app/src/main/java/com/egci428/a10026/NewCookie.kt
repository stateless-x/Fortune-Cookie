package com.egci428.a10026

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_cookie.*
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
        wishBtn.setOnClickListener {
            fetchJson()
            saveData()
        }
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
                var formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy :mm a")
                dateText.text = "Date: ${formatter.format(LocalDateTime.now())}"
                wishBtn.text = "Save"
                imageView.setImageResource(R.drawable.opened_cookie)
            }
        }
        asyncTask.execute(jsonURL)
    }

    fun saveData(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        //append data to arrayList
    }

}