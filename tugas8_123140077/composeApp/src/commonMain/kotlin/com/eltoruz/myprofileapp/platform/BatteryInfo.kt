package com.eltoruz.myprofileapp.platform

expect class BatteryInfo() {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
