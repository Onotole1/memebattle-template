package ru.memebattle.repository

import ru.memebattle.model.UserModel

interface UserRepository {
    suspend fun getById(id: Long): UserModel?
    suspend fun getByIds(ids: Collection<Long>): List<UserModel>
    suspend fun getByUsername(username: String): UserModel?
    suspend fun save(item: UserModel)
}