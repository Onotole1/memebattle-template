package ru.memebattle.db.data.schedule

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Schedule : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val date: Column<Long> = long("date")
}