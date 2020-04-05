package com.rest.test;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit ;

import java.util.*;

import org.json.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.io.DataOutputStream;
import java.io.IOException;


public class EnviarComandoDispositivo{ 

  public Resposta enviarComandoDispositivo(int idComando, int usuario, int dispositivo, int sensor, int comando, int politicaDinamica) {

	Storage s = new Storage();
	Resposta ri = new Resposta();

	JSONObject json = new JSONObject();
	json.put("usuario", usuario);
	json.put("dispositivo", dispositivo);
	json.put("sensor", sensor);
	json.put("comando", comando);
	json.put("politicaDinamica", politicaDinamica);

	String mensagem = json.toString();

        String mensagem2="";
        for(int i=0; i<mensagem.length(); i++){
         if(mensagem.charAt(i)=='"'){
           mensagem2+="\\"; mensagem2+=mensagem.charAt(i);
         }
         else{
           mensagem2+=mensagem.charAt(i);
         }
        }

        mensagem2 = "{\"comando\":\""+mensagem2+"\"}";

	System.out.println("MENSAGEM A SER ENVIADA: " + mensagem2);


	Ip ii = new Ip();	
	String ip = ii.getIpDispositivo(dispositivo);		
        

	try {
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
            os.writeBytes(mensagem2);
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

            String rs = resposta.toString();

            System.out.println("RESPOSTA = " + rs);

            JSONObject obj = new JSONObject(rs);
            JSONArray arr = obj.getJSONArray("valores");
       
	          String[] valores = new String[2];
	          valores[0]=arr.getString(0);
            valores[1]=arr.getString(1);
	          ri.setValores(valores);
	           return ri;

          } catch (MalformedURLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } finally{
             if (connectManiot != null) {
                connectManiot.disconnect();
              }
	  }
	    

	} catch (Exception e) {
		e.printStackTrace();
	}
	return ri;
    }
}

