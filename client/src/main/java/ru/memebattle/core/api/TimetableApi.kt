package ru.memebattle.core.api

import io.reactivex.Single
import retrofit2.http.GET
import ru.memebattle.common.dto.schdule.ScheduleDayDto

interface TimetableApi {

    @GET("timetable")
    fun getTimetableList(): Single<List<ScheduleDayDto>>
}