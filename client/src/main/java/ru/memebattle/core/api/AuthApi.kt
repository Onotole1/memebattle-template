package ru.memebattle.core.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.memebattle.common.dto.AuthenticationRequestDto
import ru.memebattle.common.dto.AuthenticationResponseDto

interface AuthApi {

    @POST("registration")
    fun signUp(@Body authenticationRequestDto: AuthenticationRequestDto): Single<AuthenticationResponseDto>

    @POST("authentication")
    fun signIn(@Body authenticationRequestDto: AuthenticationRequestDto): Single<AuthenticationResponseDto>
}