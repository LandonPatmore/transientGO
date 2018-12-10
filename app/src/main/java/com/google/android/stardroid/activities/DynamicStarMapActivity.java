// Copyright 2010 Google Inc.
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

package com.google.android.stardroid.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.stardroid.ApplicationConstants;
import com.google.android.stardroid.R;
import com.google.android.stardroid.activities.dialogs.EulaDialogFragment;
import com.google.android.stardroid.activities.dialogs.HelpDialogFragment;
import com.google.android.stardroid.activities.dialogs.NoSensorsDialogFragment;
import com.google.android.stardroid.activities.util.ActivityLightLevelChanger;
import com.google.android.stardroid.activities.util.ActivityLightLevelChanger.NightModeable;
import com.google.android.stardroid.activities.util.ActivityLightLevelManager;
import com.google.android.stardroid.activities.util.FullscreenControlsManager;
import com.google.android.stardroid.activities.util.GooglePlayServicesChecker;
import com.google.android.stardroid.base.Lists;
import com.google.android.stardroid.control.AstronomerModel;
import com.google.android.stardroid.control.AstronomerModel.Pointing;
import com.google.android.stardroid.control.ControllerGroup;
import com.google.android.stardroid.control.MagneticDeclinationCalculatorSwitcher;
import com.google.android.stardroid.data.TransientData;
import com.google.android.stardroid.data.UserData;
import com.google.android.stardroid.inject.HasComponent;
import com.google.android.stardroid.layers.LayerManager;
import com.google.android.stardroid.renderer.RendererController;
import com.google.android.stardroid.renderer.SkyRenderer;
import com.google.android.stardroid.renderer.util.AbstractUpdateClosure;
import com.google.android.stardroid.renderer.util.SearchHelper;
import com.google.android.stardroid.touch.DragRotateZoomGestureDetector;
import com.google.android.stardroid.touch.GestureInterpreter;
import com.google.android.stardroid.touch.MapMover;
import com.google.android.stardroid.units.GeocentricCoordinates;
import com.google.android.stardroid.units.Vector3;
import com.google.android.stardroid.util.MathUtil;
import com.google.android.stardroid.util.MiscUtil;
import com.google.android.stardroid.util.SensorAccuracyMonitor;
import com.google.android.stardroid.views.ButtonLayerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * The main map-rendering Activity.
 */
