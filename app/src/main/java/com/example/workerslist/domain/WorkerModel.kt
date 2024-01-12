package com.example.workerslist.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkerModel(
    val id: Long = DEFAULT_VALUE,
    val fio : String,
    val birthday: String,
    val jobRole: String
) : Parcelable{

    companion object{
        const val DEFAULT_VALUE = 0L
    }
}