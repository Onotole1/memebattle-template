package ru.memebattle.repository

import kotlinx.coroutines.sync.Mutex
import ru.memebattle.db.data.toModel
import ru.memebattle.model.UserModel
import ru.memebattle.sqldelight.data.User
import ru.memebattle.sqldelight.data.UserQueries

class UserRepositoryImpl(private val userQueries: UserQueries) : UserRepository {
    private var nextId = 1L
    private val items = mutableListOf<UserModel>()
    private val mutex = Mutex()

    override suspend fun getById(id: Long): UserModel? =
        userQueries.selectUserById(id).executeAsOneOrNull()?.let(User::toModel)

    override suspend fun getByIds(ids: Collection<Long>): List<UserModel> =
        userQueries.selectUsersByIds(ids).executeAsList().map(User::toModel)

    override suspend fun getByUsername(username: String): UserModel? =
        userQueries.selectUserByName(username).executeAsOneOrNull()?.let(User::toModel)

    override suspend fun save(item: UserModel): Unit =
        userQueries.insertUser(item.username, item.password)
}

