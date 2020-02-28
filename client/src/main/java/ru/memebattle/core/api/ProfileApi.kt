package ru.memebattle.core.api

import io.reactivex.Single
import retrofit2.http.GET
import ru.memebattle.common.dto.UserResponseDto

interface ProfileApi {

    @GET("me")
    fun getProfile(): Single<UserResponseDto>
}