package com.dukhin.callblockingtestdemo;

import android.telecom.Call;
import android.telecom.InCallService;
//only works if the dialer role is set :/
public class InCallServiceImpl extends InCallService {
    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);
        call.disconnect();
    }
}
