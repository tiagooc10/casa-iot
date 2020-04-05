
package com.rest.test;

import java.io.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;
import java.util.Properties;
import java.util.Date;

// REST
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.client.Entity;
//import javax.ws.rs.client;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
//import org.json.JSONReader;
//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonReader;

public class SwitchWinetDriver {
    JSONObject jsonObject;

    String properties = "";
 
    public SwitchWinetDriver(String ip) {
        properties = ip;
    }

    // action applied=1, command dont sent=0, no server connection=-2, no change cos is already in the status=-1
    public int turnOn() {
        /*int status = getStatus();
        /if (status == 1){
            System.out.println("SWITCH WINET       :  No change. The switch is already ON.");
            return -1;
        }
        else if (status == -2){
            System.out.println("SWITCH WINET       :  Problem with turning on the switch.");
            return -2;
        }
        else if (status == 0){*/
            String command = "{\"light_status\":\"on\"}";
            String link="http://"+properties+"/commands";
            Client client = ClientBuilder.newClient();
            try{
                String result = client.target(link).request(MediaType.APPLICATION_JSON).post(Entity.json(command), String.class);
                if (result.equals("Command received.")){
                    System.out.println("SWITCH WINET       :  Switch turned ON successfully.");
                    return 1;
                }
                else{
                    System.out.println("SWITCH WINET       :  Problem with turning OFF the switch.");
                    return 0;
                }
            }
            catch(Exception ex){
                return -2;
            }
        //}
        //return 1;
    }

    // action applied=1, command dont sent=0, no server connection=-2
    public int turnOff() {
        /*int status = getStatus();
        if (status == 1){
            System.out.println("SWITCH WINET       :  No change. The switch is already OFF.");
            return -1;
        }
        else if (status == -2){
            System.out.println("SWITCH WINET       :  Problem with turning off the switch.");
            return -2;
        }
        else if (status == 0){*/
            String command = "{\"light_status\":\"off\"}";
            String link="http://"+properties+"/commands";
            Client client = ClientBuilder.newClient();
            try{
                String result = client.target(link).request(MediaType.APPLICATION_JSON).post(Entity.json(command), String.class);
                if (result.equals("Command received.")){
                    System.out.println("SWITCH WINET       :  Switch turned OFF successfully.");
                    return 1;
                }
                else{
                    System.out.println("SWITCH WINET       :  Problem with turning OFF the switch.");
                    return 0;
                }
            }
            catch(Exception ex){
                return -2;
            }
        //}
        //return 1;
    }

    // turnOn = 1, turnOff=0, error=-1
    public int getStatus() {
        //String command = "{\"command\":\"a\"}";
        String link="http://"+properties+"/values";
        Client client = ClientBuilder.newClient();
        try{
            String jsonx = client.target(link).request(MediaType.APPLICATION_JSON).get(String.class);
            jsonObject = new JSONObject(jsonx);
            String strStatus = jsonObject.getString("light_status");
            String s = jsonObject.getString("light_status");
            System.out.println("LIGTH STATUS-------------------: " + s);
            if (s.equals("on"))
               return 1;
            else
               return 0;
        }
        catch(Exception ex){
            return -2;
        }
    }
}
