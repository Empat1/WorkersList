package com.example.workerslist.domain

import androidx.lifecycle.LiveData

interface WorkersRepository {

    suspend fun addWorker(workerModel: WorkerModel)

    suspend fun deleteWorker(workerId: Long)

    fun getAllWorker() : LiveData<List<WorkerModel>>

    suspend fun save()
}