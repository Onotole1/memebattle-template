package ru.memebattle.common.dto.schdule

data class ScheduleDayRequestDto(
    val date: String,
    val lessons: List<LessonRequestDto>
)