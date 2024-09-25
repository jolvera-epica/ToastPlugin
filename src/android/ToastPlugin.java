package com.ejemplo.toast;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;
import com.ejemplo.toast.Config;

public class ToastPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            String message = args.getString(0);
            this.showToast(message);
            callbackContext.success();
            return true;
        }
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(cordova.getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        // Initialize FaceTec SDK
        Config.initializeFaceTecSDKFromAutogeneratedConfig(cordova.getActivity().getApplicationContext(), successful -> {
            if(successful) {
                showToastLooper("Exito al iniciar");
            }
            else {
                showToastLooper("Fallo en el SDK");
            }
        });
    }

    private void showToastLooper(final String message) {
        // Run on UI thread
        this.cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(cordova.getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
