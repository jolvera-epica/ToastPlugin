package com.ejemplo.toast;

import android.content.Context;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import com.facetec.sdk.*;


public class LivenessCheckProcessor implements FaceTecFaceScanProcessor {

    public LivenessCheckProcessor(String sessionToken, Context context) {
         FaceTecSessionActivity.createAndLaunchSession(context, LivenessCheckProcessor.this, sessionToken);
    }

    @Override
    public void processSessionWhileFaceTecSDKWaits(FaceTecSessionResult faceTecSessionResult, FaceTecFaceScanResultCallback faceTecFaceScanResultCallback) {
        Log.d("FACE_TEC","LLEGO AQUI");
    }
}