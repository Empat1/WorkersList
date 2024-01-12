package com.example.workerslist

import android.app.Application
import androidx.work.Configuration
import com.example.workerslist.data.service.WorkersFactory
import com.example.workerslist.di.DaggerApplicationComponent
import javax.inject.Inject

class ApplicationApp : Application(), Configuration.Provider{

    @Inject
    lateinit var workerFactory: WorkersFactory

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}