public class DynamicStarMapActivity extends InjectableActivity
        implements OnSharedPreferenceChangeListener, HasComponent<DynamicStarMapComponent> {
  private static final int TIME_DISPLAY_DELAY_MILLIS = 1000;
  private FullscreenControlsManager fullscreenControlsManager;

  @Override
  public DynamicStarMapComponent getComponent() {
    return daggerComponent;
  }

  /**
   * Passed to the renderer to get per-frame updates from the model.
   *
   * @author John Taylor
   */
  private static final class RendererModelUpdateClosure extends AbstractUpdateClosure {
    private RendererController rendererController;
    private AstronomerModel model;

    public RendererModelUpdateClosure(AstronomerModel model,
                                      RendererController rendererController) {
      this.model = model;
      this.rendererController = rendererController;
    }

    @Override
    public void run() {
      Pointing pointing = model.getPointing();
      float directionX = pointing.getLineOfSightX();
      float directionY = pointing.getLineOfSightY();
      float directionZ = pointing.getLineOfSightZ();

      float upX = pointing.getPerpendicularX();
      float upY = pointing.getPerpendicularY();
      float upZ = pointing.getPerpendicularZ();

      rendererController.queueSetViewOrientation(directionX, directionY, directionZ, upX, upY, upZ);

      Vector3 up = model.getPhoneUpDirection();
      rendererController.queueTextAngle(MathUtil.atan2(up.x, up.y));
      rendererController.queueViewerUpDirection(model.getZenith().copy());

      float fieldOfView = model.getFieldOfView();
      rendererController.queueFieldOfView(fieldOfView);
    }
  }

  // Activity for result Ids
  public static final int GOOGLE_PLAY_SERVICES_REQUEST_CODE = 1;
  public static final int GOOGLE_PLAY_SERVICES_REQUEST_LOCATION_PERMISSION_CODE = 2;
  // End Activity for result Ids

  private static final float ROTATION_SPEED = 10;
  private static final String TAG = MiscUtil.getTag(DynamicStarMapActivity.class);

  @Inject ControllerGroup controller;
  private GestureDetector gestureDetector;
  @Inject AstronomerModel model;
  private RendererController rendererController;
  private boolean nightMode = false;
  private boolean searchMode = false;
  private GeocentricCoordinates searchTarget = GeocentricCoordinates.getInstance(0, 0);

  @Inject SharedPreferences sharedPreferences;
  private GLSurfaceView skyView;
  private PowerManager.WakeLock wakeLock;
  private String searchTargetName;
  @Inject LayerManager layerManager;
  // TODO(widdows): Figure out if we should break out the
  // time dialog and time player into separate activities.
  private View timePlayerUI;
  private DynamicStarMapComponent daggerComponent;
  @Inject Handler handler;
  @Inject GooglePlayServicesChecker playServicesChecker;
  @Inject FragmentManager fragmentManager;
  @Inject EulaDialogFragment eulaDialogFragmentNoButtons;
  @Inject HelpDialogFragment helpDialogFragment;
  @Inject NoSensorsDialogFragment noSensorsDialogFragment;
  @Inject SensorAccuracyMonitor sensorAccuracyMonitor;
  // A list of runnables to post on the handler when we resume.
  private List<Runnable> onResumeRunnables = new ArrayList<>();
  private Button cTran;
  private boolean finish;

  // We need to maintain references to these objects to keep them from
  // getting gc'd.
  @SuppressWarnings("unused")
  @Inject MagneticDeclinationCalculatorSwitcher magneticSwitcher;

  private DragRotateZoomGestureDetector dragZoomRotateDetector;
  @Inject Animation flashAnimation;
  private ActivityLightLevelManager activityLightLevelManager;
  private long sessionStartTime;

  @Override
  public void onCreate(Bundle icicle) {
    Log.d(TAG, "onCreate at " + System.currentTimeMillis());
    super.onCreate(icicle);

    daggerComponent = DaggerDynamicStarMapComponent.builder()
            .applicationComponent(getApplicationComponent())
            .dynamicStarMapModule(new DynamicStarMapModule(this)).build();
    daggerComponent.inject(this);

    sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    // Set up full screen mode, hide the system UI etc.
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // TODO(jontayler): upgrade to
    // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    // when we reach API level 16.
    // http://developer.android.com/training/system-ui/immersive.html for the right way
    // to do it at API level 19.
    //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    // Eventually we should check at the point of use, but this will do for now.  If the
    // user revokes the permission later then odd things may happen.
    playServicesChecker.maybeCheckForGooglePlayServices();

    initializeModelViewController();
    checkForSensorsAndMaybeWarn();

    // Search related
    setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

    ActivityLightLevelChanger activityLightLevelChanger = new ActivityLightLevelChanger(this,
            new NightModeable() {
              @Override
              public void setNightMode(boolean nightMode1) {
                DynamicStarMapActivity.this.rendererController.queueNightVisionMode(nightMode1);
              }
            });
    activityLightLevelManager = new ActivityLightLevelManager(activityLightLevelChanger,
            sharedPreferences);

    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);

    Log.d(TAG, "-onCreate at " + System.currentTimeMillis());

    finish = false;
    cTran = (Button) findViewById(R.id.cTransient);
    cTran.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        System.out.println("Button clicked!!!!!!!!!!!");
        System.out.println(SearchHelper.INSTANCE.targetInFocusRadius());
        if (SearchHelper.INSTANCE.targetInFocusRadius()) {
          startActivity(new Intent(DynamicStarMapActivity.this, CaughtTransientActivity.class));
          finish = true;
        }
      }
    });

    ProgressBar p = (ProgressBar) findViewById(R.id.userExpBar);
    p.setProgress(UserData.INSTANCE.getUserExp());

    TextView exp = (TextView) findViewById(R.id.EXP);
    exp.setText("Exp: " + UserData.INSTANCE.getUserExp() + "/100");

    TextView lev = (TextView) findViewById(R.id.userLevel);
    String uL = Integer.toString(UserData.INSTANCE.getUserLevel());
    lev.setText(uL);

    TextView sco = (TextView) findViewById(R.id.userScore);
    String uS = Integer.toString(UserData.INSTANCE.getUserScore());
    sco.setText(uS);

    activateSearchTarget(TransientData.INSTANCE.getData().get(0).getCoords());

  }

  private void checkForSensorsAndMaybeWarn() {
    SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null
            && sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
      Log.i(TAG, "Minimum sensors present");
      // We want to reset to auto mode on every restart, as users seem to get
      // stuck in manual mode and can't find their way out.
      // TODO(johntaylor): this is a bit of an abuse of the prefs system, but
      // the button we use is wired into the preferences system.  Should probably
      // change this to a use a different mechanism.
      sharedPreferences.edit().putBoolean(ApplicationConstants.AUTO_MODE_PREF_KEY, true).apply();
      setAutoMode(true);
      return;
    }
    // Missing at least one sensor.  Warn the user.
    handler.post(new Runnable() {
      @Override
      public void run() {
        if (!sharedPreferences
                .getBoolean(ApplicationConstants.NO_WARN_ABOUT_MISSING_SENSORS, false)) {
          Log.d(TAG, "showing no sensor dialog");
          noSensorsDialogFragment.show(fragmentManager, "No sensors dialog");
          // First time, force manual mode.
          sharedPreferences.edit().putBoolean(ApplicationConstants.AUTO_MODE_PREF_KEY, false)
                  .apply();
          setAutoMode(false);
        }
      }
    });

  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "DynamicStarMap onDestroy");
    super.onDestroy();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
      case (KeyEvent.KEYCODE_DPAD_LEFT):
        Log.d(TAG, "Key left");
        controller.rotate(-10.0f);
        break;
      case (KeyEvent.KEYCODE_DPAD_RIGHT):
        Log.d(TAG, "Key right");
        controller.rotate(10.0f);
        break;
      default:
        Log.d(TAG, "Key: " + event);
        return super.onKeyDown(keyCode, event);
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);
    //fullscreenControlsManager.delayHideTheControls();
    switch (item.getItemId()) {
      case R.id.menu_item_settings:
        Log.d(TAG, "Settings");
        startActivity(new Intent(this, EditSettingsActivity.class));
        break;
      case R.id.menu_item_help:
        Log.d(TAG, "Help");
        helpDialogFragment.show(fragmentManager, "Help Dialog");
        break;
      case R.id.menu_item_dim:
        Log.d(TAG, "Toggling nightmode");
        nightMode = !nightMode;
        sharedPreferences.edit().putString(ActivityLightLevelManager.LIGHT_MODE_KEY,
                nightMode ? "NIGHT" : "DAY").commit();
        break;
      case R.id.menu_item_tos:
        Log.d(TAG, "Loading ToS");
        eulaDialogFragmentNoButtons.show(fragmentManager, "Eula Dialog No Buttons");
        break;
      case R.id.menu_item_calibrate:
        Log.d(TAG, "Loading Calibration");
        Intent intent = new Intent(this, CompassCalibrationActivity.class);
        intent.putExtra(CompassCalibrationActivity.HIDE_CHECKBOX, true);
        startActivity(intent);
        break;
      case R.id.menu_item_diagnostics:
        Log.d(TAG, "Loading Diagnostics");
        startActivity(new Intent(this, DiagnosticActivity.class));
        break;
      case R.id.transientsmenu:
        Log.d(TAG, "Loading Transient");
        startActivity(new Intent(this, ListOfTransientsActivity.class));
        break;
      case R.id.transientscaught:
        Log.d(TAG, "Loading Caught");
        startActivity(new Intent(this, CaughtTransientList.class));
        break;
      default:
        Log.e(TAG, "Unwired-up menu item");
        return false;
    }
    return true;
  }

  @Override
  public void onStart() {
    super.onStart();
    sessionStartTime = System.currentTimeMillis();
  }

  private enum SessionBucketLength {
    LESS_THAN_TEN_SECS(10), TEN_SECS_TO_THIRTY_SECS(30),
    THIRTY_SECS_TO_ONE_MIN(60), ONE_MIN_TO_FIVE_MINS(300),
    MORE_THAN_FIVE_MINS(Integer.MAX_VALUE);
    private int seconds;
    private SessionBucketLength(int seconds) {
      this.seconds = seconds;
    }
  }

  private SessionBucketLength getSessionLengthBucket(int sessionLengthSeconds) {
    for (SessionBucketLength bucket : SessionBucketLength.values()) {
      if (sessionLengthSeconds < bucket.seconds) {
        return bucket;
      }
    }
    Log.e(TAG, "Programming error - should not get here");
    return SessionBucketLength.MORE_THAN_FIVE_MINS;
  }

  @Override
  public void onStop() {
    super.onStop();
    // Define a session as being the time between the main activity being in
    // the foreground and pushed back.  Note that this will mean that sessions
    // do get interrupted by (e.g.) loading preference or help screens.
    int sessionLengthSeconds = (int) ((
            System.currentTimeMillis() - sessionStartTime) / 1000);
    SessionBucketLength bucket = getSessionLengthBucket(sessionLengthSeconds);
  }

  @Override
  public void onResume() {

    Log.d(TAG, "onResume at " + System.currentTimeMillis());
    super.onResume();
    Log.i(TAG, "Resuming");

    wakeLock.acquire();
    Log.i(TAG, "Starting view");
    skyView.onResume();
    Log.i(TAG, "Starting controller");
    controller.start();
    activityLightLevelManager.onResume();
    if (controller.isAutoMode()) {
      sensorAccuracyMonitor.start();
    }
    for (Runnable runnable : onResumeRunnables) {
      handler.post(runnable);
    }
    Log.d(TAG, "-onResume at " + System.currentTimeMillis());

    activateSearchTarget(TransientData.INSTANCE.getData().get(0).getCoords());
  }

  @Override
  public void onPause() {
    Log.d(TAG, "DynamicStarMap onPause");
    super.onPause();
    sensorAccuracyMonitor.stop();
    for (Runnable runnable : onResumeRunnables) {
      handler.removeCallbacks(runnable);
    }
    activityLightLevelManager.onPause();
    controller.stop();
    skyView.onPause();
    wakeLock.release();

    // Debug.stopMethodTracing();
    Log.d(TAG, "DynamicStarMap -onPause");

    if(finish){finish();}


  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Log.d(TAG, "Preferences changed: key=" + key);
    switch (key) {
      case ApplicationConstants.AUTO_MODE_PREF_KEY:
        boolean autoMode = sharedPreferences.getBoolean(key, true);
        Log.d(TAG, "Automode is set to " + autoMode);
        if (!autoMode) {
          Log.d(TAG, "Switching to manual control");
        } else {
          Log.d(TAG, "Switching to sensor control");
        }
        setAutoMode(autoMode);
        break;
      default:
        return;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // Log.d(TAG, "Touch event " + event);
    // Either of the following detectors can absorb the event, but one
    // must not hide it from the other
    boolean eventAbsorbed = false;
    if (gestureDetector.onTouchEvent(event)) {
      eventAbsorbed = true;
    }
    if (dragZoomRotateDetector.onTouchEvent(event)) {
      eventAbsorbed = true;
    }
    return eventAbsorbed;
  }

  @Override
  public boolean onTrackballEvent(MotionEvent event) {
    // Log.d(TAG, "Trackball motion " + event);
    controller.rotate(event.getX() * ROTATION_SPEED);
    return true;
  }

  private void initializeModelViewController() {
    Log.i(TAG, "Initializing Model, View and Controller @ " + System.currentTimeMillis());
    setContentView(R.layout.skyrenderer);
    skyView = (GLSurfaceView) findViewById(R.id.skyrenderer_view);
    // We don't want a depth buffer.
    skyView.setEGLConfigChooser(false);
    SkyRenderer renderer = new SkyRenderer(getResources());
    skyView.setRenderer(renderer);

    rendererController = new RendererController(renderer, skyView);
    // The renderer will now call back every frame to get model updates.
    rendererController.addUpdateClosure(
            new RendererModelUpdateClosure(model, rendererController));

    Log.i(TAG, "Setting layers @ " + System.currentTimeMillis());
    layerManager.registerWithRenderer(rendererController);
    Log.i(TAG, "Set up controllers @ " + System.currentTimeMillis());
    controller.setModel(model);
    wireUpScreenControls(); // TODO(johntaylor) move these?
  }

  private void setAutoMode(boolean auto) {
    controller.setAutoMode(auto);
    if (auto) {
      sensorAccuracyMonitor.start();
    } else {
      sensorAccuracyMonitor.stop();
    }
  }

  private void wireUpScreenControls() {

    ButtonLayerView providerButtons = (ButtonLayerView) findViewById(R.id.layer_buttons_control);

    int numChildren = providerButtons.getChildCount();
    List<View> buttonViews = new ArrayList<>();
    for (int i = 0; i < numChildren; ++i) {
      ImageButton button = (ImageButton) providerButtons.getChildAt(i);
      buttonViews.add(button);
    }

    fullscreenControlsManager = new FullscreenControlsManager(
            this.findViewById(R.id.main_sky_view),
            Lists.<View>asList(providerButtons),
            buttonViews);

    MapMover mapMover = new MapMover(model, controller, this);

    gestureDetector = new GestureDetector(this, new GestureInterpreter(
            fullscreenControlsManager, mapMover));



    dragZoomRotateDetector = new DragRotateZoomGestureDetector(mapMover);
  }


  @Override
  protected void onRestoreInstanceState(Bundle icicle) {
    Log.d(TAG, "DynamicStarMap onRestoreInstanceState");
    super.onRestoreInstanceState(icicle);
    if (icicle == null) return;
    searchMode = icicle.getBoolean(ApplicationConstants.BUNDLE_SEARCH_MODE);
    float x = icicle.getFloat(ApplicationConstants.BUNDLE_X_TARGET);
    float y = icicle.getFloat(ApplicationConstants.BUNDLE_Y_TARGET);
    float z = icicle.getFloat(ApplicationConstants.BUNDLE_Z_TARGET);
    searchTarget = TransientData.INSTANCE.getData().get(0).getCoords();
    if (searchMode) {
      rendererController.queueEnableSearchOverlay(searchTarget);
    }
    nightMode = icicle.getBoolean(ApplicationConstants.BUNDLE_NIGHT_MODE, false);
  }

  @Override
  protected void onSaveInstanceState(Bundle icicle) {
    Log.d(TAG, "DynamicStarMap onSaveInstanceState");
    icicle.putBoolean(ApplicationConstants.BUNDLE_SEARCH_MODE, searchMode);
    icicle.putFloat(ApplicationConstants.BUNDLE_X_TARGET, searchTarget.x);
    icicle.putFloat(ApplicationConstants.BUNDLE_Y_TARGET, searchTarget.y);
    icicle.putFloat(ApplicationConstants.BUNDLE_Z_TARGET, searchTarget.z);
    icicle.putBoolean(ApplicationConstants.BUNDLE_NIGHT_MODE, nightMode);
    super.onSaveInstanceState(icicle);
  }

  public void activateSearchTarget(GeocentricCoordinates target) {
    // Store these for later.
    searchTarget = target;
    rendererController.queueViewerUpDirection(model.getZenith().copy());
    rendererController.queueEnableSearchOverlay(target.copy());
    boolean autoMode = sharedPreferences.getBoolean(ApplicationConstants.AUTO_MODE_PREF_KEY, true);
    if (!autoMode) {
      controller.teleport(target);
    }
  }

  public AstronomerModel getModel() {
    return model;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == GOOGLE_PLAY_SERVICES_REQUEST_CODE) {
      playServicesChecker.runAfterDialog();
      return;
    }
    Log.w(TAG, "Unhandled activity result");
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String[] permissions,
                                         int[] grantResults) {
    if (requestCode == GOOGLE_PLAY_SERVICES_REQUEST_LOCATION_PERMISSION_CODE) {
      playServicesChecker.runAfterPermissionsCheck(requestCode, permissions, grantResults);
      return;
    }
    Log.w(TAG, "Unhandled request permissions result");
  }
}
