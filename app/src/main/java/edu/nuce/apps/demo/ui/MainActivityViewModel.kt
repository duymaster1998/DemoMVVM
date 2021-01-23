package edu.nuce.apps.demo.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import edu.nuce.apps.demo.data.models.User
import edu.nuce.apps.demo.result.Event
import edu.nuce.apps.demo.result.Result
import edu.nuce.apps.demo.result.data
import edu.nuce.apps.demo.result.successOr
import edu.nuce.apps.demo.usecase.LoadUserSessionUseCase
import edu.nuce.apps.demo.usecase.LoadUsersSessionUseCase
import edu.nuce.apps.demo.util.toLiveData

class MainActivityViewModel @ViewModelInject constructor(
    loadUsersSessionUseCase: LoadUsersSessionUseCase,
    private val loadUserSessionUseCase: LoadUserSessionUseCase
) : ViewModel(), EventActions {

    private val loadUsersResult: LiveData<Result<List<User>>> = loadUsersSessionUseCase(Unit).toLiveData()

    val loading: LiveData<Boolean> = loadUsersResult.map { it == Result.Loading }

    val usersUiData: LiveData<List<User>> = loadUsersResult.map { it.successOr(emptyList()) }

    private val userId = MutableLiveData<Long>()

    private val _navigateToUserActivity = MutableLiveData<Event<Long>>()
    val navigateToUserActivity: LiveData<Event<Long>> = _navigateToUserActivity

    private val loadUserResult: LiveData<Result<User>> = userId.switchMap { userId ->
        loadUserSessionUseCase(userId).toLiveData()
    }

    private val _errorMessage = MediatorLiveData<Event<String>>().apply {
        addSource(loadUsersResult) { result ->
            if (result is Result.Error) {
                value = Event(content = result.throwable.message ?: "Error")
            }
        }
        addSource(loadUserResult) { result ->
            if (result is Result.Error) {
                value = Event(content = result.throwable.message ?: "Error")
            }
        }
    }
    val errorMessage: LiveData<Event<String>> = _errorMessage

    val userUiData: LiveData<User?> = loadUserResult.map { it.data }

    fun setUserId(id: Long) { userId.value = id }

    override fun onItemClicked(item: User) {
        _navigateToUserActivity.value = Event(item.id)
    }
}