package com.example.wifiapp

import android.annotation.SuppressLint
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build

class WifiHelper(private val wifiManager: WifiManager) {

    /**
     * Connect to a WiFi network
     */
    fun connectToNetwork(ssid: String, password: String): Boolean {
        return try {
            val wifiConfig = WifiConfiguration().apply {
                SSID = "\"$ssid\""
                preSharedKey = "\"$password\""
                hiddenSSID = false
            }

            val netId = wifiManager.addNetwork(wifiConfig)
            if (netId == -1) {
                return false
            }

            wifiManager.disconnect()
            wifiManager.enableNetwork(netId, true)
            wifiManager.reconnect()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Disconnect from current WiFi network
     */
    @SuppressLint("MissingPermission")
    fun disconnect(): Boolean {
        return try {
            wifiManager.disconnect()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Check if device is connected to WiFi
     */
    @SuppressLint("MissingPermission")
    fun isConnected(): Boolean {
        val connectionInfo = wifiManager.connectionInfo
        return connectionInfo != null && connectionInfo.networkId != -1
    }

    /**
     * Get current connected WiFi SSID
     */
    @SuppressLint("MissingPermission")
    fun getCurrentSSID(): String {
        val connectionInfo = wifiManager.connectionInfo
        return if (connectionInfo != null && connectionInfo.ssid != null) {
            // Remove quotes from SSID if present
            connectionInfo.ssid.replace("\"", "")
        } else {
            "Unknown"
        }
    }

    /**
     * Get WiFi signal strength
     */
    @SuppressLint("MissingPermission")
    fun getSignalStrength(): Int {
        val connectionInfo = wifiManager.connectionInfo
        return connectionInfo?.rssi ?: 0
    }

    /**
     * Enable WiFi
     */
    fun enableWiFi(): Boolean {
        return if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        } else {
            true
        }
    }

    /**
     * Disable WiFi
     */
    fun disableWiFi(): Boolean {
        return if (wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = false
        } else {
            true
        }
    }

    /**
     * Check if WiFi is enabled
     */
    fun isWiFiEnabled(): Boolean {
        return wifiManager.isWifiEnabled
    }
}
