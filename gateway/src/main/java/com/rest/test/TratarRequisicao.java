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
import java.lang.*;

import com.rest.test.drivers.*;
  

public class TratarRequisicao{
    volatile boolean flag = true;
    int id_req=0;

    String idComando="";

    Storage s = new Storage();
	
    public Resposta Tratar(int dispositivo, int sensor, int operacao, String comp){
	System.out.println("Iniciou o tratamento do Comando");
	System.out.println("Complemento antes de dividir: " + comp);

	float[] complemento = DividirString(comp);

	System.out.print("Complemento: ");
	for(int i=0; i<5; i++){
	  System.out.print(complemento[i] + " ");
	}
	System.out.println();
	
	//Trata para onde o Requisicao deve ser enviado
        try{
	    if(dispositivo==1) return Wemo(sensor,operacao);
	    else if(dispositivo==2) return PhillipsHue(sensor,operacao,complemento);
	    else if(dispositivo==3) return SensorIris(sensor,operacao);
	    else if(dispositivo==4) return SensorRFID(sensor,operacao);
	    else if(dispositivo==5) return SensorPresenca(sensor,operacao);
	    else if(dispositivo==6) return SensorArduino(sensor,operacao);
	    else if(dispositivo==7) return ArCondicionado(sensor,operacao, complemento);
	    else if(dispositivo==8) return playerMusica(dispositivo, sensor,operacao,complemento);
	    else if(dispositivo==9) return multiSensorSmartThings(dispositivo, sensor, operacao);
	    else if(dispositivo==20) return Persiana(sensor,operacao);
	    else if(dispositivo==22) return Lampadas(sensor,operacao);
            else if(dispositivo>=11 && dispositivo<=18) return dispositivosTeste(dispositivo, sensor,operacao,complemento);
        }catch(Exception e){
        }  
	Resposta ri = new Resposta();
	String[] intArray = {"-6"};
	ri.setValores(intArray);
	return ri;       
    }

     public void setIdComando(String idComando){
         this.idComando=idComando;
     }
     
     public String getIdComando(){
         return idComando;
     }

    //Método que divide a String S em vários floats
    float[] DividirString(String s){
      float[] resposta = new float[10];
      if(s.length()<=0){
        return resposta;
      }
      String[] a = s.split(" ");
      for(int i=0; i<a.length; i++){
        resposta[i]=Float.parseFloat(a[i]);
      }
      return resposta;
    }
  

    public Resposta Wemo(int sensor, int operacao) throws Exception{
      System.out.println("Dispositivo Wemo");

      //Verifica qual o wemo associado a usuario ou aplicacao que esta requisitando
      //Buscar o ip do wemo associado a este usuario, nesse momento ja está certo que ele pode alterar o dispositivo
      ControllerSwitchWemo cw = new ControllerSwitchWemo();

      //Respostas
      Resposta ri = new Resposta();
      String[] valores = new String[10];

      //ligar a tomada wemo
      if(operacao==1){
        //cw.SwitchOn();
	      s.insertData(17,"1");
      }
      //desligar a tomada wemo
      else if(operacao==2){
        //cw.SwitchOff();
        s.insertData(17,"0");
      }
     //Ler os dados de consumo do wemo
      else if(operacao==3){
        //valores[0] = cw.ReadConsumptionStatus();
        //ri.setValores(valores);
        s.insertData(18,"125");
      }
      //Ler o status do wemo
      else if(operacao==4){
	      String ip="";
        boolean aux = cw.ReadStatus(ip);
	      if(aux)
	        valores[0]="1";
	      else
	        valores[0]="0";
	        ri.setValores(valores);
      }
	
      return ri;       
    }

