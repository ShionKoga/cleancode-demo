package com.example.demo.testseparation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cars")
class CarDealerController(val carDealerService: CarDealerService) {
    @GetMapping
    fun searchCar(@RequestParam mileage: Int): List<Car> {
        return carDealerService.searchCarBy(mileage)
    }
}