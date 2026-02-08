package com.example.myfirstkmpapp

import android.os.Build


actual fun getPlatform(): String {
    return "Android (SDK ${Build.VERSION.SDK_INT})"
}