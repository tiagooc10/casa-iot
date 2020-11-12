package com.rest.data;

public class SolicitacaoExclusiva{

   private int id;

   private int dispositivo;

   private int sensor;

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

   @Override
   public String toString(){
	return "SolicitacaoExclusiva{" + "id=" + id+ ", dispositivo=" + dispositivo + ", sensor=" + sensor + "}";
   }

}


