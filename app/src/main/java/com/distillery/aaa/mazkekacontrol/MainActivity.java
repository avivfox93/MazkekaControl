package com.distillery.aaa.mazkekacontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;

public class MainActivity extends AppCompatActivity {
    UDPServer server;
    Notifications not;
    public int meth = 0;
    public int eth = 0;
    public int tails = 0;
    public int finish = 0;
    private double temp = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        not = new Notifications();
        server = new UDPServer(new UDPServer.onReceive() {
            @Override
            public void onReceive(DatagramPacket data) {
                JSONObject json = new JSONObject();
                String sit = "";
                try{
                    json = new JSONObject(new String(data.getData()));
                    try{
                        temp = json.getDouble("temp");
                        sit = json.getString("sit");
                        if(!manageNotifications(sit)){
                            not.unoti(Double.toString(temp),sit);
                        }
                    }catch (JSONException e){
                        if (!sit.isEmpty() && temp > 0){
                            not.unoti(Double.toString(temp),"NULL");
                        }
                        e.printStackTrace();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    private boolean manageNotifications(String sit){
        boolean x = false;
        switch (sit){
            case "meth":
                if (meth < 2){
                    not.noti(Double.toString(temp),sit);
                    meth++;
                }
                x = true;
                break;
            case "eth":
                if (eth < 2){
                    not.noti(Double.toString(temp),sit);
                    eth++;
                }
                x = true;
                break;
            case "tails":
                if (tails < 2){
                    not.noti(Double.toString(temp),sit);
                    tails++;
                }
                x = true;
                break;
            case "finish":
                if (finish < 2){
                    not.noti(Double.toString(temp),sit);
                    finish++;
                }
                x = true;
                break;
            default:
                break;
        }
        return x;
    }
}
