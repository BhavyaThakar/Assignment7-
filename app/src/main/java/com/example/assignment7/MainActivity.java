package com.example.assignment7;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    private FloatingActionButton addAddress;
    private ArrayList<MClass> bookMarks;
    private DatabaseHandler dbHandler;
    private LocationRVAdapter locationRVAdapter;
    private RecyclerView locationRV;
    ImageView iv_battery;
    TextView tv_battery;
    Handler handler;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addAddress = findViewById(R.id.floatingActionButton);
        bookMarks = new ArrayList<>();
        dbHandler = new DatabaseHandler(MainActivity.this);
        bookMarks = dbHandler.readLocations();

        locationRVAdapter = new LocationRVAdapter(bookMarks, MainActivity.this);
        locationRV = findViewById(R.id.recycler_view);
        locationRV.setLayoutManager(new LinearLayoutManager(this));
        locationRV.setAdapter(locationRVAdapter);
        locationRVAdapter.notifyDataSetChanged();

        iv_battery = findViewById(R.id.battery_iv);
        tv_battery = findViewById(R.id.battery_tv);

        runnable = new Runnable() {
            @Override
            public void run() {
                int level = (int) batteryLevel();
                tv_battery.setText(level + " %");


                if (level == 100){
                    iv_battery.setImageResource(R.drawable.battery_full);
                }
                if (level >= 87 && level<100){
                    iv_battery.setImageResource(R.drawable.battery_6bar);
                }
                if (level >= 75 && level<87){
                    iv_battery.setImageResource(R.drawable.battery_5bar);
                }
                if (level >= 62 && level<75){
                    iv_battery.setImageResource(R.drawable.battery_4bar);
                }
                if (level >= 50&& level<62){
                    iv_battery.setImageResource(R.drawable.battery_3bar);
                }
                if (level >= 38&& level<50){
                    iv_battery.setImageResource(R.drawable.battery_2bar);
                }
                if (level >= 25 && level<38){
                    iv_battery.setImageResource(R.drawable.battery_1bar);
                }
                if (level >= 13 && level<25){
                    iv_battery.setImageResource(R.drawable.battery_0bar);
                }
                if (level <= 12){
                    iv_battery.setImageResource(R.drawable.battery_alert);
                }

                handler.postDelayed(runnable, 5000);
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 0);


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


    }
    public float batteryLevel(){
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if (level == -1 || scale == -1){
            return 50.0f;
        }
        return  (float) level / (float) scale * 100.0f;
    }




}