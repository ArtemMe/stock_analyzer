package com.mezh.heroku_demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping(HELLO_PATH)
    fun getHello(): String {
        return "I am fine!"
    }

    companion object {
        const val HELLO_PATH = "/check"
    }
}