package com.rest.data;

public class RegraContexto{

   //id do usuario
   private int id;
	
   //tipo da regra
   //tipo==1 -> regra do tipo on/off
   //tipo==2 -> regra do tipo >/</= certo valor
   //tipo==3 -> regra do tipo localizacao do objeto = UFMG, deve ser associada aos dados de algum sensor
   //TIPO==4 -> regra do tipo horario
   private int tipo;

   //id do dispositivo que terá a regra associada a ele
   private int dispositivo1;

   //id do sensor que terá a regra associada a ele
   private int sensor1;

   //id do dispositivo que faz parte da expressão da regra
   private int dispositivo2;

   //id do sensor que faz parte da expressão da regra
   private int sensor2;

   //comparador que será necessário para regras do tipo 2
   private String comp;

   //valor, que será 0 ou 1 para regras do tipo 1, algum outro valor caso seja >= ou <=, ou o nome de alguma localização
   private String valor;

   //usuarios para os quais a regra será verificada
   //podem também ser identificados por algum atributo
   private String usuarios;

   public int  getId(){
   	return id;
   }
   public void setId(int id){
	this.id = id;
   }
   public int  getTipo(){
   	return tipo;
   }
   public void setTipo(int tipo){
	this.tipo = tipo;
   }
   public int getDispositivo1(){
   	return dispositivo1;
   }
   public void setDispositivo1(int dispositivo1){
   	this.dispositivo1=dispositivo1;
   }
   public int getSensor1(){
	return sensor1;
   }
   public void setSensor1(int sensor1){
	this.sensor1 = sensor1;
   }
   public int getDispositivo2(){
   	return dispositivo2;
   }
   public void setDispositivo2(int dispositivo2){
   	this.dispositivo2=dispositivo2;
   }
   public int getSensor2(){
	return sensor2;
   }
   public void setSensor2(int sensor2){
	this.sensor2 = sensor2;
   }
   public String getComp(){
	return comp;
   }
   public void setComp(String comp){
	this.comp = comp;
   }
   public String  getValor(){
   	return valor;
   }
   public void setValor(String valor){
	this.valor = valor;
   }
   public String getUsuarios(){
        return usuarios;
   }
   public void setUsuarios(String usuarios){
        this.usuarios=usuarios;
   }

   @Override
   public String toString(){
	return "";
   }

}


