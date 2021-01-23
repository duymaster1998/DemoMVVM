package edu.nuce.apps.demo.data.network

import edu.nuce.apps.demo.data.models.User
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    fun getUsers(): Observable<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Long): Single<User>
}