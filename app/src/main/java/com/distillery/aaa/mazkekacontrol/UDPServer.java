package com.distillery.aaa.mazkekacontrol;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by AvivLaptop on 11/10/2017.
 */

public class UDPServer {
    public DatagramSocket socket;
    private onReceive listener;
    private Thread listen;
    public static String SERVER = "10.0.0.63";
    public static int PORT = 5657;
    private boolean sit = true;

    public UDPServer(onReceive udpCallback){
        this.listener = udpCallback;
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            listen();
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    interface onReceive{
        void onReceive(DatagramPacket data);
    }

    public void listen(){
        sit = true;
        listen = new Thread("listen"){
            public void run() {
                while (sit) {
                    byte[] buff = new byte[1024];
                    DatagramPacket data = new DatagramPacket(buff, buff.length);
                    try {
                        socket.receive(data);
                        listener.onReceive(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        listen.start();
    }

    public void send(DatagramPacket packet){
        try {
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void send(String msg, String IP, int port){
        try {
            byte[] buff = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            packet.setAddress(InetAddress.getByName(IP));
            packet.setPort(port);
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void send(String msg){
        try {
            byte[] buff = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            packet.setAddress(InetAddress.getByName(SERVER));
            packet.setPort(PORT);
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stop(){
        sit = false;
        socket.close();
        socket = null;
    }
}
