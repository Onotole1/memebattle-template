package ru.memebattle.db.data.schedule

import org.jetbrains.exposed.sql.ResultRow
import ru.memebattle.common.dto.schdule.LessonDto
import ru.memebattle.common.dto.schdule.ScheduleDayDto

fun ResultRow.toScheduleDayDto(lessons: List<LessonDto>) = ScheduleDayDto(
    id = this[Schedule.id],
    date = this[Schedule.date],
    lessons = lessons
)