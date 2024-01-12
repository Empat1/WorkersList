package com.example.workerslist.di

import android.app.Application
import com.example.workerslist.data.WorkersRepositoryImpl
import com.example.workerslist.data.database.AppDatabase
import com.example.workerslist.data.database.WorkersDao
import com.example.workerslist.di.annotation.ApplicationScope
import com.example.workerslist.domain.WorkersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindsRepository(impl: WorkersRepositoryImpl) : WorkersRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideWorkerDao(
            application: Application
        ): WorkersDao {
            return AppDatabase.getInstance(application).workerDao()
        }
    }
}