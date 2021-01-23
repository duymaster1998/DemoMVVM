package edu.nuce.apps.demo.usecase

import edu.nuce.apps.demo.data.models.User
import edu.nuce.apps.demo.data.repository.UserRepository
import edu.nuce.apps.demo.di.IoScheduler
import edu.nuce.apps.demo.result.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class LoadUserSessionUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoScheduler scheduler: Scheduler
): ObservableUseCase<Long, User>(scheduler) {

    override fun execute(parameters: Long): Observable<Result<User>> {
        return userRepository.getObservableUser(parameters).map {
            when(it) {
                is Result.Success -> Result.Success(it.data)
                is Result.Loading -> Result.Loading
                is Result.Error -> Result.Error(it.throwable)
            }
        }
    }
}