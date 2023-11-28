package com.example.demo.strategy

import org.springframework.stereotype.Service

interface GoodUserStoryService {
    fun <T> getBacklog(converter: UserStoryConverter<T>): T
}

@Service
class DefaultGoodUserStoryService(
    val userStoryRepository: UserStoryRepository
): GoodUserStoryService {
    override fun <T> getBacklog(converter: UserStoryConverter<T>): T {
        val userStories = userStoryRepository
            .findAll()
            .filter { it.state != UserStoryState.ACCEPTED }
        return converter.convert(userStories)
    }
}