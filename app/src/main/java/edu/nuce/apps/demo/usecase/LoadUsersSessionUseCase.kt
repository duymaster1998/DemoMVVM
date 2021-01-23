package edu.nuce.apps.demo.usecase

import edu.nuce.apps.demo.data.models.User
import edu.nuce.apps.demo.data.repository.UserRepository
import edu.nuce.apps.demo.di.IoScheduler
import edu.nuce.apps.demo.result.Result
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class LoadUsersSessionUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoScheduler scheduler: Scheduler
) : ObservableUseCase<Unit, List<User>>(scheduler) {

    override fun execute(parameters: Unit): Observable<Result<List<User>>> {
        return userRepository.getObservableUsers().map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data)
                is Result.Error -> Result.Error(result.throwable)
                is Result.Loading -> Result.Loading
            }
        }
    }

}