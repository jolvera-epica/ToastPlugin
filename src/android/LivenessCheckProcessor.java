package com.ejemplo.toast;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import com.facetec.sdk.*;


public class LivenessCheckProcessor implements FaceTecFaceScanProcessor {

    private boolean success = false;

    public LivenessCheckProcessor(String sessionToken, Context context) {
        FaceTecSessionActivity.createAndLaunchSession(context, LivenessCheckProcessor.this, sessionToken);
    }

    @Override
    public void processSessionWhileFaceTecSDKWaits(FaceTecSessionResult faceTecSessionResult, FaceTecFaceScanResultCallback faceTecFaceScanResultCallback) {
        Log.d("FACE_TEC","LLEGO AQUI");

        if(faceTecSessionResult.getStatus() != FaceTecSessionStatus.SESSION_COMPLETED_SUCCESSFULLY) {
            Log.d("FaceTecSDKSampleApp", "Session was not completed successfully, cancelling.");
            NetworkingHelpers.cancelPendingRequests();
            faceTecFaceScanResultCallback.cancel();
            return;
        }

        JSONObject parameters = new JSONObject();

        try {
            parameters.put("faceScan", faceTecSessionResult.getFaceScanBase64());
            parameters.put("auditTrailImage", faceTecSessionResult.getAuditTrailCompressedBase64()[0]);
            parameters.put("lowQualityAuditTrailImage", faceTecSessionResult.getLowQualityAuditTrailCompressedBase64()[0]);
        }
        catch(JSONException e) {
            e.printStackTrace();
            Log.d("FaceTecSDKSampleApp", "Exception raised while attempting to create JSON payload for upload.");
        }

        okhttp3.Request request = new okhttp3.Request.Builder()
            .url(Config.BaseURL + "/liveness-3d")
            .header("Content-Type", "application/json")
            .header("X-Device-Key", Config.DeviceKeyIdentifier)
            .header("User-Agent", FaceTecSDK.createFaceTecAPIUserAgentString(faceTecSessionResult.getSessionId()))
            .header("X-User-Agent", FaceTecSDK.createFaceTecAPIUserAgentString(faceTecSessionResult.getSessionId()))
            .post(new ProgressRequestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parameters.toString()),
                new ProgressRequestBody.Listener() {
                    @Override
                    public void onUploadProgressChanged(long bytesWritten, long totalBytes) {
                        final float uploadProgressPercent = ((float)bytesWritten) / ((float)totalBytes);
                        faceTecFaceScanResultCallback.uploadProgress(uploadProgressPercent);
                    }
                }))
            .build();

        NetworkingHelpers.getApiClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {

                String responseString = response.body().string();
                response.body().close();
                try {
                    JSONObject responseJSON = new JSONObject(responseString);
                    boolean wasProcessed = responseJSON.getBoolean("wasProcessed");
                    boolean error = responseJSON.getBoolean("error");

                    if (error) {
                        String errorMessage = responseJSON.optString("errorMessage");
                        Log.d("FaceTecSDKSampleApp", "Error while processing FaceScan. " + errorMessage);
                        faceTecFaceScanResultCallback.cancel();
                        return;
                    }

                    String scanResultBlob = responseJSON.getString("scanResultBlob");

                    if(wasProcessed) {

                        FaceTecCustomization.overrideResultScreenSuccessMessage = "Face Scanned\n3D Liveness Proven";

                        success = faceTecFaceScanResultCallback.proceedToNextStep(scanResultBlob);
                    }
                    else {
                        faceTecFaceScanResultCallback.cancel();
                    }
                }
                catch(JSONException e) {
                    e.printStackTrace();
                    Log.d("FaceTecSDKSampleApp", "Exception raised while attempting to parse JSON result.");
                    faceTecFaceScanResultCallback.cancel();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @Nullable IOException e) {
                Log.d("FaceTecSDKSampleApp", "Exception raised while attempting HTTPS call.");
                faceTecFaceScanResultCallback.cancel();
            }
        });
    }

    public boolean isSuccess() {
        return this.success;
    }
}