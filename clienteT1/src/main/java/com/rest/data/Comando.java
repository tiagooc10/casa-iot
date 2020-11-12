package com.rest.data;

public class Comando{

   private String comando;


   public String getComando(){
   	return comando;
   }
   public void setComando(String comando){
	this.comando = comando;
   }

   @Override
   public String toString(){
	return "Comando = {"+comando+"}";
   }

}