    public Resposta PhillipsHue(int sensor, int operacao, float[] complemento) throws Exception{
      System.out.println("Comando para Phillips Hue");
      float x=0,y=0;
      int intensidade=0; 

      //Verfica qual bridge esta associada a este usuario
      ManIoTHueDriver mh = new ManIoTHueDriver();
     
      //Mudar de On pra Off, ou o contrario, a lampada de indice "sensor"
      if(operacao==1){
         mh.changeOnOff(sensor, true);
      }
      //Alterar a intensidade da luz de uma determinada lampada
      if(operacao==2){
	intensidade=(int)complemento[0];
        mh.changeBrightness(sensor, intensidade);
      }
      //Alterar a cor de uma dada lampada
      if(operacao==3){
        x=complemento[0]; y=complemento[1];
        mh.setColor(sensor, x, y);
      }
      //Obter as propriedades de uma lampada
      if(operacao==4){
        String r = mh.getLampProperties(sensor);
      }
      //Obter as propriedades de todas as lampadas
      if(operacao==5){
        String r = mh.getAllLampProperties();
      }

      Resposta ri = new Resposta();
      return ri;       
    }

    public Resposta SensorIris(int sensor, int operacao) throws Exception{
      System.out.println("Comando para Iris");
      String[] valores = new String[sensor];

      Resposta ri = new Resposta();
      Storage s = new Storage();

      //Realizar leitura de luminosidade de um sensor especifico
      if(operacao==1){
        int aux = s.selectData(sensor);
	      valores[0]=new Integer(aux).toString();
      }
      //Realizar leitura de temperatura de um sensor especifico
      else if(operacao==2){
        int aux = s.selectData(sensor);
      }
      //Realizar leitura de luminosidade de um grupo de sensores
      else if(operacao==3){
        int aux = s.selectData(sensor);
      }
      //Realizar leitura de temperatura de um grupo de sensores
      else if(operacao==4){
        int aux = s.selectData(sensor);
      }
      //Realizar leitura de luminosidade de todos os sensores
      else if(operacao==5){
        for(int i=1; i<=sensor; i++){
          int aux=-1;
          try{
            aux=s.selectData(i);
	          valores[i-1]=new Integer(aux).toString();
          }
          catch(Exception e){
          }
        }

	    for(int i=1; i<=sensor; i++){
          System.out.println("L"+i+": " + valores[i-1]);
        }
      }
      ri.setValores(valores);
      return ri;
    }


    //Adicionar jar:  mvn install:install-file -Dfile=AlienRFID.jar -DgroupId=maniot -DartifactId=maniot -Dversion=1 -Dpackaging=jar
    public Resposta SensorRFID(int sensor, int operacao) throws Exception{
/*    System.out.println("Comando para RFID");
      //Tag tagList[];
      String idTag="";

      //Driver RFID, responsavel por fazer a leitura das tags
      ReaderRFID rf = new ReaderRFID();

      //Verifica quais tags de uma dada lista estão no ambiente
      if(operacao==1){
        rf.getTags();
      }
      //Identify if a tag was or was not readed in a certain time interval
      if(operacao==2){
        rf.isTagPresent(idTag);
      }       
*/
      Resposta ri = new Resposta();
      return ri;
    }



    public Resposta SensorPresenca(int sensor,int operacao) throws Exception{
      System.out.println("Comando para Sensor de Presenca");
      //Requisistar uma lista com pessoas no ambiente
      if(operacao==1){

      }
  
	    Resposta ri = new Resposta();
	    return ri;       
    }

    public Resposta SensorArduino(int sensor, int operacao) throws Exception{
      System.out.println("Comando para Arduino");
      //Fazer a leitura dos sensores do arduino 
      if(operacao==1){
        //String[] cmd = {"/bin/bash", "-c", "echo password | python script.py '" + packet.toString() + "'"};
        //Runtime.getRuntime().exec(cmd);
      }
	    Resposta ri = new Resposta();
	    return ri;       
    }

    public Resposta ArCondicionado(int sensor, int operacao, float[] complemento) throws Exception{
      System.out.println("Comando para o Ar Condicionado");
      int temperatura=0;
      AirCond a = new AirCond();

      //Ligar o ar condicionar 
      if(operacao==1){
	      a.setOn(sensor);
      }
      //Desligar o ar condicionado
      if(operacao==2){
	      a.setOff(sensor);
      }
      //Alterar a Temperatura
      if(operacao==2){
	      temperatura=(int)complemento[0];
	      a.setTemperatura(sensor,temperatura);
      }

      //Retornar 10 é um sinal de que tudo ocorreu corretamente
      Resposta ri = new Resposta();
      return ri;       
    }

