package com.example.demo.strategy


interface UserStoryConverter<T> {
    fun convert(userStories: List<UserStory>): T
}

class DoNothingConverter: UserStoryConverter<List<UserStory>> {
    override fun convert(userStories: List<UserStory>): List<UserStory> {
        return userStories
    }
}

class SeparateByVelocityConverter: UserStoryConverter<List<List<UserStory>>> {
    override fun convert(userStories: List<UserStory>): List<List<UserStory>> {
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