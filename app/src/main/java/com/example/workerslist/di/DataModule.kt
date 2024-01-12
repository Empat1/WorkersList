package com.example.workerslist.di

import com.example.workerslist.data.WorkersRepositoryImpl
import com.example.workerslist.di.annotation.ApplicationScope
import com.example.workerslist.domain.WorkersRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindsRepository(impl: WorkersRepositoryImpl) : WorkersRepository
}