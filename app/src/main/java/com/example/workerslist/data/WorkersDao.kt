package com.example.workerslist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.workerslist.di.annotation.ApplicationScope
import com.example.workerslist.domain.Worker
import javax.inject.Inject

@ApplicationScope
class WorkersDao @Inject constructor(){

    private val localSaveWorker: MutableLiveData<ArrayList<Worker>> = MutableLiveData<ArrayList<Worker>>()

    fun addWorkers(workers: List<Worker>){
        localSaveWorker.postValue(workers as ArrayList<Worker>?)
    }

    fun addWorker(worker: Worker){
        localSaveWorker.value?.add(worker)
    }

    fun deleteWorker(worker: Worker){
        localSaveWorker.value?.remove(worker)
    }

    fun getAllWorker(): LiveData<ArrayList<Worker>> =
        localSaveWorker
}