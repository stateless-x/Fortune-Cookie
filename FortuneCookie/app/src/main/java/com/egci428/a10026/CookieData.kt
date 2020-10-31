package com.egci428.a10026

import java.time.format.DateTimeFormatter
import java.util.*

data class CookieData(val message:String, val status:String, val datelog: String){

    override fun toString(): String {
        return message
    }
}