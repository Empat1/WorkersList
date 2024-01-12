package com.example.workerslist.data

import com.example.workerslist.helper.DateHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class JsonDataManager@Inject constructor() {

    @Inject
    lateinit var mapper: DateHelper

    fun <T> exportToJSON(data: T): String {
        val gson = Gson()
        return gson.toJson(data)
    }

    fun <T> importFromJSON(dataString: String, type: Class<T>): T? {
        try {
            if (type == String::class.java) return dataString as T?
            val gson = GsonBuilder().setDateFormat(DateHelper.pattern).create()
            return gson.fromJson(dataString, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}