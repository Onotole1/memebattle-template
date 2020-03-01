package ru.memebattle.core.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.memebattle.common.dto.game.MemeRequest
import ru.memebattle.common.dto.game.MemeResponse

interface GameApi {

    @GET("game")
    fun getState(): Single<MemeResponse>

    @POST("game")
    fun sendLike(@Body memeRequest: MemeRequest): Single<MemeResponse>
}