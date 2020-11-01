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
        var cookieArrayAdapter = CookieArrayAdapter(this, data!!)
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

            var cookie = mCookie[position]
            var view: View //data from each row
            var formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")
            //inflate the row in the listview
            if (convertView == null) {
                val layoutInflater = LayoutInflater.from(parent!!.context)
                view = layoutInflater.inflate(R.layout.main_row, parent, false)
                val viewHolder = ViewHolder(view.messageText, view.logText)
                view.tag = viewHolder
            } else {
                view = convertView
            }

            var viewHolder = view.tag as ViewHolder
            //display text in each row
            viewHolder.messageTextView.text = cookie.message
            viewHolder.logTextView.text = "Date: ${formatter.format(LocalDateTime.now())}"

            Log.d("getView","CookieStatus: ${cookie.status}")
            Log.d("getView","Cookie Array: ${cookie}")

            //condition if status is positive/negative
            if(cookie.status=="negative"){
                viewHolder.messageTextView.setTextColor(Color.parseColor("red"))
            }else if(cookie.status=="positive"){
                viewHolder.messageTextView.setTextColor(Color.parseColor("blue"))
            }

            view.setOnClickListener(){
                mCookie.removeAt(position)
                notifyDataSetChanged()
                Log.d("viewclick","view clicked: ${position}")
//                Log.d("viewclick","view clicked: ${mCookie[position]}")
            }
            return view
        }


    }
}
    private  class  ViewHolder(val messageTextView:TextView, val logTextView: TextView){
    }
