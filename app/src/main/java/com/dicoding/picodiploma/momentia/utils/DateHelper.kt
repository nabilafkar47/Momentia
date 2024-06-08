package com.dicoding.picodiploma.momentia.utils

import com.dicoding.picodiploma.momentia.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class DateHelper(private val context: Context) {

    private val ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private val DISPLAY_PATTERN = "dd MMMM yyyy, HH:mm"
    private val locale: Locale
        @RequiresApi(Build.VERSION_CODES.N)
        get() = context.resources.configuration.locales[0]

    @RequiresApi(Build.VERSION_CODES.N)
    fun formatToDisplay(iso8601String: String): String {
        val inputFormat = SimpleDateFormat(ISO_8601_PATTERN, Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat(DISPLAY_PATTERN, locale)
        outputFormat.timeZone = TimeZone.getDefault()

        val parsedDate = inputFormat.parse(iso8601String)
        return outputFormat.format(parsedDate ?: Date())
    }

    fun getTimeAgo(iso8601String: String): String {
        val inputFormat = SimpleDateFormat(ISO_8601_PATTERN, Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val createdTime = inputFormat.parse(iso8601String)?.time ?: return context.getString(R.string.unknown_time)
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - createdTime

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "· $seconds ${context.getString(R.string.seconds)}"
            minutes < 60 -> "· $minutes ${context.getString(R.string.minutes)}"
            hours < 24 -> "· $hours ${context.getString(R.string.hours)}"
            else -> "· $days ${context.getString(R.string.days)}"
        }
    }
}