package ru.memebattle.db.data

import ru.memebattle.model.UserModel
import ru.memebattle.sqldelight.data.User

fun User.toModel() = UserModel(id, username, password)