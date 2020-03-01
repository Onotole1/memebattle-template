package ru.memebattle.common.dto.game

data class MemeResponse(val state: String, val memes: List<String>, val likes: List<Int>)