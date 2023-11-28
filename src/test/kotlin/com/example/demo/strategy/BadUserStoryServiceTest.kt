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
class BadUserStoryServiceTest {
    @Autowired
    private lateinit var userStoryRepository: UserStoryRepository
    private lateinit var userStoryService: BadUserStoryService

    @BeforeEach
    fun setup() {
        userStoryService = DefaultBadUserStoryService(userStoryRepository)
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


            val backlogs = userStoryService.getBacklog()


            assertEquals(UUID.fromString("11111111-2222-3333-4444-555555555555"), backlogs.first().storyId)
        }

        @Test
        fun `returns title correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    title = "user story title"
                }
            )


            val backlogs = userStoryService.getBacklog()


            assertEquals("user story title", backlogs.first().title)
        }

        @Test
        fun `returns story type correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    storyType = StoryType.CHORE
                }
            )


            val backlogs = userStoryService.getBacklog()


            assertEquals(StoryType.CHORE, backlogs.first().storyType)
        }

        @Test
        fun `returns points correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    points = 3
                }
            )


            val backlogs = userStoryService.getBacklog()


            assertEquals(3, backlogs.first().points)
        }

        @Test
        fun `returns state correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    state = UserStoryState.STARTED
                }
            )


            val backlogs = userStoryService.getBacklog()


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


            val backlogs = userStoryService.getBacklog()


            backlogs.forEach {
                assertNotEquals(UserStoryState.ACCEPTED, it.state)
            }
        }
    }

    @Nested
    inner class GetBacklogSeparatedByVelocity {
        @Test
        fun `returns user story id correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    storyId = UUID.fromString("11111111-2222-3333-4444-555555555555")
                }
            )


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            val firstWeekBacklogs = backlogs.first()
            assertEquals(
                UUID.fromString("11111111-2222-3333-4444-555555555555"),
                firstWeekBacklogs.first().storyId
            )
        }

        @Test
        fun `returns title correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    title = "user story title"
                }
            )


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            val firstWeekBacklogs = backlogs.first()
            assertEquals("user story title", firstWeekBacklogs.first().title)
        }

        @Test
        fun `returns story type correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    storyType = StoryType.CHORE
                }
            )


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            val firstWeekBacklogs = backlogs.first()
            assertEquals(StoryType.CHORE, firstWeekBacklogs.first().storyType)
        }

        @Test
        fun `returns points correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    points = 3
                }
            )


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            val firstWeekBacklogs = backlogs.first()
            assertEquals(3, firstWeekBacklogs.first().points)
        }

        @Test
        fun `returns state correctly`() {
            userStoryRepository.save(
                UserStoryBuilder.build {
                    state = UserStoryState.STARTED
                }
            )


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            val firstWeekBacklogs = backlogs.first()
            assertEquals(UserStoryState.STARTED, firstWeekBacklogs.first().state)
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


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            backlogs.forEach {
                assertNotEquals(UserStoryState.ACCEPTED, it.first().state)
            }
        }

        @Test
        fun `returns backlog separated by 10 points`() {
            userStoryRepository.saveAll(listOf(
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 3 },

                UserStoryBuilder.build { points = 2 },
                UserStoryBuilder.build { points = 1 },
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 1 },

                UserStoryBuilder.build { points = 2 },
            ))


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            assertEquals(3, backlogs.count())
            val sumOfFirstWeekStories: Int = backlogs.first().sumOfPoints()
            val sumOfSecondWeekStories: Int = backlogs[1].sumOfPoints()
            val sumOfThirdWeekStories: Int = backlogs.last().sumOfPoints()
            assertEquals(9, sumOfFirstWeekStories)
            assertEquals(10, sumOfSecondWeekStories)
            assertEquals(2, sumOfThirdWeekStories)
        }

        private fun List<UserStory>.sumOfPoints(): Int {
            return this.map { it.points }.reduce {acc, points -> acc + points }
        }

        @Test
        fun `returns empty list when there is no user stories`() {
            userStoryRepository.deleteAll()


            val backlogs = userStoryService.getBacklogSeparatedByVelocity()


            assertTrue(backlogs.isEmpty())
        }
    }
}