    public Resposta playerMusica(int dispositivo, int sensor, int operacao, float[] complemento) throws Exception{
      System.out.println("Comando para Player de Musica");
    
      int porta;
      if(dispositivo==10) porta=8080;
      if(dispositivo==11) porta=8081;
      if(dispositivo==12) porta=8082;
      if(dispositivo==13) porta=8083;
      if(dispositivo==14) porta=8084;


      int temperatura=0;
      playerMusica p = new playerMusica();

      //Dar play na execução
      if(operacao==1){
	      p.play();
      }
      //Dar stop na execução 
      if(operacao==2){
	      p.stop();
      }
      //Passar para a próxima música
      if(operacao==3){
	      p.next();
      }
      //Passar para a música anterior
      if(operacao==4){
	      p.prev();
      }

      Resposta ri = new Resposta();
      return ri;       
    }

    public Resposta Persiana(int sensor, int operacao) throws Exception{
      System.out.println("Comando para a Persiana");

      Resposta ri = new Resposta();

      //Driver que controla a persiana
      ShadeSomaDriver sc = new ShadeSomaDriver();

      //Abrir a persiana
      if(operacao==1){
        sc.openShade();
      }
      //Fechar a persiana
      if(operacao==2){
        sc.closeShade();
      }

      if(operacao==3){
        String status = sc.getStatus();
        String[] valores = new String[2];
        valores[0] = status;
        ri.setValores(valores);
      }

      //Obter a bateria
      if(operacao==4){
        int b = sc.getShadeBattery();
	      String[] valores = new String[2];
	      valores[0] = new Integer(b).toString();
	      ri.setValores(valores);
      }

      return ri;       
    }


    public Resposta Lampadas(int sensor, int operacao) throws Exception{
      System.out.println("Comando para as Lampadas");

      Resposta ri = new Resposta();

      String ip = "192.168.88.10";

      //Driver que controla a persiana
      SwitchWinetDriver sc = new SwitchWinetDriver(ip);

      //Abrir a persiana
      if(operacao==1){
        sc.turnOn();
      }
      //Fechar a persiana
      if(operacao==2){
        sc.turnOff();
      }

      if(operacao==3){
        int r = sc.getStatus();
        String status = "";
        if(r==1)
          status="ligadas";
        if(r==2)
          status="fechadas";
        String[] valores = new String[2];
        valores[0] = status;
        ri.setValores(valores);
      }

      return ri;       
    }

    public Resposta dispositivosTeste(int dispositivo, int sensor, int operacao, float[] complemento) throws Exception{
      System.out.println("Comando para o Dispositivo Teste " +  dispositivo);
      dispositivosTeste p = new dispositivosTeste(dispositivo, idComando);

      Resposta ri = new Resposta();

      //Ligar
      if(operacao==1){
	      p.ligar();
      }
      //Desligar
      if(operacao==2){
	      p.desligar();
      }
      //get Status
      if(operacao==3){
	      int status = p.getStatus();
	      String[] valores = new String[2];
	      valores[0]=new Integer(status).toString();
	      ri.setValores(valores);
      }

      System.out.println("Retornando Resposta: " + ri.toString());
      return ri;       
    }

    public Resposta multiSensorSmartThings(int dispositivo, int sensor, int operacao) throws Exception{
      System.out.println("Comando para o Sensor SmartThings");
      multiSensorSmartThings mt = new multiSensorSmartThings();
      Resposta ri = new Resposta();

      //obter a temperatura
      if(operacao==1){
	      double d = mt.getTemperatura();
      	String[] valores=new String[2];
        int aux_int = (int)d;
      	valores[0]=new Integer(aux_int).toString();
      	ri.setValores(valores);
      }
      System.out.println("Retornando Resposta: " + ri.toString());
      return ri;       
    }

}
