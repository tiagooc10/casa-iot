package com.rest.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
import com.rest.data.Requisicao;
import java.util.concurrent.TimeUnit;

/**
 *
 * Controla o a todos os drivers, recebendo requisicoes das aplicações e as realizando
 * nos dispositivos.
 * 
 *
 */

public class Drivers{
    
    public int usuario;     //ID do usuario ou aplicacao que esta querendo realizar o operacao no dispositivo
    public int operacao;     //ID do operacao que se deseja realizar sobre o sensor ou atuador de um dispositivo
    public int dispositivo; //1=Wemo, 2=Lampada Phillips, 3=Sensor Iris, 4 = RFID, 5 = Blutooth, 6 = Sensor Arduino
    public int sensor;      //Numero do sensor ou atuador com o qual se deseja interagir. Inicia no 1 e vai ate o numero disponivel
 
    String complemento;

    public Drivers(){
        try{

	  this.sensor=sensor;
	  this.operacao=operacao;


	  //Driver que retorna quem esta presenta
	  //PresenceModule pr = new PresenceModule("192.168.1.102");

	  //Iris ir=new Iris();
          //try{
          //  TimeUnit.SECONDS.sleep(10);
          //}
	  //catch(Exception e){}

          //Oscilloscope me = new Oscilloscope();
          //me.run();

	  //Inicializar o Driver de Presenca

	  //ReaderRFID rfid = new ReaderRFID();
		
	  //Thread t1 = new Thread(rfid);
	  //t1.start();
/*
	  TratarRequisicao t=new TratarRequisicao();
          t.start();
*/
         
        }catch (Exception e){

        }
    }
}


