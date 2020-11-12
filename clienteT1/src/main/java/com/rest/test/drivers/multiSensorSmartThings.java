package com.rest.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import org.json.*;



public class multiSensorSmartThings{

    public double getTemperatura(){	
	System.out.println("Metodo GetTemperatura");

	try{
            String s = "https://graph-na04-useast2.api.smartthings.com/api/smartapps/installations/975ecb91-ffe9-4c40-aba5-60925f42ba89/temperatura";
            URL url = new URL(s);
            URLConnection uc = url.openConnection();

            uc.setRequestProperty("X-Requested-With", "Curl");
   
            String aut = "Bearer 7ad0247f-24a1-48d4-9d45-c489f58d02df";
            uc.setRequestProperty("Authorization", aut);

            BufferedReader reader=new BufferedReader( new InputStreamReader(uc.getInputStream()));
            String resposta = reader.readLine();
	    System.out.println("Resposta: " + resposta);
		
	    String[] campos = resposta.split(",");
	    System.out.println(campos[1]);
	    if(campos.length>1){
		System.out.println(campos[1]);
		String ss = campos[1].substring(campos[1].indexOf(":") + 1);
		ss = ss.substring(0, ss.indexOf("}"));
		System.out.println("Retornando " + Double.parseDouble(ss));
		return Double.parseDouble(ss);
	    }    
	    
	}
	catch(Exception e){}
	return 100;
    }
}
