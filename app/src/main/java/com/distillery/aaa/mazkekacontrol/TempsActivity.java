package com.distillery.aaa.mazkekacontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;

public class TempsActivity extends AppCompatActivity {

    UDPServer server;
    Button save;
    EditText ethIN;
    EditText methIN;
    EditText tailsIN;
    EditText finishIN;

    double meth;
    double eth;
    double tails;
    double finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temps);

        ethIN = (EditText) findViewById(R.id.methanol);
        methIN = (EditText) findViewById(R.id.ethanol);
        tailsIN = (EditText) findViewById(R.id.tails);
        finishIN = (EditText) findViewById(R.id.finish);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                meth = Double.valueOf(methIN.getText().toString());
                eth = Double.valueOf(ethIN.getText().toString());
                tails = Double.valueOf(tailsIN.getText().toString());
                finish = Double.valueOf(finishIN.getText().toString());

                try {

                    JSONObject obj = new JSONObject();
                    obj.put("msg","set");
                    obj.put("meth",meth);
                    obj.put("eth",eth);
                    obj.put("tails",tails);
                    obj.put("finish",finish);
                    server.send(obj.toString());
                    obj = new JSONObject();
                    obj.put("msg", "bye");
                    finish();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        server = new UDPServer(new UDPServer.onReceive() {
            @Override
            public void onReceive(DatagramPacket data) {

                String msg = new String(data.getData());

                try{

                    JSONObject json = new JSONObject(msg);
                    meth = json.getDouble("meth");
                    eth = json.getDouble("eth");
                    tails = json.getDouble("tails");
                    finish = json.getDouble("finish");

                    methIN.setText(String.valueOf(meth));
                    ethIN.setText(String.valueOf(eth));
                    tailsIN.setText(String.valueOf(tails));
                    finishIN.setText(String.valueOf(finish));

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
        try {
            JSONObject obj = new JSONObject();
            obj.put("msg","get");
            server.send(obj.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
