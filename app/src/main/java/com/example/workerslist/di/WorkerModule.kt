package com.example.workerslist.di

import com.example.workerslist.data.service.ChildWorkerFactory
import com.example.workerslist.data.service.SaveDataWorker
import com.example.workerslist.di.annotation.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(SaveDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: SaveDataWorker.Factory): ChildWorkerFactory
}