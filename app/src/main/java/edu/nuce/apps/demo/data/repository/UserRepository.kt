package edu.nuce.apps.demo.data.repository

import edu.nuce.apps.demo.data.models.User
import edu.nuce.apps.demo.data.network.ApiService
import edu.nuce.apps.demo.result.Result
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
): SessionUserRepository {

    override fun getObservableUsers(): Observable<Result<List<User>>> {
        return apiService.getUsers()
            .map { users -> Result.Success(users) }
    }

    override fun getObservableUser(id: Long): Observable<Result<User>> {
        return apiService.getUserById(id)
            .toObservable()
            .map { user -> Result.Success(user) }
    }

}

interface SessionUserRepository {
    fun getObservableUsers(): Observable<Result<List<User>>>
    fun getObservableUser(id: Long): Observable<Result<User>>
}