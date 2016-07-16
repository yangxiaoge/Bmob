package com.sample.wmz.bmob;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cn.bmob.push.PushConstants;

/**
 * Created by wmz on 16-7-16.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Toast.makeText(context,intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
