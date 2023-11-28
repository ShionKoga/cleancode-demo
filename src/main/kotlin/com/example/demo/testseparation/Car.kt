package com.example.demo.testseparation

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "car")
data class Car(
    @Id
    val carId: UUID,
    val carName: String,
    val modelYear: Int,
    val mileage: Int,
    @Enumerated(EnumType.STRING)
    val driveType: DriveType,
    val price: Int,
    @Enumerated(EnumType.STRING)
    val bodyStyle: BodyStyle,
    @Enumerated(EnumType.STRING)
    val color: ExteriorColor,
    val doorCount: Int,
    val automaticParking: Boolean,
    val sunroof: Boolean,
    val leatherSeats: Boolean,
    val brakeAssist: Boolean,
    val cruiseControl: Boolean,
)

enum class DriveType {
    AWD, FRONT_WHEEL_DRIVE, REAR_WHEEL_DRIVE
}

enum class BodyStyle {
    SEDAN, TRUCK, SUV, VAN
}

enum class ExteriorColor {
    BLACK, WHITE, BLUE, RED, GREEN, SILVER, GRAY
}