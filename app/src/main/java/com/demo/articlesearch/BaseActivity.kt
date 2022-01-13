package com.demo.articlesearch

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {

    companion object {
        var createToast: Toast? = null

        fun showToast(context: Context?, message: String) {
            if (context != null) {
                try {
                    if (createToast != null) createToast!!.cancel()
                    createToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                    createToast?.show()
                } catch (ignored: Exception) {
                }
            }
        }

        fun changeTimeDate(time: String, pattern: String): String {
            return try {
                val inputTimeFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                var date: Date? = null
                try {
                    date = inputTimeFormat.parse(time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputTimeFormat = SimpleDateFormat(pattern, Locale.US)
                outputTimeFormat.format(date)
            } catch (e: Exception) {
                time
            }
        }

        fun changeTime(time: String, pattern: String): String {
            return try {
                val inputTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
                var date: Date? = null
                try {
                    date = inputTimeFormat.parse(time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val outputTimeFormat = SimpleDateFormat(pattern, Locale.US)
                outputTimeFormat.format(date)
            } catch (e: Exception) {
                time
            }
        }
    }
}