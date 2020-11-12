package com.rest.data;

public class Politica{

   private String politica;


   public String getPolitica(){
   	return politica;
   }
   public void setPolitica(String politica){
	this.politica=politica;
   }

   @Override
   public String toString(){
	return "Politica{" + "politica=" + politica + "}";
   }
}
