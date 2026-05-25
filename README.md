# WiFi App

An Android application built with Kotlin that allows users to connect to and disconnect from WiFi networks.

## Features

- ✅ Connect to WiFi networks
- ✅ Disconnect from WiFi networks
- ✅ Display current WiFi connection status
- ✅ Show connected SSID
- ✅ Get WiFi signal strength
- ✅ Enable/Disable WiFi

## Requirements

- Android 7.0 (API 24) or higher
- Kotlin 1.9+
- Android Studio Jellyfish or later

## Permissions

The app requires the following permissions:
- `ACCESS_WIFI_STATE` - To read WiFi state
- `CHANGE_WIFI_STATE` - To connect/disconnect from WiFi
- `ACCESS_FINE_LOCATION` - For WiFi scanning (required on Android 6.0+)
- `NEARBY_WIFI_DEVICES` - For WiFi operations (required on Android 12+)

## Project Structure

```
app/
├── src/main/java/com/example/wifiapp/
│   ├── MainActivity.kt       # Main activity with UI
│   └── WifiHelper.kt         # WiFi utility functions
├── src/main/res/
│   └── layout/
│       └── activity_main.xml # UI layout
└── AndroidManifest.xml       # App configuration and permissions
```

## How to Use

1. Clone the repository
2. Open the project in Android Studio
3. Update the WiFi SSID and password in `MainActivity.kt` (in the `showConnectDialog()` method)
4. Build and run the app on an Android device or emulator

## Code Overview

### WifiHelper.kt

Helper class that provides WiFi operations:
- `connectToNetwork(ssid, password)` - Connect to a WiFi network
- `disconnect()` - Disconnect from current network
- `isConnected()` - Check connection status
- `getCurrentSSID()` - Get current SSID
- `getSignalStrength()` - Get signal strength
- `enableWiFi()` - Enable WiFi
- `disableWiFi()` - Disable WiFi

### MainActivity.kt

Main activity that handles:
- Permission requests
- UI updates
- User interactions

## Future Enhancements

- [ ] Add WiFi network scanning
- [ ] Display list of available networks
- [ ] Implement WiFi network selection dialog
- [ ] Add saved networks management
- [ ] Implement WiFi speed testing
- [ ] Add network information display

## License

MIT License

## Author

1suv
