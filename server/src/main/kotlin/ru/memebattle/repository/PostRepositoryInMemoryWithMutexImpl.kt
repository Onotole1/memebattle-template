package ru.memebattle.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.memebattle.model.PostModel

class PostRepositoryInMemoryWithMutexImpl : PostRepository {
    private var nextId = 1L
    private val items = mutableListOf<PostModel>()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> {
        mutex.withLock {
            return items.reversed()
        }
    }

    override suspend fun getById(id: Long): PostModel? {
        mutex.withLock {
            return items.find { it.id == id }
        }
    }

    override suspend fun save(item: PostModel): PostModel {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == item.id }) {
                -1 -> {
                    val copy = item.copy(id = nextId++)
                    items.add(copy)
                    copy
                }
                else -> {
                    val copy = items[index].copy(author = item.author, content = item.content)
                    items[index] = copy
                    copy
                }
            }
        }
    }

    override suspend fun removeById(id: Long) {
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(likes = item.likes + 1)
                    try {
                        items[index] = copy
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        println("size: ${items.size}")
                        println(index)
                    }
                    copy
                }
            }
        }
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

