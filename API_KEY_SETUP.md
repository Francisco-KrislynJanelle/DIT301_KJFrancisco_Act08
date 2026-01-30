# Google Maps API Key Setup

## Step-by-Step Guide

### 1. Google Cloud Console Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Sign in with your Google account
3. Create a new project:
   - Click "Select a project" → "New Project"
   - Enter project name: "GPS Location App"
   - Click "Create"

### 2. Enable Maps API

1. In the Google Cloud Console, go to "APIs & Services" → "Library"
2. Search for "Maps SDK for Android"
3. Click on it and press "Enable"

### 3. Create API Key

1. Go to "APIs & Services" → "Credentials"
2. Click "Create Credentials" → "API Key"
3. Copy the generated API key
4. (Optional) Click "Restrict Key" to add restrictions:
   - Application restrictions: Android apps
   - Add your package name: `com.example.gpslocationonmap`
   - API restrictions: Maps SDK for Android

### 4. Add API Key to App

1. Open `app/src/main/AndroidManifest.xml`
2. Find this line:
   ```xml
   android:value="YOUR_API_KEY_HERE"
   ```
3. Replace `YOUR_API_KEY_HERE` with your actual API key:
   ```xml
   android:value="AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
   ```

### 5. Security Best Practices

**⚠️ Important**: Never commit API keys to public repositories!

For production apps:
- Use build variants to separate debug/release keys
- Store keys in `local.properties` (not tracked by git)
- Use environment variables in CI/CD

Example secure setup:
```kotlin
// In build.gradle.kts
android {
    defaultConfig {
        manifestPlaceholders["MAPS_API_KEY"] = project.findProperty("MAPS_API_KEY") ?: ""
    }
}
```

```xml
<!-- In AndroidManifest.xml -->
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="${MAPS_API_KEY}" />
```

```properties
# In local.properties
MAPS_API_KEY=your_actual_api_key_here
```

## Troubleshooting

### Common API Key Issues

1. **Map shows gray screen**: API key not configured or invalid
2. **"This page can't load Google Maps correctly"**: API not enabled
3. **Authentication errors**: Check package name restrictions
4. **Quota exceeded**: Check usage limits in Cloud Console

### Testing API Key

You can test if your API key works by visiting:
```
https://maps.googleapis.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&key=YOUR_API_KEY
```

Replace `YOUR_API_KEY` with your actual key. You should see a map image.