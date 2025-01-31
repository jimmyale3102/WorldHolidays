package dev.alejo.world_holidays.core.uitls

import android.annotation.SuppressLint
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateUtils {
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    private val dateFormat = SimpleDateFormat("EEE dd")
    private val nextHolidayDateFormat = SimpleDateFormat("EEE, MMM dd")
    private val monthDateFormat = SimpleDateFormat("MMM")

    fun getStringDateFromDate(date: Date): String = dateFormatter.format(date)
    fun getDateFromString(dateString: String): Date? = dateFormatter.parse(dateString)
    fun getLocalDateFromString(dateString: String): LocalDate {
        val dateSplit = dateString.split("-")
        return LocalDate(dateSplit[0].toInt(), dateSplit[1].toInt(), dateSplit[2].toInt())
    }
    fun getDayFromDate(date: Date): String = dateFormat.format(date).split(" ")[1]
    fun getDayNameFromDate(date: Date): String = dateFormat.format(date).split(" ")[0]
    fun getMonthNumber(dateString: String): Int = dateString.split("-")[1].toInt()
    fun getYearNumber(dateString: String): Int = dateString.split("-")[0].toInt()
    fun getMonthName(date: Date): String = monthDateFormat.format(date)
    fun getMonthFromDate(date: Date) = dateFormatter.format(date).split("-")[1].toInt()
    fun getYearFromDate(date: Date) = dateFormatter.format(date).split("-")[0].toInt()
    fun getNextHolidayFormatted(dateString: String): String =
        nextHolidayDateFormat.format(getDateFromString(dateString)!!)
    fun getDateAsInt(dateString: String): Int {
        val dateFormatted = dateFormatter.format(getDateFromString(dateString)!!)
        val year = dateFormatted.split("-")[0]
        val month = dateFormatted.split("-")[1]
        val day = dateFormatted.split("-")[2]
        return (year + month + day).toInt()
    }
    fun getTimeFromString(dateString: String): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.SECOND, 1)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.DAY_OF_MONTH, dateString.split("-")[2].toInt())
        calendar.set(Calendar.MONTH, dateString.split("-")[1].toInt() - 1)
        calendar.set(Calendar.YEAR, dateString.split("-")[0].toInt())
        return calendar.timeInMillis
    }
}