package ru.memebattle.service

import ru.memebattle.common.dto.schdule.ScheduleDayDto
import ru.memebattle.common.dto.schdule.ScheduleDayRequestDto
import ru.memebattle.repository.ScheduleRepository

class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    suspend fun getAll(): List<ScheduleDayDto> = scheduleRepository.getAll()

    suspend fun insert(schedule: ScheduleDayRequestDto): Unit = scheduleRepository.insert(schedule)
}