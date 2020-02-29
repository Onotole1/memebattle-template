package ru.memebattle.repository

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.memebattle.common.dto.schdule.ScheduleDayDto
import ru.memebattle.common.dto.schdule.ScheduleDayRequestDto
import ru.memebattle.db.data.schedule.Lesson
import ru.memebattle.db.data.schedule.Schedule
import ru.memebattle.db.data.schedule.toLessonDto
import ru.memebattle.db.data.schedule.toScheduleDayDto
import ru.memebattle.db.dbQuery

class ScheduleRepositoryImpl : ScheduleRepository {

    override suspend fun getAll(): List<ScheduleDayDto> =
        dbQuery {
            Schedule.selectAll().map { scheduleResult ->
                val schedules = Lesson.select {
                    (Lesson.scheduleId eq scheduleResult[Schedule.id])
                }.map {
                    it.toLessonDto()
                }

                scheduleResult.toScheduleDayDto(schedules)
            }
        }

    override suspend fun insert(item: ScheduleDayRequestDto): Unit =
        dbQuery {
            val id = transaction {
                Schedule.insert { insertStatement ->
                    insertStatement[date] = item.date
                } get Schedule.id
            }

            item.lessons.forEach { lessonDto ->
                Lesson.insert { insertStatement ->
                    insertStatement[classRoom] = lessonDto.classRoom
                    insertStatement[name] = lessonDto.name
                    insertStatement[teacherName] = lessonDto.teacherName
                    insertStatement[teacherId] = lessonDto.teacherId
                    insertStatement[timeStart] = lessonDto.timeStart
                    insertStatement[timeEnd] = lessonDto.timeEnd
                    insertStatement[scheduleId] = id
                }
            }
        }
}