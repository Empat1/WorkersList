package com.example.workerslist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.workerslist.data.database.WorkersDao
import com.example.workerslist.data.service.SaveDataWorker
import com.example.workerslist.domain.WorkerMapper
import com.example.workerslist.domain.WorkerModel
import com.example.workerslist.domain.WorkersRepository
import javax.inject.Inject

class WorkersRepositoryImpl @Inject constructor(
    private val workersDao: WorkersDao,
    private val application: Application,
    private val mapper: WorkerMapper
) : WorkersRepository {

    override suspend fun addWorker(workerModel: WorkerModel) {
        workersDao.addWorker(mapper.mapModelToDto(workerModel))
    }
    override suspend fun deleteWorker(workerId: Long) {
        workersDao.deleteWorker(workerId)
    }

    override fun getAllWorker(): LiveData<List<WorkerModel>> =
        workersDao.getAllWorkerVM().map{ its ->
            its.map {mapper.mapDtoToModel(it)}
        }

    override suspend fun save() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            SaveDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            SaveDataWorker.makeRequest()
        )
    }
}