package ru.memebattle.service

import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.core.readText
import kotlinx.io.streams.asInput
import ru.memebattle.common.dto.game.MemeResponse
import ru.memebattle.model.vk.model.*
import java.nio.file.Files
import java.nio.file.Paths

class MemeService {

    private var currentMemes: List<String> = emptyList()
    private var currentLikes: MutableList<Int> = mutableListOf(
        0, 0
    )
    private var state: String = "start"
    private val mutex = Mutex()

    init {
        GlobalScope.launch {
            startRound()
        }
    }

    suspend fun getCurrentState(): MemeResponse =
        mutex.withLock {
            MemeResponse(state, currentMemes, currentLikes)
        }

    suspend fun rateMeme(memeIndex: Int): MemeResponse =
        mutex.withLock {
            currentLikes[memeIndex] = currentLikes[memeIndex].inc()
            MemeResponse(state, currentMemes, currentLikes)
        }

    private suspend fun startRound() {

        withContext(Dispatchers.Default) {

            while (true) {
                val memes = getMemes()

                val pairs = mutex.withLock {
                    val photos = memes.response?.items?.map {
                        it.attachments?.firstOrNull()?.photo?.sizes?.getMaxImage()
                    }

                    val pairs: MutableList<Pair<String, String>> = mutableListOf()
                    val photosNotNull = photos.orEmpty().filterNotNull()
                    for (s in 0..photosNotNull.size step 2) {
                        if (s.inc() <= photosNotNull.lastIndex) {
                            pairs.add(photosNotNull[s] to photosNotNull[s.inc()])
                        }
                    }
                    pairs
                }

                pairs.forEach {

                    mutex.withLock {
                        state = "memes"

                        currentMemes = listOf(it.first, it.second)
                    }

                    delay(10000)

                    mutex.withLock {
                        state = "result"
                    }

                    delay(5000)

                    mutex.withLock {
                        currentLikes = mutableListOf(0, 0)
                    }
                }
            }
        }
    }

    private suspend fun getMemes(): VKResponse =
        withContext(Dispatchers.IO) {
            Gson().fromJson(requireNotNull(ClassLoader.getSystemResourceAsStream("vk.json")).asInput().readText(), VKResponse::class.java)
        }
}