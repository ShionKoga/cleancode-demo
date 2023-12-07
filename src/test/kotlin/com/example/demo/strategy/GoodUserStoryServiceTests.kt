package com.example.demo.strategy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GoodUserStoryServiceTests {
    @Autowired
    private lateinit var userStoryRepository: UserStoryRepository
    private lateinit var userStoryService: GoodUserStoryService


    @BeforeEach
    fun setup() {
        userStoryService = DefaultGoodUserStoryService(userStoryRepository)
    }

    @Nested
    inner class GetBacklog {
        @Test
        fun `returns user story id correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    storyId = UUID.fromString("11111111-2222-3333-4444-555555555555")
                }
            )


            val backlogs = userStoryService.getBacklog(DoNothingConverter())


            assertEquals(UUID.fromString("11111111-2222-3333-4444-555555555555"), backlogs.first().storyId)
        }

        @Test
        fun `returns title correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    title = "user story title"
                }
            )


            val backlogs = userStoryService.getBacklog(DoNothingConverter())


            assertEquals("user story title", backlogs.first().title)
        }

        @Test
        fun `returns story type correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    storyType = StoryType.CHORE
                }
            )


            val backlogs = userStoryService.getBacklog(DoNothingConverter())


            assertEquals(StoryType.CHORE, backlogs.first().storyType)
        }

        @Test
        fun `returns points correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    points = 3
                }
            )


            val backlogs = userStoryService.getBacklog(DoNothingConverter())


            assertEquals(3, backlogs.first().points)
        }

        @Test
        fun `returns state correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    state = UserStoryState.STARTED
                }
            )


            val backlogs = userStoryService.getBacklog(DoNothingConverter())


            assertEquals(UserStoryState.STARTED, backlogs.first().state)
        }

        @Test
        fun `returns unaccepted user story`() {
            userStoryRepository.saveAll(listOf(
                UserStoryBuilder.build {
                    state = UserStoryState.UNSTARTED
                },
                UserStoryBuilder.build {
                    state = UserStoryState.STARTED
                },
                UserStoryBuilder.build {
                    state = UserStoryState.FINISHED
                },
                UserStoryBuilder.build {
                    state = UserStoryState.DELIVERED
                },
                UserStoryBuilder.build {
                    state = UserStoryState.REJECTED
                },
                UserStoryBuilder.build {
                    state = UserStoryState.ACCEPTED
                },
            ))


            val backlogs = userStoryService.getBacklog(DoNothingConverter())


            backlogs.forEach {
                assertNotEquals(UserStoryState.ACCEPTED, it.state)
            }
        }

        @Test
        fun `passes correct userStories to converter`() {
            val spyConverter = SpyConverter()
            val expectedUuid = UUID.fromString("11111111-2222-3333-4444-555555555555")
            userStoryRepository.save(
                UserStoryBuilder.build {
                    storyId = expectedUuid
                }
            )


            userStoryService.getBacklog(spyConverter)

            
            assertEquals(spyConverter.convert_argument_userStories?.first()?.storyId, expectedUuid)
        }
    }
}