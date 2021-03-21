package com.sattar.githubusers.di

import android.app.Application
import com.sattar.githubusers.ApplicationClass
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        SchedulerModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: ApplicationClass)
}
