package com.example.demo.strategy

import org.springframework.stereotype.Service

interface BadUserStoryService {
    fun getBacklog(): List<UserStory>
    fun getBacklogSeparatedByVelocity(): List<List<UserStory>>
}

@Service
class DefaultBadUserStoryService(
    val userStoryRepository: UserStoryRepository
): BadUserStoryService {
    override fun getBacklog(): List<UserStory> {
        return userStoryRepository
            .findAll()
            .filter { it.state != UserStoryState.ACCEPTED }
    }

    override fun getBacklogSeparatedByVelocity(): List<List<UserStory>> {
        val userStories = userStoryRepository
            .findAll()
            .filter { it.state != UserStoryState.ACCEPTED }

        val velocity = 10
        val result = mutableListOf<MutableList<UserStory>>()
        var currentList = mutableListOf<UserStory>()
        var sum = 0

        userStories.forEach {
            if (sum + it.points <= velocity) {
                currentList.add(it)
                sum += it.points
            } else {
                result.add(currentList)
                currentList = mutableListOf(it)
                sum = it.points
            }
        }
        if (currentList.isNotEmpty()) {
            result.add(currentList)
        }

        return result
    }
}