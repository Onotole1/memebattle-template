package ru.memebattle.core.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.memebattle.common.dto.AuthenticationRequestDto
import ru.memebattle.common.dto.AuthenticationResponseDto
import ru.memebattle.common.dto.report.ReportDto

interface ReportApi {

    @POST("report")
    fun report(@Body reportDto: ReportDto): Completable

}