package com.dukhin.callblockingtestdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telecom.Call;
import android.telecom.CallScreeningService;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class CallScreenImpl extends CallScreeningService {

    @Override
    public void onScreenCall(@NonNull Call.Details callDetails) {

        String number = callDetails.getHandle().toString();
        number = number.substring(number.indexOf(":") + 1);
        TelecomManager telecomManager = (TelecomManager) getSystemService(TELECOM_SERVICE);
        //telecomManager.accept
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        List<PhoneAccountHandle> lstPA=telecomManager.getCallCapablePhoneAccounts();
        String baseNum = telecomManager.getLine1Number(lstPA.get(0));
        baseNum = baseNum.replaceAll("-", "");
        number = number.replaceAll("-", "");
        if (baseNum.length() > 10) {
            //assuming country code is 1 digit
            baseNum = baseNum.substring(2);
        }
        if (number.length() > 10) {
            number = number.substring(number.length()-10);
        }
        //Log.d("yolo", number + " " + baseNum);

        if (number.startsWith(baseNum.substring(0,7)) && !number.equals(baseNum)) {
            CallResponse.Builder builder = new CallResponse.Builder();
            builder.setDisallowCall(true);
            builder.setRejectCall(true);
            builder.setSkipCallLog(true);
            builder.setSkipNotification(true);

            respondToCall(callDetails,  builder.build());
            //Toast.makeText(this.getApplicationContext(), "ended Call from: " + number +"to: " + baseNum, Toast.LENGTH_SHORT).show();


        }

    }
}


