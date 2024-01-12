package com.example.workerslist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.workerslist.data.service.SaveDataWorker
import com.example.workerslist.domain.Worker
import com.example.workerslist.domain.Workers
import com.example.workerslist.domain.WorkersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

class WorkersRepositoryImpl @Inject constructor(
    private val fileDataManager: FileDataManager,
    private val jsonDataManager: JsonDataManager,
    private val workersDao: WorkersDao,
    private val application: Application
) : WorkersRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
      scope.launch(Dispatchers.IO) {
          load()
      }
    }

    private suspend fun load(){
        val listWorker = jsonDataManager.importFromJSON(fileDataManager.readFile(),  Workers::class.java)

        if(listWorker == null)
            workersDao.addWorkers(ArrayList())
        else
            workersDao.addWorkers(listWorker.worker)
    }

    override fun addWorker(worker: Worker) {
        workersDao.addWorker(worker)
    }
    override fun deleteWorker(worker: Worker) {
        workersDao.deleteWorker(worker)
    }

    override fun getAllWorker(): LiveData<ArrayList<Worker>> =
        workersDao.getAllWorker()

    override suspend fun save() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            SaveDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            SaveDataWorker.makeRequest()
        )
    }
}