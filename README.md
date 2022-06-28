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

## Development

### Build plugin

To build the plugin you can perform the gradle task `assaemble`.

### Publish plugin

To publish the plugin you can perform the gradle task `publishPlugins`.

### Include local plugin

For development it is useful to test the plugin without uploading it. To do this you can use a local package dependency.

Just point in your project gradle, where you want to test the plugin, to your local repository:

```groovy
buildscript{
    dependencies{
        classpath files('/Users/starke/Developer/android-versioning/build/libs/gradle-versioning-1.2.0.jar')
    }
}

apply plugin: de.develappers.versioning.Versioning
```

https://stackoverflow.com/questions/35302414/adding-local-plugin-to-a-gradle-project
