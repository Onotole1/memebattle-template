package ru.memebattle.db.data.schedule

import org.jetbrains.exposed.sql.ResultRow
import ru.memebattle.common.dto.schdule.LessonDto

fun ResultRow.toLessonDto() = LessonDto(
    id = this[Lesson.id],
    name = this[Lesson.name],
    teacherName = this[Lesson.teacherName],
    teacherId = this[Lesson.teacherId],
    classRoom = this[Lesson.classRoom],
    timeStart = this[Lesson.timeStart],
    timeEnd = this[Lesson.timeEnd]
)