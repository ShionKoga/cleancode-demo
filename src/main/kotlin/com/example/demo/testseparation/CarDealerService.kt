package com.example.demo.testseparation

import org.springframework.stereotype.Service

@Service
interface CarDealerService {
    fun searchCarBy(mileage: Int): List<Car>
}

class DefaultCarDealerService(
    val carRepository: CarRepository
): CarDealerService {
    override fun searchCarBy(mileage: Int): List<Car> {
        return carRepository
            .findAllByMileageLessThanEqual(mileage)
            .sortedBy { it.mileage }
    }
}