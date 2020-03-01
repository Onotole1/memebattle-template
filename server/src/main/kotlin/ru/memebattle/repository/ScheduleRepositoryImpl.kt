package ru.memebattle.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
import java.text.SimpleDateFormat
import java.util.*

class ScheduleRepositoryImpl : ScheduleRepository {

    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)

    private val mutex = Mutex()

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
            val scheduleDayDate = item.date.parseDate()
            val id = transaction {
                Schedule.insert { insertStatement ->
                    insertStatement[date] = scheduleDayDate
                } get Schedule.id
            }


            item.lessons.forEach { lessonDto ->
                val timeStartParsed = lessonDto.timeStart.parseDate()
                val timeEndParsed = lessonDto.timeEnd.parseDate()
                Lesson.insert { insertStatement ->
                    insertStatement[classRoom] = lessonDto.classRoom
                    insertStatement[name] = lessonDto.name
                    insertStatement[teacherName] = lessonDto.teacherName
                    insertStatement[teacherId] = lessonDto.teacherId
                    insertStatement[timeStart] = timeStartParsed
                    insertStatement[timeEnd] = timeEndParsed
                    insertStatement[scheduleId] = id
                }
                Unit
            }
        }

    private suspend fun String.parseDate(): Long =
        mutex.withLock {
            format.parse(this).time
        }
}