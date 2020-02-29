package ru.memebattle.core.api

import io.reactivex.Single
import retrofit2.http.GET
import ru.memebattle.common.dto.PostResponseDto

interface NewsApi {

    @GET("posts")
    fun getPosts(): Single<List<PostResponseDto>>
}