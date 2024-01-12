package com.example.workerslist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.workerslist.domain.WorkersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListWorkerViewModel @Inject constructor(
    private val repository: WorkersRepository
) : ViewModel(){

    val isLoading = MutableLiveData(false)

    fun getWorker() = repository.getAllWorker().map {
        isLoading.value = true
        it
    }

    fun save() {
        viewModelScope.launch {
            repository.save()
        }
    }
}