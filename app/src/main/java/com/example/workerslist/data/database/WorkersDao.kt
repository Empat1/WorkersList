package com.example.workerslist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkersDao{

    @Query("SELECT * FROM WORKER_DB")
    fun getAllWorkerVM(): LiveData<List<WorkerDb>>

    @Query("SELECT * FROM WORKER_DB")
    fun getAllWorker(): List<WorkerDb>
    @Query("SELECT * FROM WORKER_DB WHERE ID == :id LIMIT 1")
    fun getWorker(id: Int): WorkerDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorker(worker: WorkerDb)

    @Query("DELETE FROM WORKER_DB WHERE ID == :userId")
    suspend fun deleteWorker(userId: Long)
}