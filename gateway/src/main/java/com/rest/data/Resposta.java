package com.rest.data;

public class Resposta{

   private double[] valores;


   public double[] getValores(){
   	return valores;
   }
   public void setValores(double[] valores){
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


