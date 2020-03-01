package ru.memebattle.exception

import java.lang.RuntimeException

class UserExistsException(message: String): RuntimeException(message)