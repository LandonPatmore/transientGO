// Copyright 2008 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.android.stardroid;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.stardroid.layers.LayerManager;
import com.google.android.stardroid.util.MiscUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

/**
 * The main Stardroid Application class.
 *
 * @author John Taylor
 */
public class StardroidApplication extends Application {
  private static final String TAG = MiscUtil.getTag(StardroidApplication.class);
  private static final String PREVIOUS_APP_VERSION_PREF = "previous_app_version";
  private static final String NONE = "Clean install";
  private static final String UNKNOWN = "Unknown previous version";

  @Inject SharedPreferences preferences;
  // We keep a reference to this just to start it initializing.
  @Inject LayerManager layerManager;
  @Inject static ExecutorService backgroundExecutor;
  @Inject SensorManager sensorManager;

  private ApplicationComponent component;

  @Override
  public void onCreate() {
    Log.d(TAG, "StardroidApplication: onCreate");
    super.onCreate();

    component = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    component.inject(this);

    Log.i(TAG, "OS Version: " + android.os.Build.VERSION.RELEASE
            + "(" + android.os.Build.VERSION.SDK_INT + ")");
    String versionName = getVersionName();
    Log.i(TAG, "Sky Map version " + versionName + " build " + getVersion());

    // This populates the default values from the preferences XML file. See
    // {@link DefaultValues} for more details.
    PreferenceManager.setDefaultValues(this, R.xml.preference_screen, false);

    performFeatureCheck();

    Log.d(TAG, "StardroidApplication: -onCreate");
  }

  public ApplicationComponent getApplicationComponent() {
    return component;
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
  }

  /**
   * Returns the version string for Sky Map.
   */
  public String getVersionName() {
    // TODO(jontayler): update to use the info created by gradle.
    PackageManager packageManager = getPackageManager();
    try {
      PackageInfo info = packageManager.getPackageInfo(this.getPackageName(), 0);
      return info.versionName;
    } catch (NameNotFoundException e) {
      Log.e(TAG, "Unable to obtain package info");
      return "Unknown";
    }
  }

  /**
   * Returns the build number for Sky Map.
   */
  public int getVersion() {
    PackageManager packageManager = getPackageManager();
    try {
      PackageInfo info = packageManager.getPackageInfo(this.getPackageName(), 0);
      return info.versionCode;
    } catch (NameNotFoundException e) {
      Log.e(TAG, "Unable to obtain package info");
      return -1;
    }
  }

  /**
   * Schedules this runnable to run as soon as possible on a background
   * thread.
   */
  // TODO(johntaylor): the idea, and I'm not sure yet whether it's a good one,
  // is to centralize the management of background threads so we don't have
  // them scattered all over the app.  We can then control how many threads
  // are spawned, perhaps having a VIP service for extra important runnables
  // that we'd prefer not to queue, as well as providing convenience functions
  // to facilitate callbacks on the UI thread.
  public static void runInBackground(Runnable runnable) {
    backgroundExecutor.submit(runnable);
  }

  /**
   * Check what features are available to this phone and report back to analytics
   * so we can judge when to add/drop support.
   */
  private void performFeatureCheck() {
    if (sensorManager == null) {
      Log.e(TAG, "No sensor manager");
  }
    // Minimum requirements
    if (hasDefaultSensor(Sensor.TYPE_ACCELEROMETER)) {
      if (hasDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)) {
        Log.i(TAG, "Minimal sensors available");
      } else {
        Log.e(TAG, "No magnetic field sensor");
      }
    } else {
      if (hasDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)) {
        Log.e(TAG, "No accelerometer");
      } else {
        Log.e(TAG, "No magnetic field sensor or accelerometer");
      }
    }

    // Check for a particularly strange combo - it would be weird to have a rotation sensor
    // but no accelerometer or magnetic field sensor
    boolean hasRotationSensor = false;
    if (hasDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)) {
      if (hasDefaultSensor(Sensor.TYPE_ACCELEROMETER) && hasDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
          && hasDefaultSensor(Sensor.TYPE_GYROSCOPE)) {
        hasRotationSensor = true;
      } else if (hasDefaultSensor(Sensor.TYPE_ACCELEROMETER) && hasDefaultSensor(
          Sensor.TYPE_MAGNETIC_FIELD)) {
        // Even though it allegedly has the rotation vector sensor too many gyro-less phones
        // lie about this, so put these devices on the 'classic' sensor code for now.
        hasRotationSensor = false;
      }
    }

    // Enable Gyro if available and user hasn't already disabled it.
    if (!preferences.contains(ApplicationConstants.SHARED_PREFERENCE_DISABLE_GYRO)) {
      preferences.edit().putBoolean(
          ApplicationConstants.SHARED_PREFERENCE_DISABLE_GYRO, !hasRotationSensor).apply();
    }

    // Do we at least have defaults for the main ones?
    int[] importantSensorTypes = {Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_GYROSCOPE,
        Sensor.TYPE_MAGNETIC_FIELD, Sensor.TYPE_LIGHT, Sensor.TYPE_ROTATION_VECTOR,
        Sensor.TYPE_ORIENTATION};

    for (int sensorType : importantSensorTypes) {
      if (hasDefaultSensor(sensorType)) {
        Log.i(TAG, "No sensor of type " + sensorType);
      } else {
        Log.i(TAG, "Sensor present of type " + sensorType);
      }
    }

    // Lastly a dump of all the sensors.
    Log.d(TAG, "All sensors:");
    List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
    Set<String> sensorTypes = new HashSet<>();
    for (Sensor sensor : allSensors) {
      Log.i(TAG, sensor.getName());
    }
    Log.d(TAG, "All sensors summary:");
    for (String sensorType : sensorTypes) {
      Log.i(TAG, sensorType);
    }
  }

  private boolean hasDefaultSensor(int sensorType) {
    if (sensorManager == null) {
      return false;
    }
    Sensor sensor = sensorManager.getDefaultSensor(sensorType);
    if (sensor == null) {
      return false;
    }
    SensorEventListener dummy = new SensorEventListener() {
      @Override
      public void onSensorChanged(SensorEvent event) {
        // Nothing
      }

      @Override
      public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing
      }
    };
    boolean success = sensorManager.registerListener(
        dummy, sensor, SensorManager.SENSOR_DELAY_UI);
    sensorManager.unregisterListener(dummy);
    return success;
  }
}
