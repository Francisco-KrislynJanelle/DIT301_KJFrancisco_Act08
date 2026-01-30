# GPS Location Tracker

A simple Android application that displays the user's live GPS location on a map using Google Maps and the device's location services.

## App Description

This mobile application provides real-time GPS location tracking with the following features:
- Requests and handles location permissions properly
- Displays an interactive Google Map
- Shows a marker at the user's current location
- Updates location automatically when the device moves
- Displays current coordinates (latitude and longitude)
- Clean, simple UI with Material Design 3

## Permissions Used

The app requires the following permissions:
- `ACCESS_FINE_LOCATION` - For precise GPS location tracking
- `ACCESS_COARSE_LOCATION` - For network-based location as fallback

These permissions are requested at runtime with proper user consent handling.

## How GPS Location is Obtained

The app uses Google Play Services Location API to obtain GPS coordinates:

1. **Permission Check**: First checks if location permissions are granted
2. **Location Client**: Uses `FusedLocationProviderClient` for optimal battery usage
3. **Location Request**: Configured for high accuracy with 5-second update intervals
4. **Real-time Updates**: Receives location updates via `LocationCallback`
5. **Map Integration**: Updates the map camera and marker position automatically

### Technical Implementation

- **Location Updates**: Every 5 seconds or when moved 10+ meters
- **Accuracy**: High accuracy mode using GPS and network providers
- **Battery Optimization**: Uses fused location provider for efficient battery usage
- **Error Handling**: Graceful handling of permission denials and location unavailability

## Screenshots

### Permission Request
![Permission Request](screenshots/permission_request.png)
*The app requests location permissions with clear explanation*

### Map with Location
![Map Location](screenshots/map_location.png)
*Google Map displaying the user's current location with marker*

### Location Updates
![Location Update](screenshots/location_update.png)
*Real-time location coordinates displayed at the bottom*

## Setup Instructions

1. **Google Maps API Key**: 
   - Get an API key from [Google Cloud Console](https://console.cloud.google.com/)
   - Enable Maps SDK for Android
   - Replace `YOUR_API_KEY_HERE` in `AndroidManifest.xml`

2. **Build and Run**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Testing**:
   - Install on a physical device (emulator location may be limited)
   - Grant location permissions when prompted
   - Move around to see location updates

## Project Structure

```
app/
├── src/main/java/com/example/gpslocationonmap/
│   ├── MainActivity.kt          # Main activity with UI and permission handling
│   ├── LocationHelper.kt        # Location services and updates
│   └── ui/theme/               # Material Design theme
├── src/main/res/               # Resources (layouts, strings, etc.)
└── src/main/AndroidManifest.xml # App permissions and configuration
```

## Dependencies

- Google Maps Compose: `4.3.3`
- Google Play Services Maps: `18.2.0`
- Google Play Services Location: `21.0.1`
- Accompanist Permissions: `0.32.0`
- Jetpack Compose with Material 3

## Notes

- **Security**: API keys should be secured in production (use build variants or secure storage)
- **Testing**: Best tested on physical devices with GPS capability
- **Permissions**: App handles permission denial gracefully without crashing
- **Battery**: Uses efficient location providers to minimize battery drain