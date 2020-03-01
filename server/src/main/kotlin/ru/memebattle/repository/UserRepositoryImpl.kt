package ru.memebattle.repository

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import ru.memebattle.db.data.user.Users
import ru.memebattle.db.data.user.toUser
import ru.memebattle.db.dbQuery
import ru.memebattle.model.UserModel

class UserRepositoryImpl : UserRepository {

    override suspend fun getById(id: Long): UserModel? =
        dbQuery {
            Users.select {
                (Users.id eq id)
            }.singleOrNull()?.toUser()
        }

    override suspend fun getByIds(ids: Collection<Long>): List<UserModel> =
        dbQuery {
            Users.select {
                (Users.id inList ids)
            }.map {
                it.toUser()
            }
        }

    override suspend fun getByUsername(username: String): UserModel? =
        dbQuery {
            Users.select {
                (Users.username eq username)
            }.singleOrNull()?.toUser()
        }

    override suspend fun save(item: UserModel) {
        dbQuery {
            Users.insert { insertStatement ->
                insertStatement[password] = item.password
                insertStatement[username] = item.username
            }
        }
    }
}

