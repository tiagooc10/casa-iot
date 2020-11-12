package com.rest.data;

public class Requisicao{

   private String comando;

   public String  getComando(){
   	return comando;
   }
   public void setId(String comando){
	this.comando = comando;
   }


   @Override
   public String toString(){
	return "Comando{" + "comando=" + comando + "}";
   }

}


