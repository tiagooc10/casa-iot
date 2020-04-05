package com.rest.test;

public class Resposta{

   private String[] valores;


   public String[] getValores(){
   	return valores;
   }
   public void setValores(String[] valores){
	this.valores = valores;
   }

   @Override
   public String toString(){
	String s="Resposta{Valores= ";
	for(int i=0; i<valores.length; i++){
	  s+=valores[i]; s+=" ";
	}
	s+="}";
	return s;
   }

}


