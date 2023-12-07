package com.example.demo.strategy

class SpyConverter: UserStoryConverter<List<UserStory>> {
    var convert_argument_userStories: List<UserStory>? = null
    override fun convert(userStories: List<UserStory>): List<UserStory> {
        convert_argument_userStories = userStories
        return emptyList()
    }
}