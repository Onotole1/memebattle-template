package ru.memebattle.repository

import ru.memebattle.common.dto.schdule.ScheduleDayDto
import ru.memebattle.common.dto.schdule.ScheduleDayRequestDto

interface ScheduleRepository {

    //suspend fun getByDay(year: Int, day: Int): ScheduleDayDto?
    suspend fun getAll(): List<ScheduleDayDto>
    suspend fun insert(item: ScheduleDayRequestDto)
}