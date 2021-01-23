package edu.nuce.apps.demo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

@InstallIn(ApplicationComponent::class)
@Module
object SchedulersModule {
    @ComputationScheduler
    @Provides
    fun providesComputationScheduler(): Scheduler = Schedulers.computation()

    @IoScheduler
    @Provides
    fun providesIoScheduler(): Scheduler = Schedulers.io()

    @MainScheduler
    @Provides
    fun providesMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}