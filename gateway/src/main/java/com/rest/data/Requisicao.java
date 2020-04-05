package com.rest.data;

public class Requisicao{

   private int id;

   private int dispositivo;

   private int sensor;
 
   private int operacao;

   private String complemento;


   public int  getId(){
   	return id;
   }
   public void setId(int id){
	this.id = id;
   }
   public int  getDispositivo(){
   	return dispositivo;
   }
   public void setDispositivo(int dispositivo){
	this.dispositivo = dispositivo;
   }
   public int getSensor(){
   	return sensor;
   }
   public void setSensor(int sensor){
	this.sensor = sensor;
   }
   public int getOperacao(){
	return operacao;
   }
   public void setOperacao(int operacao){
	this.operacao = operacao;
   }
   public String getComplemento(){
   	return complemento;
   }
   public void setComplemento(String complemento){
	this.complemento = complemento;
   }

   @Override
   public String toString(){
	return "Comando{" + "id=" + id + ", dispositivo=" + dispositivo + ", sensor=" + sensor + ", operacao=" + operacao + "}";
   }

}


