package com.distillery.aaa.mazkekacontrol;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    Thread thread;
    Button btn;
    TextView txt;
    ImageView connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        not = new Notifications(mNotificationManager, getApplicationContext());
        server = new UDPServer(new UDPServer.onReceive() {
            @Override
            public void onReceive(DatagramPacket data) {
                Log.e("msg","RCV");
                JSONObject json = new JSONObject();
                String sit = "";
                changeSIT();
                try{
                    json = new JSONObject(new String(data.getData()));
                    if (json.getString("msg").equals("temp")) {
                        try {
                            temp = json.getDouble("temp");
                            temp = Double.parseDouble(String.format("%.3f", temp));
                            sit = json.getString("sit");
                            if (!manageNotifications(sit)) {
                                not.unoti(Double.toString(temp), sit);
                            }
                        } catch (JSONException e) {
                            if (!sit.isEmpty() && temp > 0) {
                                not.unoti(Double.toString(temp), "NULL");
                            }
                            e.printStackTrace();
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
        connected = (ImageView) findViewById(R.id.imageView);
        txt = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    server.send(new JSONObject().put("msg", "hi").toString());
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
                }else{
                    not.unoti(Double.toString(temp),sit);
                }
                x = true;
                break;
            case "eth":
                if (eth < 2){
                    not.noti(Double.toString(temp),sit);
                    eth++;
                }else{
                    not.unoti(Double.toString(temp),sit);
                }
                x = true;
                break;
            case "tails":
                if (tails < 2){
                    not.noti(Double.toString(temp),sit);
                    tails++;
                }else{
                    not.unoti(Double.toString(temp),sit);
                }
                x = true;
                break;
            case "finish":
                if (finish < 2){
                    not.noti(Double.toString(temp),sit);
                    finish++;
                }else{
                    not.unoti(Double.toString(temp),sit);
                }
                x = true;
                break;
            default:
                break;
        }
        return x;
    }
    private void changeSIT(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connected.setImageResource(R.drawable.presence_online);
            }
        });
    }
}
