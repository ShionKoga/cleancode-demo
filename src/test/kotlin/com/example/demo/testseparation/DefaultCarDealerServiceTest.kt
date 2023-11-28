package com.example.demo.testseparation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DefaultCarDealerServiceTest {
    @Autowired
    private lateinit var carRepository: CarRepository
    private lateinit var carDealerService: CarDealerService

    @BeforeEach
    fun setup() {
        carDealerService = DefaultCarDealerService(carRepository)
    }

    @Nested
    inner class BadTests {
        // どんな悪臭が漂ってますか？
        // Carにプロパティを追加した時、どこを変更する必要がありますか？

        @Test
        fun `returns car information`() {
            carRepository.save(
                Car(
                    UUID.fromString("11111111-2222-3333-4444-555555555555"),
                    "Highlander",
                    2022,
                    10000,
                    DriveType.FRONT_WHEEL_DRIVE,
                    45678,
                    BodyStyle.SUV,
                    ExteriorColor.WHITE,
                    5,
                    automaticParking = false,
                    sunroof = true,
                    leatherSeats = false,
                    brakeAssist = true,
                    cruiseControl = true
                )
            )


            val mileage = 20000
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(UUID.fromString("11111111-2222-3333-4444-555555555555"), searchResults.first().carId)
            assertEquals("Highlander", searchResults.first().carName)
            assertEquals(2022, searchResults.first().modelYear)
            assertEquals(10000, searchResults.first().mileage)
            assertEquals(DriveType.FRONT_WHEEL_DRIVE, searchResults.first().driveType)
            assertEquals(45678, searchResults.first().price)
            assertEquals(BodyStyle.SUV, searchResults.first().bodyStyle)
            assertEquals(ExteriorColor.WHITE, searchResults.first().color)
            assertEquals(5, searchResults.first().doorCount)
            assertEquals(false, searchResults.first().automaticParking)
            assertEquals(true, searchResults.first().sunroof)
            assertEquals(false, searchResults.first().leatherSeats)
            assertEquals(true, searchResults.first().brakeAssist)
            assertEquals(true, searchResults.first().cruiseControl)
        }

        @Test
        fun `returns car that mileage less than or equal given value`() {
            carRepository.saveAll(listOf(
                Car(
                    UUID.randomUUID(),
                    "",
                    0,
                    19999,
                    DriveType.AWD,
                    0,
                    BodyStyle.SEDAN,
                    ExteriorColor.BLACK,
                    0,
                    automaticParking = false,
                    sunroof = false,
                    leatherSeats = false,
                    brakeAssist = false,
                    cruiseControl = false,
                ),
                Car(
                    UUID.randomUUID(),
                    "",
                    0,
                    20000,
                    DriveType.AWD,
                    0,
                    BodyStyle.SEDAN,
                    ExteriorColor.BLACK,
                    0,
                    automaticParking = false,
                    sunroof = false,
                    leatherSeats = false,
                    brakeAssist = false,
                    cruiseControl = false,
                ),
                Car(
                    UUID.randomUUID(),
                    "",
                    0,
                    20001,
                    DriveType.AWD,
                    0,
                    BodyStyle.SEDAN,
                    ExteriorColor.BLACK,
                    0,
                    automaticParking = false,
                    sunroof = false,
                    leatherSeats = false,
                    brakeAssist = false,
                    cruiseControl = false,
                ),
            ))


            val mileage = 20000
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(2, searchResults.count())
            assertEquals(19999, searchResults.first().mileage)
            assertEquals(20000, searchResults.last().mileage)
        }

        @Test
        fun `returns car sorted by mileage`() {
            carRepository.saveAll(listOf(
                Car(
                    UUID.randomUUID(),
                    "",
                    0,
                    2,
                    DriveType.AWD,
                    0,
                    BodyStyle.SEDAN,
                    ExteriorColor.BLACK,
                    0,
                    automaticParking = false,
                    sunroof = false,
                    leatherSeats = false,
                    brakeAssist = false,
                    cruiseControl = false,
                ),
                Car(
                    UUID.randomUUID(),
                    "",
                    0,
                    1,
                    DriveType.AWD,
                    0,
                    BodyStyle.SEDAN,
                    ExteriorColor.BLACK,
                    0,
                    automaticParking = false,
                    sunroof = false,
                    leatherSeats = false,
                    brakeAssist = false,
                    cruiseControl = false,
                ),
            ))


            val mileage = 20000
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(1, searchResults.first().mileage)
            assertEquals(2, searchResults.last().mileage)
        }
    }

    @Nested
    inner class GoodTests {
        @Test
        fun `returns its id`() {
            carRepository.save(
                CarBuilder.build {
                    carId = UUID.fromString("11111111-2222-3333-4444-555555555555")
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(UUID.fromString("11111111-2222-3333-4444-555555555555"), searchResults.first().carId)
        }

        @Test
        fun `returns its name`() {
            carRepository.save(
                CarBuilder.build {
                    carName = "car name"
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals("car name", searchResults.first().carName)
        }

        @Test
        fun `returns its model year`() {
            carRepository.save(
                CarBuilder.build {
                    modelYear = 2023
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(2023, searchResults.first().modelYear)
        }

        @Test
        fun `returns its mileage`() {
            carRepository.save(
                CarBuilder.build {
                    mileage = 20000
                }
            )


            val mileage = 20000
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(20000, searchResults.first().mileage)
        }

        @Test
        fun `returns its drive type`() {
            carRepository.save(
                CarBuilder.build {
                    driveType = DriveType.FRONT_WHEEL_DRIVE
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(DriveType.FRONT_WHEEL_DRIVE, searchResults.first().driveType)
        }

        @Test
        fun `returns its price`() {
            carRepository.save(
                CarBuilder.build {
                    price = 1234
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(1234, searchResults.first().price)
        }

        @Test
        fun `returns its body style`() {
            carRepository.save(
                CarBuilder.build {
                    bodyStyle = BodyStyle.SUV
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(BodyStyle.SUV, searchResults.first().bodyStyle)
        }

        @Test
        fun `returns its color`() {
            carRepository.save(
                CarBuilder.build {
                    color = ExteriorColor.BLUE
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(ExteriorColor.BLUE, searchResults.first().color)
        }

        @Test
        fun `returns its door count`() {
            carRepository.save(
                CarBuilder.build {
                    doorCount = 5
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(5, searchResults.first().doorCount)
        }

        @Test
        fun `returns that the car has automatic parking`() {
            carRepository.save(
                CarBuilder.build {
                    automaticParking = true
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertTrue(searchResults.first().automaticParking)
        }

        @Test
        fun `returns that the car has sunroof`() {
            carRepository.save(
                CarBuilder.build {
                    sunroof = true
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertTrue(searchResults.first().sunroof)
        }

        @Test
        fun `returns that the car has leather seats`() {
            carRepository.save(
                CarBuilder.build {
                    leatherSeats = true
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertTrue(searchResults.first().leatherSeats)
        }

        @Test
        fun `returns that the car has brake assist`() {
            carRepository.save(
                CarBuilder.build {
                    brakeAssist = true
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertTrue(searchResults.first().brakeAssist)
        }

        @Test
        fun `returns that the car has cruise control`() {
            carRepository.save(
                CarBuilder.build {
                    cruiseControl = true
                }
            )


            val mileage = 0
            val searchResults = carDealerService.searchCarBy(mileage)


            assertTrue(searchResults.first().cruiseControl)
        }

        @Test
        fun `returns car that mileage less than or equal given value`() {
            carRepository.saveAll(
                listOf(
                    CarBuilder.build {
                        mileage = 19999
                    },
                    CarBuilder.build {
                        mileage = 20000
                    },
                    CarBuilder.build {
                        mileage = 20001
                    },
                )
            )


            val mileage = 20000
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(2, searchResults.count())
            assertEquals(19999, searchResults.first().mileage)
            assertEquals(20000, searchResults.last().mileage)
        }

        @Test
        fun `returns car sorted by mileage`() {
            carRepository.saveAll(
                listOf(
                    CarBuilder.build {
                        mileage = 1
                    },
                    CarBuilder.build {
                        mileage = 2
                    },
                )
            )


            val mileage = 20000
            val searchResults = carDealerService.searchCarBy(mileage)


            assertEquals(1, searchResults.first().mileage)
            assertEquals(2, searchResults.last().mileage)
        }

        // Builderではなくクラスにデフォルト値をつけるとどんな 悪臭が発生しますか？
    }
}