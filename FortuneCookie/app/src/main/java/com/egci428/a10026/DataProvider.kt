package com.egci428.a10026

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DataProvider {
    //takes the data from new cookie and put it in array object
    private val data = ArrayList<CookieData>()
    fun getData(): ArrayList<CookieData>{
        return data
    }
    init{
        var formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")
        data.add(
            CookieData(
                "You're Lucky",
                "positive"
            )
        )
        data.add(
            CookieData(
                "You will get A",
                "positive"
            )
        )
        data.add(
                    CookieData(
                        "Don't Panic",
                        "negative"
                    )
        )
    }
}