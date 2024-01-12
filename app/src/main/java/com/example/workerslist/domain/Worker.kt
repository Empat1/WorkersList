package com.example.workerslist.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Worker(
    val fio : String,
    val birthday: String,
    val jobRole: String
) : Parcelable