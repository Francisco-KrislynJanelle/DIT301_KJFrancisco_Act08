# Build and Run Instructions

## Prerequisites

1. **Android Studio** installed with SDK
2. **Google Maps API Key** from Google Cloud Console
3. **Physical Android device** (recommended for GPS testing)

## Setup Steps

### 1. Configure Google Maps API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing one
3. Enable "Maps SDK for Android" API
4. Create credentials → API Key
5. Replace `YOUR_API_KEY_HERE` in `app/src/main/AndroidManifest.xml` with your actual API key

### 2. Build the Project

```bash
# Clean and build
./gradlew clean
./gradlew assembleDebug

# Or build release version
./gradlew assembleRelease
```

### 3. Install and Run

```bash
# Install on connected device
./gradlew installDebug

# Or use Android Studio
# Click "Run" button or press Shift+F10
```

## Testing Checklist

- [ ] App launches without crashing
- [ ] Permission request appears on first launch
- [ ] Map loads after granting permissions
- [ ] Current location marker appears on map
- [ ] Location coordinates display at bottom
- [ ] Location updates when device moves
- [ ] App handles permission denial gracefully

## Troubleshooting

### Common Issues

1. **Map not loading**: Check API key configuration
2. **Location not found**: Ensure GPS is enabled on device
3. **Permission crashes**: Check permission handling code
4. **Build errors**: Sync project and check dependencies

### Debug Commands

```bash
# Check connected devices
adb devices

# View app logs
adb logcat | grep GPSLocationOnMap

# Clear app data
adb shell pm clear com.example.gpslocationonmap
```