package com.example.wifiapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager
    private lateinit var statusTextView: TextView
    private lateinit var connectButton: Button
    private lateinit var disconnectButton: Button
    private lateinit var wifiHelper: WifiHelper

    private val PERMISSION_REQUEST_CODE = 100
    private val requiredPermissions = mutableListOf(
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            add(Manifest.permission.NEARBY_WIFI_DEVICES)
        }
    }.toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        statusTextView = findViewById(R.id.statusTextView)
        connectButton = findViewById(R.id.connectButton)
        disconnectButton = findViewById(R.id.disconnectButton)

        // Initialize WiFi Manager
        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiHelper = WifiHelper(wifiManager)

        // Check permissions
        checkAndRequestPermissions()

        // Set up button listeners
        connectButton.setOnClickListener {
            showConnectDialog()
        }

        disconnectButton.setOnClickListener {
            disconnectFromWiFi()
        }

        // Update status
        updateStatus()
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest, PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val allGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (allGranted) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConnectDialog() {
        // For now, we'll connect to a hardcoded SSID
        // In a real app, you'd show a dialog or list of available networks
        val ssid = "YourWiFiSSID"
        val password = "YourPassword"

        connectToWiFi(ssid, password)
    }

    private fun connectToWiFi(ssid: String, password: String) {
        val success = wifiHelper.connectToNetwork(ssid, password)
        if (success) {
            Toast.makeText(this, "Connecting to $ssid", Toast.LENGTH_SHORT).show()
            updateStatus()
        } else {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun disconnectFromWiFi() {
        val success = wifiHelper.disconnect()
        if (success) {
            Toast.makeText(this, "Disconnected from WiFi", Toast.LENGTH_SHORT).show()
            updateStatus()
        } else {
            Toast.makeText(this, "Failed to disconnect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStatus() {
        val isConnected = wifiHelper.isConnected()
        val currentSSID = wifiHelper.getCurrentSSID()

        val status = if (isConnected) {
            "Connected to: $currentSSID"
        } else {
            "Not connected"
        }

        statusTextView.text = status
        connectButton.isEnabled = !isConnected
        disconnectButton.isEnabled = isConnected
    }
}
