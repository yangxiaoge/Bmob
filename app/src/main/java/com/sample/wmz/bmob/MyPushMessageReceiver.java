package com.sample.wmz.bmob;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import cn.bmob.push.PushConstants;

/**
 * Created by wmz on 16-7-16.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    Notification myNotication;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Toast.makeText(context,intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING),
                    Toast.LENGTH_SHORT).show();

            // android 6.0 兼容!
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder  = new Notification.Builder(context);
            builder.setAutoCancel(false);
            builder.setTicker("推送");
            //设置小图标
            builder.setSmallIcon(R.mipmap.ic_launcher);
            //设置大图标
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
            //设置标题
            builder.setContentTitle("Bmob App");
            //设置标题下面的内容
            builder.setContentText("You have a new message");
            //正文内容
            builder.setSubText(intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));   //API level 16
            //设置时间
            builder.setWhen(System.currentTimeMillis());
            builder.build();

            myNotication = builder.getNotification();
            notificationManager.notify(11, myNotication);
        }
    }
}
