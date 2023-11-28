package com.example.demo.testseparation

import java.util.*

class CarBuilder {
    var carId: UUID = UUID.randomUUID()
    var carName: String = ""
    var modelYear: Int = 0
    var mileage: Int = 0
    var driveType: DriveType = DriveType.FRONT_WHEEL_DRIVE
    var price: Int = 0
    var bodyStyle: BodyStyle = BodyStyle.SEDAN
    var color: ExteriorColor = ExteriorColor.WHITE
    var doorCount: Int = 0
    var automaticParking: Boolean = false
    var sunroof: Boolean = false
    var leatherSeats: Boolean = false
    var brakeAssist: Boolean = false
    var cruiseControl: Boolean = false

    companion object {
        fun build(block: CarBuilder.() -> Unit) = CarBuilder().apply(block).build()
    }

    fun build(): Car {
        return Car(
            carId,
            carName,
            modelYear,
            mileage,
            driveType,
            price,
            bodyStyle,
            color,
            doorCount,
            automaticParking,
            sunroof,
            leatherSeats,
            brakeAssist,
            cruiseControl,
        )
    }
}