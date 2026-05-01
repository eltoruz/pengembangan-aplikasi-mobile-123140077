package com.eltoruz.myprofileapp.platform

import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceBatteryState

actual class BatteryInfo {
    init {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
    }

    actual fun getBatteryLevel(): Int = (UIDevice.currentDevice.batteryLevel * 100).toInt()
    actual fun isCharging(): Boolean = UIDevice.currentDevice.batteryState == UIDeviceBatteryState.UIDeviceBatteryStateCharging
}
