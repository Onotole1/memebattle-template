package ru.memebattle.repository

import ru.memebattle.common.dto.schdule.ScheduleDayDto
import ru.memebattle.common.dto.schdule.ScheduleDayRequestDto

interface ScheduleRepository {

    suspend fun getAll(): List<ScheduleDayDto>
    suspend fun insert(item: ScheduleDayRequestDto)
}