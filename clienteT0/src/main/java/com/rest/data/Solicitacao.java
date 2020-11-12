package com.rest.data;

public class Solicitacao{

   private int id;

   private int dispositivo;

   private int sensor;

   private int operacao;

   //informacoes do contexto


   public int getId(){
   	return id;
   }
   public void setId(int id){
	this.id = id;
   }
   public int getDispositivo(){
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


   @Override
   public String toString(){
	return "Solicitacao{" + "id=" + id+ ", dispositivo=" + dispositivo + ", sensor=" + sensor + "operacao" + operacao + "}";
   }

}


