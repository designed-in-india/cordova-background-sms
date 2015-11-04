package com.vikram.cordova.sms;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSPlugin extends CordovaPlugin {


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("start".equals(action)) {
            class IncomingSms extends BroadcastReceiver {
                final SmsManager sms = SmsManager.getDefault();

                public void onReceive(Context context, Intent intent) {
                    final Bundle bundle = intent.getExtras();
                    try {
                        if (bundle != null) {
                            final Object[] pdusObj = (Object[]) bundle.get("pdus");
                            for (int i = 0; i < pdusObj.length; i++) {
                                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                                String senderNum = phoneNumber;
                                String message = currentMessage.getDisplayMessageBody();
                                Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(context,
                                        "senderNum: " + senderNum + ", message: " + message, duration);
                                toast.show();
                            } // end for loop
                        } // bundle is null
                    } catch (Exception e) {
                        Log.e("SmsReceiver", "Exception smsReceiver" + e);

                    }
                }
            }

            return true;

        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }
}


