# Android Emulator Location Setup

## 🎯 Quick Fix for Emulator Testing

The app is still loading because **Android Studio emulator doesn't have real GPS**. Here's how to set up location:

### Method 1: Extended Controls (Recommended)

1. **Open Extended Controls**:
   - Click the `...` (more) button in emulator toolbar
   - Or press `Ctrl+Shift+P` (Windows) / `Cmd+Shift+P` (Mac)

2. **Set Location**:
   - Click "Location" in the left panel
   - Choose one of these options:
     - **Single points**: Enter coordinates manually
     - **Routes**: Simulate movement along a path
     - **Playback**: Use saved GPX files

3. **Quick Test Locations**:
   ```
   San Francisco: 37.7749, -122.4194
   New York: 40.7128, -74.0060
   London: 51.5074, -0.1278
   Your location: [Get from Google Maps]
   ```

4. **Send Location**:
   - Click "Send" to set the location
   - The app should immediately show your location

### Method 2: Command Line (Alternative)

```bash
# Connect to emulator
adb shell

# Set location (latitude, longitude)
geo fix -122.4194 37.7749

# Or use telnet
telnet localhost 5554
geo fix -122.4194 37.7749
```

### Method 3: Google Maps in Emulator

1. Open Google Maps in the emulator
2. Allow location permissions
3. Maps will prompt to enable location
4. This helps initialize location services

## 🔧 App Improvements Made

I've added **emulator fallback** to the app:
- If real GPS fails, shows mock location after 3 seconds
- Uses San Francisco coordinates as default
- Adds small random variations to simulate movement

## 📱 Testing on Real Device (Recommended)

For **real GPS testing**:

1. **Enable Developer Options**:
   - Settings → About Phone → Tap "Build Number" 7 times

2. **Enable USB Debugging**:
   - Settings → Developer Options → USB Debugging

3. **Connect Device**:
   ```bash
   adb devices  # Should show your device
   ```

4. **Install and Test**:
   ```bash
   ./gradlew installDebug
   ```

## 🚀 Expected Behavior Now

### In Emulator:
- **With location set**: Shows immediately
- **Without location set**: Shows mock location after 3 seconds
- **Manual location**: Use Extended Controls to set coordinates

### On Real Device:
- **Outdoors**: 5-30 seconds for GPS fix
- **Indoors**: May take 1-2 minutes or use network location
- **First launch**: Longer due to GPS cold start

## 🛠️ Troubleshooting Emulator

### Location Still Not Working?

1. **Check Emulator Settings**:
   - AVD Manager → Edit AVD → Advanced Settings
   - Ensure "GPS" is enabled

2. **Restart Emulator**:
   - Cold boot the emulator
   - Wipe data if necessary

3. **Check Google Play Services**:
   - Use emulator with Google Play Store
   - Update Google Play Services in emulator

4. **Alternative Emulator**:
   - Try different API level (28, 29, 30)
   - Use x86_64 images for better performance

### Quick Test Commands

```bash
# Check if location is working
adb shell dumpsys location

# Check GPS status
adb shell settings get secure location_providers_allowed

# Enable location services
adb shell settings put secure location_providers_allowed +gps,+network
```

## 📍 Setting Custom Locations

### Popular Test Locations:
```
Google HQ: 37.4220, -122.0841
Apple Park: 37.3349, -122.0090
Times Square: 40.7580, -73.9855
Eiffel Tower: 48.8584, 2.2945
```

### Simulate Movement:
1. Extended Controls → Location → Routes
2. Load GPX file or create custom route
3. Play route to simulate walking/driving

The app should now work much better in the emulator! 🎉