package com.eltoruz.myprofileapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform