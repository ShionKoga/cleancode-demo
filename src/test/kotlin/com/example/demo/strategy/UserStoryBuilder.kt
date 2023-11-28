package com.example.demo.strategy

import java.util.*


class UserStoryBuilder {
    var storyId: UUID = UUID.randomUUID()
    var title: String = ""
    var storyType: StoryType = StoryType.FEATURE
    var points: Int = 0
    var state: UserStoryState = UserStoryState.UNSTARTED

    companion object {
        fun build(block: UserStoryBuilder.() -> Unit) = UserStoryBuilder().apply(block).build()
    }

    fun build(): UserStory {
        return UserStory(
            storyId,
            title,
            storyType,
            points,
            state,
        )
    }
}