package ru.memebattle.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import ru.memebattle.Database
import java.nio.file.Files
import java.nio.file.Paths

class DatabaseCreator(private val path: String?) {

    fun create(): Database {
        val driver: SqlDriver = JdbcSqliteDriver(if (path.isNullOrBlank()) {
            JdbcSqliteDriver.IN_MEMORY
        } else {
            if (Files.notExists(Paths.get(path))) {
                Files.createDirectories(Paths.get(path).parent)
                Files.createFile(Paths.get(path))
            }

            "jdbc:sqlite:$path"
        })
        Database.Schema.create(driver)
        return Database(driver)
    }
}