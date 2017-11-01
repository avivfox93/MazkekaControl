package com.distillery.aaa.mazkekacontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;

public class MainActivity extends AppCompatActivity {
    UDPServer server;
    Notifications not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        not = new Notifications();
        server = new UDPServer(new UDPServer.onReceive() {
            @Override
            public void onReceive(DatagramPacket data) {
                JSONObject json = new JSONObject();
                double temp = 0.0;
                String sit = "";
                try{
                    json = new JSONObject(new String(data.getData()));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                try{
                    temp = json.getDouble("temp");
                    sit = json.getString("sit");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if (!sit.isEmpty()){

                }
            }
        });
    }
}
