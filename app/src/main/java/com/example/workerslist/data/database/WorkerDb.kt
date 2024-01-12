package com.example.workerslist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "WORKER_DB")
data class WorkerDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("FIO")
    val fio: String,
    @SerializedName("BIRTHDAY")
    val birthday: String,
    @SerializedName("JOB_ROLE")
    val jobRole: String
) {

}