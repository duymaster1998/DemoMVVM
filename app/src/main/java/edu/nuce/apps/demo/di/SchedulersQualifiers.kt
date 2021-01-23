package edu.nuce.apps.demo.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ComputationScheduler

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoScheduler

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainScheduler