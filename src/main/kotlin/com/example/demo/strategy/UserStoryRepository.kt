package com.example.demo.strategy

import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
@Table(name = "user_story")
interface UserStoryRepository: JpaRepository<UserStory, UUID> {
}