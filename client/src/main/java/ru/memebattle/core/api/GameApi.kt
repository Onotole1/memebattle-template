package ru.memebattle.core.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GameApi {

    @GET("game")
    fun getState(): Single<>

    @POST("game")
    fun sendLike(@Body ): Single<>
}