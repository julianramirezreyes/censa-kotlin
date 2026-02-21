package com.example.censa

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform