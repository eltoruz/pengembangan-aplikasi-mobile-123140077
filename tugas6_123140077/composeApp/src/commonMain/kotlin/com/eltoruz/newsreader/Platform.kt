package com.eltoruz.newsreader

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform