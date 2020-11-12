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

import com.rest.data.*;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

//import org.glassfish.jersey.server.ResourceConfig;



@Path("/rest")
public class RestClienteT0SemCaching {

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
