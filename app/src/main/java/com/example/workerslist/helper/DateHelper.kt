package com.example.workerslist.helper

import com.example.workerslist.di.annotation.ApplicationScope
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@ApplicationScope
class DateHelper @Inject constructor(){
    private val dateFormat = DateTimeFormatter.ofPattern(pattern)

    fun dateCorrect(dateStr: String): Boolean {
        return try {
            val a = LocalDate.parse(dateStr, dateFormat)
            a < LocalDate.now()
        }catch (e: DateTimeParseException){
            false
        }
    }

    companion object {
        const val pattern = "dd-MM-yyyy"
    }
}