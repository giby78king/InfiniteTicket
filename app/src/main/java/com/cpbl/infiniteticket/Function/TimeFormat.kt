package com.cpbl.infiniteticket

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimeFormat {
    //接受六位以上日期(數字格式)
    //ex. 202012 --> Dec
    fun MonthToAbbreviation(Month: String): String {

        var monthName: String = ""
        when (Month.substring(4, 6)) {
            "01" -> monthName = "Jan"
            "02" -> monthName = "Feb"
            "03" -> monthName = "Mar"
            "04" -> monthName = "Apr"
            "05" -> monthName = "May"
            "06" -> monthName = "Jun"
            "07" -> monthName = "Jul"
            "08" -> monthName = "Aug"
            "09" -> monthName = "Sep"
            "10" -> monthName = "Oct"
            "11" -> monthName = "Nov"
            "12" -> monthName = "Dec"
        }
        return monthName
    }

    inner class TodayDate() {

        @SuppressLint("SimpleDateFormat")
        fun yyyyMMdd(): String {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("yyyyMMdd")

            timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")

            return timeFormat.format(calendar.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun yyyyMMdd(Day: Int): String {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("yyyyMMdd")

            timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")
            calendar.add(Calendar.DATE, Day)

            return timeFormat.format(calendar.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun yyyyMM(): String {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("yyyyMM")

            timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")

            return timeFormat.format(calendar.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun yyyyMM(MONTH: Int): String {
            val calendar = Calendar.getInstance()
            val TimeFormat = SimpleDateFormat("yyyyMM")

            TimeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")
            calendar.add(Calendar.MONTH, MONTH)

            return TimeFormat.format(calendar.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun yyyyMMddHHmmss(): String {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("yyyyMMddHHmmss")

            timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")
            return timeFormat.format(calendar.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun yyyyMMddHHmmss(addSecond: Int): String {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("yyyyMMddHHmmss")
            timeFormat.timeZone = TimeZone.getTimeZone("Asia/Taipei")
            calendar.add(Calendar.SECOND, addSecond)
            return timeFormat.format(calendar.time)
        }
    }


}