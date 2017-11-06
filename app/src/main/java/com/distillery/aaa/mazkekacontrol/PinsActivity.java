package com.distillery.aaa.mazkekacontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;

public class PinsActivity extends AppCompatActivity {

    UDPServer client;
    EditText hotplate;
    EditText solonoid1;
    EditText solonoid2;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pins);
        hotplate = (EditText) findViewById(R.id.hotplate);
        solonoid1 = (EditText) findViewById(R.id.solonoid1);
        solonoid2 = (EditText) findViewById(R.id.solonoid2);
        save = (Button) findViewById(R.id.SAVE);
        client = new UDPServer(new UDPServer.onReceive() {
            @Override
            public void onReceive(DatagramPacket data) {
                recv(data);
            }
        });
        try{
            JSONObject obj = new JSONObject();
            obj.put("msg","getpins");
            client.send(obj.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPins();
                client.stop();
                finish();
            }
        });
    }

    private void recv(DatagramPacket data){
        String message = new String(data.getData());
        try{
            JSONObject obj = new JSONObject(message);
            int solonoid1Pin = obj.getInt("solonoid1");
            int solonoid2Pin = obj.getInt("solonoid2");
            int hotplatePin = obj.getInt("plate");
            hotplate.setText(Integer.toString(hotplatePin));
            solonoid1.setText(Integer.toString(solonoid1Pin));
            solonoid2.setText(Integer.toString(solonoid2Pin));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void sendPins(){
        try{
            JSONObject obj = new JSONObject();
            obj.put("msg","setpins");
            obj.put("solonoid1",Integer.valueOf(solonoid1.getText().toString()));
            obj.put("solonoid2",Integer.valueOf(solonoid2.getText().toString()));
            obj.put("plate",Integer.valueOf(hotplate.getText().toString()));
            client.send(obj.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
