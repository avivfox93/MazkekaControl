package com.distillery.aaa.mazkekacontrol;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    Button save;
    Button temps;
    Button pins;
    EditText ipText;
    EditText portText;
    Context contx;
    SaveLoad saveLoad;
    String IP;
    int PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        contx = getApplicationContext();
        saveLoad = new SaveLoad(contx);
        save = (Button) findViewById(R.id.save);
        temps = (Button) findViewById(R.id.temps);
        pins = (Button) findViewById(R.id.pins);
        ipText = (EditText) findViewById(R.id.ipText);
        portText = (EditText) findViewById(R.id.portText);
        String[] address = saveLoad.loadAddress().split(",");
        if(address.length == 2){
            IP = address[0];
            PORT = Integer.valueOf(address[1]);
            ipText.setText(IP);
            portText.setText(Integer.toString(PORT));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] save = new String[2];
                save[0] = ipText.getText().toString();
                save[1] = portText.getText().toString();
                String fsave = save[0] + "," + save[1];
                saveLoad.saveAddress(fsave);
                saveLoad.setAddress();
            }
        });

        temps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this,TempsActivity.class);
                startActivity(myIntent);
            }
        });

        pins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this,PinsActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
