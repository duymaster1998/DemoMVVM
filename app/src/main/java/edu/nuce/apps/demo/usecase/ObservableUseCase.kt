package edu.nuce.apps.demo.usecase

import edu.nuce.apps.demo.result.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

abstract class ObservableUseCase<in P, R>(private val scheduler: Scheduler) {

    operator fun invoke(parameters: P): Observable<Result<R>> = execute(parameters)
        .onErrorReturn { e -> Result.Error(e) }
        .startWithItem(Result.Loading)
        .subscribeOn(scheduler)

    protected abstract fun execute(parameters: P): Observable<Result<R>>
}