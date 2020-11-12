package com.rest.test;

import java.util.List;
import java.util.ArrayList;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.*;

import com.rest.test.analiseContexto.*;
import com.rest.data.*;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

//import org.glassfish.jersey.server.ResourceConfig;



@Path("/rest")
public class RestClienteT1SemCaching {

  boolean CACHING=false;

  static int ID_C=0;
  static int ID_S=0;

  public static synchronized int getID_SOLICITACAO(){
        ID_S++;
	return ID_S;
  }	


//Recebe um comando de operacao sobre um sensor, usuário já deve ter a permissão concedida
//formato = id, dispositivo, sensor, operacao
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/comando")
   public Resposta create(Comando r){

       int ID_COMANDO=ID_C;
       String comando = r.getComando();
       Resposta ri = new Resposta();

       try{
	    //Tratando o comando vindo dos dispositivos
	    if(comando.equals("1")){
              //Executar o comando desejado e retornar a resposta para o usuário
	      System.out.println("Executando Operação !");
              ExecutarOpercao ex = new ExecutarOpercao();
              int x = ex.executar(Integer.parseInt(comando));

              int[] valores = new int[2];
              valores[0]=x;
              ri.setValores(valores);

	      return ri;
	    }
	    if(comando.equals("2")){
              //Executar o comando desejado e retornar a resposta para o usuário
	      System.out.println("Executando Operação !");
              ExecutarOpercao ex = new ExecutarOpercao();
              int x = ex.executar(Integer.parseInt(comando));

              int[] valores = new int[2];
              valores[0]=x;
              ri.setValores(valores);

	      return ri;
	    }
	    if(comando.equals("3")){
              //Executar o comando desejado e retornar a resposta para o usuário
	      System.out.println("Executando Operação !");
              ExecutarOpercao ex = new ExecutarOpercao();
              int x = ex.executar(Integer.parseInt(comando));

              int[] valores = new int[2];
              valores[0]=x;
              ri.setValores(valores);

	      return ri;
	    }
	    //Tratando o comando vindo do gateway
	    else{
              //Processar o comando, verificando as políticas para determinar se ele será
              //executado ou não. Deve vir no comando o índice das políticas a serem verificadas, que é único tanto
              //no gateway quando nesses dispositivos
              JSONObject jsonObj = new JSONObject(comando);
              //Busca a regra que deve ser avaliada para poder executar o comando
              String[] idsPoliticasDinamicas = (jsonObj.get("politicaDinamica").toString()).split(" ");
              int dispositivo = Integer.parseInt((jsonObj.get("dispositivo")).toString());
              int operacao = Integer.parseInt((jsonObj.get("comando")).toString());
              int usuario = Integer.parseInt((jsonObj.get("usuario")).toString());
	
	      System.out.println("\n\nCHEGOU O ID COMANDO: " + ID_COMANDO+"\n\n");
	      
	      //Busca as politicas dinamicas no banco de dados local
	      List<String> politicas = new ArrayList<String>();
	      StorageCliente s = new StorageCliente();
	      for(int i=0; i<idsPoliticasDinamicas.length; i++){
	        String aux =s.getPolitica(Integer.parseInt(idsPoliticasDinamicas[i]));
		politicas.add(aux);
	      }

	      //COLOCAR PRIORIDADE DE ACORDO COM O TEMPO QUE LEVA PARA OBTER O DADO, ANALISANDO A DE MENOR TEMPO 

	      int x=0;
	      if(idsPoliticasDinamicas[0].equals("0")){
	        //Executar o comando desejado e retornar a resposta para o usuário
	        System.out.println("Executando Operação !" + "/n/n");
                ExecutarOpercao ex = new ExecutarOpercao();
                x = ex.executar(operacao);
	      }
	      else{
                //Analisa a politica, verificando se retornará PERMITIR
                analisadorPoliticas ap = new analisadorPoliticas();
	        for(int i=0; i<politicas.size(); i++){
		  int aux=0;
		  if(CACHING==true && s.selectCaching(Integer.parseInt(idsPoliticasDinamicas[i]),usuario)) aux=4;
		  else aux=ap.processarPolitica(politicas.get(i),usuario,dispositivo,operacao, ID_COMANDO);
	          if(aux==3 || aux==4){
		    if(aux==3) System.out.println("Politica Dinamica Permitiu a Operacao!");
		    if(aux==4) System.out.println("Politica Dinamica em Sistema de Caching Permitiu a Opercao!");
                    //Executar o comando desejado e retornar a resposta para o usuário
	            System.out.println("Executando Operação !"+ "/n/n");
                    ExecutarOpercao ex = new ExecutarOpercao();
                    x = ex.executar(operacao);

                    int[] valores = new int[2];
                    valores[0]=x;
                    ri.setValores(valores);

		    System.out.println("\n\nCOMANDO " + ID_COMANDO + " EXECUTOU CORRETAMENTE\n\n");

		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		    Date date1 = new Date();
		    Date date2 = new Date();
   		    //String datetime1 = dateFormat.format(date1);
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date2);
		    calendar.add(Calendar.SECOND, 60000);
		    date2=calendar.getTime();
		    //String datetime2 = dateFormat.format(date2);

		    System.out.println("aux: " + aux + " CACHING: " + CACHING);

		    if(aux==3 && CACHING==true){
                      System.out.println("Inserindo Caching para Politica Dinamica " + 
                                          Integer.parseInt(idsPoliticasDinamicas[i]) + 
                                          " no intervalo " + date1 + " " + date2);
		      s.insertCaching(Integer.parseInt(idsPoliticasDinamicas[i]),usuario,date1,date2);
		    }
		    break;
		  }
		}
	      }
	      jsonObj=null;
            }
	    return ri;
      }
      catch(Exception e){}

