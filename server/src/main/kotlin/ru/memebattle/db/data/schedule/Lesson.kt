package ru.memebattle.db.data.schedule

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Lesson : Table() {
    val id: Column<Long>  = long("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar(name = "name", length = 100)
    val teacherName: Column<String> = varchar(name = "teachername", length = 100)
    val teacherId: Column<Long> = long(name = "teacherid")
    val classRoom: Column<String> = varchar(name = "classroom", length = 100)
    val timeStart: Column<Long> = long(name = "timestart")
    val timeEnd: Column<Long> = long(name = "timeend")
    val scheduleId = long(name = "scheduleid").references(Schedule.id, onDelete = ReferenceOption.CASCADE)
}