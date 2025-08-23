package com.even.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.provider.Settings;

import org.apache.cordova.*;
import org.json.JSONArray;

public class KioskPlugin extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    switch (action) {
      case "enable":
        enableImmersiveMode();
        callbackContext.success("Immersive mode enabled");
        return true;

      case "switchLauncher":
        openLauncherChooser();
        callbackContext.success("Launcher switch screen opened");
        return true;

      default:
        return false;
    }
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

  private void openLauncherChooser() {
    Intent intent = new Intent(Settings.ACTION_HOME_SETTINGS);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    cordova.getActivity().startActivity(intent);
  }
}
