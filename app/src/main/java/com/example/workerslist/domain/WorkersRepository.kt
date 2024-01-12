package com.example.workerslist.domain

import androidx.lifecycle.LiveData

interface WorkersRepository {

    fun addWorker(worker: Worker)

    fun deleteWorker(worker: Worker)

    fun getAllWorker() : LiveData<ArrayList<Worker>>

    suspend fun save()
}