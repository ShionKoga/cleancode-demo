package com.example.demo.strategy

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class ConverterTests {
    @Nested
    inner class DoNothingConverterTests {
        @Test
        fun `just return given user stories`() {
            val converter = DoNothingConverter()
            val expectedUserStories = listOf(
                UserStoryBuilder.build { title = "some title" }
            )


            val result = converter.convert(expectedUserStories)


            assertEquals(expectedUserStories, result)
        }
    }

    @Nested
    inner class SeparateByVelocityConverterTests {
        @Test
        fun `returns backlog separated by 10 points`() {
            val userStories = listOf(
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 3 },

                UserStoryBuilder.build { points = 2 },
                UserStoryBuilder.build { points = 1 },
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 3 },
                UserStoryBuilder.build { points = 1 },

                UserStoryBuilder.build { points = 2 },
            )
            val converter = SeparateByVelocityConverter()


            val result = converter.convert(userStories)


            assertEquals(3, result.count())
            val sumOfFirstWeekStories: Int = result.first().sumOfPoints()
            val sumOfSecondWeekStories: Int = result[1].sumOfPoints()
            val sumOfThirdWeekStories: Int = result.last().sumOfPoints()
            assertEquals(9, sumOfFirstWeekStories)
            assertEquals(10, sumOfSecondWeekStories)
            assertEquals(2, sumOfThirdWeekStories)
        }

        private fun List<UserStory>.sumOfPoints(): Int {
            return this.map { it.points }.reduce {acc, points -> acc + points }
        }

        @Test
        fun `returns empty list when there is no user stories`() {
            val emptyUserStories = emptyList<UserStory>()
            val converter = SeparateByVelocityConverter()


            val result = converter.convert(emptyUserStories)


            assertTrue(result.isEmpty())
        }
    }
}