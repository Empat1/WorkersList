package com.example.workerslist.di

import androidx.lifecycle.ViewModel
import com.example.workerslist.di.annotation.ViewModelKey
import com.example.workerslist.presentation.ListWorkerViewModel
import com.example.workerslist.presentation.WorkerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ListWorkerViewModel::class)
    @Binds
    fun bindListWorkerViewModel(impl: ListWorkerViewModel): ViewModel

    @IntoMap
    @ViewModelKey(WorkerViewModel::class)
    @Binds
    fun bindWorkerViewModel(impl: WorkerViewModel): ViewModel

}