package com.example.workerslist.presentation

import androidx.lifecycle.ViewModel
import com.example.workerslist.helper.DateHelper
import com.example.workerslist.domain.Worker
import com.example.workerslist.domain.WorkersRepository
import javax.inject.Inject

class WorkerViewModel @Inject constructor(
    private val repository: WorkersRepository,
    private val dateHelper: DateHelper
) : ViewModel() {

    fun correctWorker(worker: Worker): Boolean{
        return dateHelper.dateCorrect(worker.birthday)
    }

    fun addWorker(worker: Worker){
        repository.addWorker(worker)
    }

    fun removeWorker(worker: Worker){
        repository.deleteWorker(worker)
    }

}