      return ri;
   }



//Recebe um comando de operacao sobre um sensor, usuário já deve ter a permissão concedida
//formato = id, dispositivo, sensor, operacao
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/politicas")
   public Resposta creat(Comando r){

            int ID_COMANDO = ID_C;
            Resposta ri = new Resposta();
            String id_politica = r.getComando();

      try{
	    System.out.println("Politica Recebida pelo Objeto: " + id_politica);

            JSONObject jsonObj = new JSONObject(id_politica);
            int id = Integer.parseInt((jsonObj.get("id")).toString());
            String politica = jsonObj.get("politica").toString();
	    String politicaAssociada = jsonObj.get("politicaEstaticaAssociada").toString();

	    StorageCliente sc= new StorageCliente();		
  	    sc.insertPolitica(id,politica);
      }
      catch(Exception e){}

      return ri;
   }


   //Método que divide a String S em vários floats
   int[] DividirString(String s){
     int[] resposta = new int[3];
     if(s.length()<=0){
       return resposta;
     }
     String[] a = s.split(" ");
     for(int i=0; i<a.length; i++){
       resposta[i]=Integer.parseInt(a[i]);
     }
     return resposta;
   }
  

//Receber uma lista com todos os dispositivos que esse usuario pode saber que existem no sistema
   @GET
   @Path("/{id}/dispositivos")
   public String getDevices(@PathParam("id") int id){
	String dispositivos="";
	try{
	  Storage s = new Storage();
	  dispositivos=s.getDevices();
	  return dispositivos;
	} 
	catch(Exception e){

	}  
	return dispositivos;
   }


//Receber uma lista com todas as permissões associadas a um dado usuario
   @GET
   @Path("/{id}/permissoes")
   public getPermissoes getPermissoes(@PathParam("id") int id){
	getPermissoes gp = new getPermissoes();
	int[] permissoes;

	try{
	  Storage s = new Storage();
	  String devices;
	  permissoes=s.getPermissoes(id);
	  gp.setPermissoes(permissoes);

	} 
	catch(Exception e){
	}

	return gp;  
   }


    @GET
    @Produces(MediaType.TEXT_PLAIN)//APPLICATION_JSON
    public String RequesAccess(@QueryParam("user") String user) {//Parametro via query	
        return "O usuario " + user + " recebeu o ID: 100";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("users/{id}")
    public String GetID(@PathParam("id") long id) {//Parametro via path
	return "O usuario de ID: " + id + " foi recuperado";
    }


   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("{id}/")
   public Chamado getChamado(@PathParam("id") long id){
	   Chamado c1 = new Chamado();
	   c1.setID(id);
	   c1.setAssunto("Assunto " + id);
	   c1.setMensagem("Mensagem " + id);
	   c1.setStatus(Status.NOVO);
	return c1;
   }

}
