package com.rest.data;

public class RespostaRequisicao{

   private int id, valor;


   public int  getID(){
   	return id;
   }
   public void setID(int id){
	this.id = id;
   }
   public int getValor(){
   	return valor;
   }
   public void setValor(int valor){
	this.valor = valor;
   }

   @Override
   public String toString(){
	return "RespostaRequisicao{" + "id=" + id + ", valor=" + valor + "}";
   }

}


