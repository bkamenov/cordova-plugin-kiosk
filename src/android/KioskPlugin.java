package com.even.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.MotionEvent;

import org.apache.cordova.*;
import org.json.JSONArray;

public class KioskPlugin extends CordovaPlugin {

  private View overlayView = null;
  private WindowManager windowManager = null;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    switch (action) {
      case "enableImmersiveMode":
        enableImmersiveMode();
        callbackContext.success("Immersive mode enabled");
        return true;

      case "disableImmersiveMode":
        disableImmersiveMode();
        callbackContext.success("Immersive mode disabled");
        return true;

      case "isLauncher":
        isLauncher(callbackContext);
        return true;

      case "switchLauncher":
        openLauncherChooser();
        callbackContext.success("Launcher switch screen opened");
        return true;

      default:
        return false;
    }
  }

  private void isLauncher(CallbackContext callbackContext) {
    Activity activity = cordova.getActivity();
    Context context = activity.getApplicationContext();
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    String currentHomePackage = context.getPackageManager().resolveActivity(intent, 0).activityInfo.packageName;
    String appPackage = context.getPackageName();

    boolean isDefaultLauncher = currentHomePackage.equals(appPackage);
    PluginResult result = new PluginResult(PluginResult.Status.OK, isDefaultLauncher);
    callbackContext.sendPluginResult(result);
  }

  private void enableImmersiveMode() {
    final Activity activity = cordova.getActivity();
    cordova.getActivity().runOnUiThread(() -> {
      activity.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      }
    });
  }

  private void disableImmersiveMode() {
    final Activity activity = cordova.getActivity();
    cordova.getActivity().runOnUiThread(() -> {
      activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

      View decorView = activity.getWindow().getDecorView();
      decorView.setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_VISIBLE
              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    });
  }

  private void openLauncherChooser() {
    Intent intent = new Intent(Settings.ACTION_HOME_SETTINGS);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    cordova.getActivity().startActivity(intent);
  }
}
