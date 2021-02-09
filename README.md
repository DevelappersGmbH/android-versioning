# Android Versioning

This plugin allows you to manage your Android versionName and versionCode using a Properties file.

## Setup

### `plugins` block:

```groovy
// app build.gradle
plugins {
  id 'de.develappers.versioning' version '1.1.0'
}
```

### Set version code and name

```groovy
android {
  defaultConfig {
    versionCode versioning.versionCode
    versionName versioning.versionName
  }
}
```
