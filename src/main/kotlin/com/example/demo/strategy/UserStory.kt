package com.example.demo.strategy

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class UserStory(
    @Id
    val storyId: UUID,
    val title: String,
    @Enumerated(EnumType.STRING)
    val storyType: StoryType,
    val points: Int,
    @Enumerated(EnumType.STRING)
    val state: UserStoryState,
)

enum class StoryType {
    FEATURE, CHORE, BUG, RELEASE
}

enum class UserStoryState {
    UNSTARTED, STARTED, FINISHED, DELIVERED, REJECTED, ACCEPTED
}