package com.rest.test.drivers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.rest.test.Storage;
import com.rest.test.Ip;

import java.net.InetSocketAddress;
import java.net.URI;

import java.util.concurrent.TimeUnit ;

import org.json.JSONObject;
import org.json.JSONArray;


public class dispositivosTeste{

    String ip="";
    int porta=8080;

    int dispositivo=0;

    String idComando="0";

    public dispositivosTeste(int dispositivo, String idComando){
        Ip ii = new Ip();
	ip=ii.getIpDispositivo(dispositivo);
	this.idComando=idComando;

	this.dispositivo=dispositivo;

	if(dispositivo%2==0){
	  porta=5683;
	}
	else{
	  porta=5683;
	}
	
	System.out.println("PORTA: " + porta + " DISPOSITIVO: " + dispositivo);
    }

    public String enviaComando(String s) {

	HttpURLConnection connectManiot=null;

        try {
	    //int ID=getID_TESTE();
	    System.setProperty("http.keepAlive", "false");
            //System.out.println("\nVai enviar um comando para o maniot\n");
            URL url = new URL("http://"+ip+":8080/rest/comando");
            connectManiot = (HttpURLConnection) url.openConnection();
            connectManiot.setDoInput(true);
            connectManiot.setDoOutput(true);
            connectManiot.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connectManiot.setRequestProperty("Accept", "application/json");
            connectManiot.setRequestMethod("POST");
            connectManiot.connect();

	    connectManiot.setReadTimeout(500000);

            DataOutputStream os = new DataOutputStream(connectManiot.getOutputStream());
            os.writeBytes(s);
            os.flush();
            os.close();

            //System.out.println("String enviada: " + s);
            int responseCode = connectManiot.getResponseCode();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connectManiot.getInputStream(), "utf-8"));
            StringBuilder resposta = new StringBuilder();
            String line = null;
            while ((line = rd.readLine()) != null) {
                resposta.append(line + "\n");
            }
            rd.close();

            return resposta.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (connectManiot != null) {
                connectManiot.disconnect();
            }
	}
        return "";
    }

    public void ligar(){
        try {
	   String r = enviaComando("{\"comando\":\""+"1"+"\"}");
            JSONObject json = new JSONObject(r);
            System.out.println(json.toString());
        }
	catch (Exception e){
            
        }
    }

    public void desligar(){
        try {
	    String r = enviaComando("{\"comando\":\""+"2"+"\"}");
            JSONObject json = new JSONObject(r);
            System.out.println(json.toString());
        }
	catch (Exception e){
            
        }
    }

   public int getStatus(){
        try {
	    String resposta = enviaComando("{\"comando\":\""+"3"+"\"}");
            JSONObject obj = new JSONObject(resposta);
            JSONArray arr = obj.getJSONArray("valores");
            int status = (int)arr.getDouble(0);
            return status;
        }
	catch (Exception e){
            
        }
        return -1;
    }
}
