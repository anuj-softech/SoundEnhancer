package com.rock.soundenhancer;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class VolumeBoostTileService extends TileService {

    private boolean isBoosting = false;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isBoosting = intent.getBooleanExtra("enabled", false);
            Log.e(getClass().getSimpleName(), "broadcast recieved: ");
            updateTile();
        }
    };

    @Override
    public void onClick() {
        Intent intent = new Intent(this, VolumeBoostService.class);
        intent.setAction("TOGGLE");
        startForegroundService(intent);
        updateTile();
    }

    @Override
    public void onStartListening() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("BOOST_STATUS"));
        requestStatusUpdate();
    }

    @Override
    public void onStopListening() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void requestStatusUpdate() {
        Intent intent = new Intent(this, VolumeBoostService.class);
        intent.setAction("STATUS");
        startForegroundService(intent);
    }

    private void updateTile() {
        Tile tile = getQsTile();
        if (tile == null) return;
        tile.setState(isBoosting ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setIcon(Icon.createWithResource(this, R.drawable.ic_booster));
        tile.setLabel("Volume Booster");
        tile.updateTile();
    }
}
