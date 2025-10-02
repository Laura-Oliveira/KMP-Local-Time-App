package com.kmp.time

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform