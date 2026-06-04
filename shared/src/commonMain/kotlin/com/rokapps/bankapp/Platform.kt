package com.rokapps.bankapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform