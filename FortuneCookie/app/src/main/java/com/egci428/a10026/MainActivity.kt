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
import kotlinx.android.synthetic.main.main_row.view.*
import okhttp3.Cookie
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    var data: ArrayList<CookieData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = DataProvider.getData()

        val listView = findViewById<ListView>(R.id.main_listview)
        val cookieArrayAdapter = CookieArrayAdapter(this, data!!)
//        val cookieArrayAdapter = MyCustomAdapter(this)
        //connect cookie-array adapter to listview
        listView.adapter = cookieArrayAdapter

        val msg = intent.getStringExtra("message")
        val datelog = intent.getStringExtra("datelog")
        val status = intent.getStringExtra("status")

        data!!.add(CookieData("${msg}", "${status}", "${datelog}"))

        button.setOnClickListener {
            val intent = Intent(this, NewCookie::class.java)
            startActivity(intent)
        }
    }

    private fun displayData() {
        //retrieve data from newcookie.kt
        val msg = intent.getStringExtra("message")
        val datelog = intent.getStringExtra("datelog")
        val status = intent.getStringExtra("status")
        Log.d("test", "${msg},${datelog},${status}")
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
            viewHolder.logTextView.text = cookie.datelog
            return view
        }
    }
}
    private  class  ViewHolder(val messageTextView:TextView, val logTextView: TextView){

    }

//    private class MyCustomAdapter(context: Context) : BaseAdapter() {
//        //represent layout .xml
//        private val mContext: Context
//        private val cookielist = arrayListOf<CookieData>()
//        init {
//            this.mContext = context
//        }
//        //responsible for rendering out each row
//        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
//            val layoutInflater = LayoutInflater.from(mContext)
//            val mainRow = layoutInflater.inflate(R.layout.main_row, viewGroup, false)
//
//            val messageTextView = mainRow.findViewById<TextView>(R.id.messageText)
//            val logTextView = mainRow.findViewById<TextView>(R.id.logText)
//
////            messageTextView.text = cookielist[position].message
////            messageTextView.text = "vasco"
//            //add date from the list here
//            logTextView.text = "date: ${null}"
//            return mainRow
//        }
//
//        override fun getItem(position: Int): Any {
//            return ""
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        //responsible for how many row in the list
//        override fun getCount(): Int {
////            return cookielist.size
//            return 5
//        }
//    }

