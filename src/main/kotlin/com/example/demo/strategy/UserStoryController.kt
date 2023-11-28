package com.example.demo.strategy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/bad/stories")
class BadUserStoryController(
    val userStoryService: BadUserStoryService
) {
    @GetMapping("/backlog")
    fun getBacklog(): List<UserStory> {
        return userStoryService.getBacklog()
    }

    @GetMapping("/v2/backlog")
    fun getBacklogV2(): List<List<UserStory>> {
        return userStoryService.getBacklogSeparatedByVelocity()
    }
}

@RestController
@RequestMapping("/api/good/stories")
class GoodUserStoryController(
    val userStoryService: GoodUserStoryService
) {
    @GetMapping("/backlog")
    fun getBacklog(): List<UserStory> {
        return userStoryService.getBacklog(DoNothingConverter())
    }

    @GetMapping("/v2/backlog")
    fun getBacklogV2(): List<List<UserStory>> {
        return userStoryService.getBacklog(SeparateByVelocityConverter())
    }
}