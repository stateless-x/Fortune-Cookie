package com.egci428.a10026

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_cookie.*
import kotlinx.android.synthetic.main.main_row.*
import kotlinx.android.synthetic.main.main_row.view.*
import okhttp3.Cookie
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    var data: ArrayList<CookieData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = DataProvider.getData()
        Log.d("oncreate","data Arraylist:  ${data}")
        val listView = findViewById<ListView>(R.id.main_listview)
        val cookieArrayAdapter = CookieArrayAdapter(this, data!!)

        var returnedData = returnedData()

        //don't add when the object is null
        if(returnedData != CookieData("${null}","${null}")) {
            data!!.add(returnedData)
        }

        Log.d("oncreate","Test:${returnedData}")
        Log.d("oncreate","data after data.add(test):${data}")

        //connect cookie-array adapter to listview
        listView.adapter = cookieArrayAdapter

        button.setOnClickListener {
            val intent = Intent(this, NewCookie::class.java)
            startActivity(intent)
        }
    }

     fun returnedData(): CookieData {
        //retrieve data from newcookie.kt
        val msg = intent.getStringExtra("message")
//        val datelog = intent.getStringExtra("datelog")
        val status = intent.getStringExtra("status")
        var formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")
        var data:CookieData = CookieData("${msg}", "${status}")
        Log.d("getData","$data")
        return data
    }

    private class CookieArrayAdapter(var context: Context, var mCookie: ArrayList<CookieData>) :
        BaseAdapter() {
        override fun getCount(): Int {
            return mCookie.size
        }

        override fun getItem(position: Int): Any {
            return mCookie[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val cookie = mCookie[position]
            val view: View
            var formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")

            if (convertView == null) {
                val layoutInflater = LayoutInflater.from(parent!!.context)
                view = layoutInflater.inflate(R.layout.main_row, parent, false)
                val viewHolder = ViewHolder(view.messageText, view.logText)
                view.tag = viewHolder
            } else {
                view = convertView
            }

            val viewHolder = view.tag as ViewHolder
            viewHolder.messageTextView.text = cookie.message
            viewHolder.logTextView.text = "Date: ${formatter.format(LocalDateTime.now())}"

            Log.d("getView","CookieStatus: ${cookie.status}")
            Log.d("getView","Cookie Array: ${cookie}")
            if(cookie.status=="negative"){
                viewHolder.messageTextView.setTextColor(Color.parseColor("red"))
            }else if(cookie.status=="positive"){
                viewHolder.messageTextView.setTextColor(Color.parseColor("blue"))
            }
            return view
        }
    }
}
    private  class  ViewHolder(val messageTextView:TextView, val logTextView: TextView){
    }
