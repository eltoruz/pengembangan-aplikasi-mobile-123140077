package com.eltoruz.myprofileapp.platform

import platform.UIKit.UIDevice

actual class DeviceInfo {
    actual fun getDeviceName(): String = UIDevice.currentDevice.name
    actual fun getOsVersion(): String = "iOS ${UIDevice.currentDevice.systemVersion}"
    actual fun getAppVersion(): String = "1.0.0"
}
