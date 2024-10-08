package com.ejemplo.toast;

import android.content.Context;
import com.facetec.sdk.*;

public class Config {
    // -------------------------------------
    // REQUIRED
    // Available at https://dev.facetec.com/account
    // NOTE: This field is auto-populated by the FaceTec SDK Configuration Wizard.
    public static String DeviceKeyIdentifier = "d1044RKrihN8rXDBYa5BIKUiDVvZOIBf";

    // -------------------------------------
    // REQUIRED
    // The URL to call to process FaceTec SDK Sessions.
    // In Production, you likely will handle network requests elsewhere and without the use of this variable.
    // See https://dev.facetec.com/security-best-practices?link=facetec-server-rest-endpoint-security for more information.
    // NOTE: This field is auto-populated by the FaceTec SDK Configuration Wizard.
    public static String BaseURL = "https://api.facetec.com/api/v3.1/biometrics";

    // -------------------------------------
    // REQUIRED
    // The FaceScan Encryption Key you define for your application.
    // Please see https://dev.facetec.com/facemap-encryption-keys for more information.
    // NOTE: This field is auto-populated by the FaceTec SDK Configuration Wizard.
    static String PublicFaceScanEncryptionKey =
        "-----BEGIN PUBLIC KEY-----\n" +
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5PxZ3DLj+zP6T6HFgzzk\n" +
        "M77LdzP3fojBoLasw7EfzvLMnJNUlyRb5m8e5QyyJxI+wRjsALHvFgLzGwxM8ehz\n" +
        "DqqBZed+f4w33GgQXFZOS4AOvyPbALgCYoLehigLAbbCNTkeY5RDcmmSI/sbp+s6\n" +
        "mAiAKKvCdIqe17bltZ/rfEoL3gPKEfLXeN549LTj3XBp0hvG4loQ6eC1E1tRzSkf\n" +
        "GJD4GIVvR+j12gXAaftj3ahfYxioBH7F7HQxzmWkwDyn3bqU54eaiB7f0ftsPpWM\n" +
        "ceUaqkL2DZUvgN0efEJjnWy5y1/Gkq5GGWCROI9XG/SwXJ30BbVUehTbVcD70+ZF\n" +
        "8QIDAQAB\n" +
        "-----END PUBLIC KEY-----"    
        ;


    // -------------------------------------
    // Convenience method to initialize the FaceTec SDK.
    // NOTE: This function is auto-populated by the FaceTec SDK Configuration Wizard based on the Keys issued to your Apps.

    public static void initializeFaceTecSDKFromAutogeneratedConfig(Context context, FaceTecSDK.InitializeCallback callback) {
        FaceTecSDK.initializeInDevelopmentMode(context, DeviceKeyIdentifier, PublicFaceScanEncryptionKey, callback);
    }



}