package com.example.workerslist.di

import android.app.Application
import com.example.workerslist.ApplicationApp
import com.example.workerslist.di.annotation.ApplicationScope
import com.example.workerslist.presentation.ListWorkersFragment
import com.example.workerslist.presentation.MainActivity
import com.example.workerslist.presentation.WorkerFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: ListWorkersFragment)
    fun inject(activity: WorkerFragment)
    fun inject(application: ApplicationApp)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(@BindsInstance application: Application) : ApplicationComponent
    }
}