package com.rock.soundenhancer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.rock.soundenhancer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding lb;
    private boolean isBoosting = false;

    private final BroadcastReceiver statusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isBoosting = intent.getBooleanExtra("enabled", false);
            updateStatus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lb = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(lb.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        lb.toggleButton.setOnClickListener(v -> {
            Intent i = new Intent(this, VolumeBoostService.class);
            i.setAction("TOGGLE");
            startService(i);
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(statusReceiver, new IntentFilter("BOOST_STATUS"));

        // Start service — it will broadcast real status
        startService(new Intent(this, VolumeBoostService.class));
    }

    private void updateStatus() {
        lb.statusText.setText("Status: " + (isBoosting ? "Boosting" : "Idle"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(statusReceiver);
    }
}
