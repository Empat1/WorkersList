package com.example.workerslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workerslist.helper.DateHelper
import com.example.workerslist.domain.WorkerModel
import com.example.workerslist.domain.WorkersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkerViewModel @Inject constructor(
    private val repository: WorkersRepository,
    private val dateHelper: DateHelper
) : ViewModel() {

    fun correctWorker(workerModel: WorkerModel): Boolean{
        return dateHelper.dateCorrect(workerModel.birthday)
    }

    fun addWorker(workerModel: WorkerModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWorker(workerModel)
        }
    }

    fun removeWorker(workerModel: WorkerModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorker(workerModel.id)
        }
    }

}