package com.eltoruz.myprofileapp.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import java.net.InetAddress

actual class NetworkMonitor {
    actual fun isConnected(): Boolean {
        return try {
            val address = InetAddress.getByName("8.8.8.8")
            address.isReachable(3000)
        } catch (e: Exception) {
            false
        }
    }

    actual fun observeConnectivity(): Flow<Boolean> = flow {
        while (true) {
            emit(isConnected())
            delay(5000)
        }
    }
}
