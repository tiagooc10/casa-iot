package com.rest.data;

public class delegarAtributo{

   //id do usuario que concederá a permissão
   private int id1;

   //id do usuario que receberá a permissão
   private int id2;

   private String atributo;

   //informacoes do contexto


   public int getId1(){
   	return id1;
   }
   public void setId1(int id1){
	this.id1 = id1;
   }
   public int getId2(){
   	return id2;
   }
   public void setId2(int id2){
	this.id2 = id2;
   }
   public String getAtributo(){
   	return atributo;
   }
   public void setAtributo(String atributo){
	this.atributo = atributo;
   }

   @Override
   public String toString(){
	return "concederPermissoes{" +  "}";
   }

}


