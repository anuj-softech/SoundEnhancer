package com.rock.soundenhancer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.media.audiofx.Equalizer;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class VolumeBoostService extends Service {
    private LoudnessEnhancer enhancer;
    Equalizer eq;
    private boolean isBoosting = false;
    private final int NOTIF_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : null;

        if ("TOGGLE".equals(action)) {
            updateNotification();
            if (isBoosting) stopBoost();
            else startBoost();
        } else if ("START".equals(action)) {
            updateNotification();
            startBoost();
        } else if ("STOP".equals(action)) {
            updateNotification();
            stopBoost();
        } else if ("STATUS".equals(action)) {
            broadcastStatus();
        }
        return START_STICKY;
    }

    private void startBoost() {
        if (!isBoosting) {
            try {
                startEnhancing();
                isBoosting = true;
                startForeground(NOTIF_ID, buildNotification());
                broadcastStatus();
            } catch (RuntimeException e) {
                broadcastStatus();
                isBoosting = false;
            }
        }
    }

        private void startEnhancing() {
                enhancer = new LoudnessEnhancer(0);
                enhancer.setTargetGain(1200);
                enhancer.setEnabled(true);
        }


    private void stopBoost() {
        if (enhancer != null) {
            enhancer.setEnabled(false);
            enhancer.release();
            enhancer = null;
        }
        if (eq != null) {
            eq.setEnabled(false);
            eq.release();
            eq = null;
        }

        isBoosting = false;
        stopForeground(true);
        broadcastStatus();
    }

    private Notification buildNotification() {
        Intent toggleIntent = new Intent(this, VolumeBoostService.class).setAction("TOGGLE");
        PendingIntent togglePending = PendingIntent.getService(this, 0, toggleIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, "boost")
                .setContentTitle("Volume Booster")
                .setContentText(isBoosting ? "Boost On" : "Boost Off")
                .setSmallIcon(R.drawable.ic_booster)
                .addAction(new NotificationCompat.Action(0, "Toggle", togglePending))
                .setOngoing(isBoosting)
                .build();
    }

    private void updateNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIF_ID, buildNotification());
    }

    @Override public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel("boost", "Volume Boost", NotificationManager.IMPORTANCE_LOW);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(ch);
        }
    }

    private void broadcastStatus() {
        Intent i = new Intent("BOOST_STATUS");
        Log.e(getClass().getSimpleName(), "Broadcasting from service");
        i.putExtra("enabled", isBoosting);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
}
