package com.rest.data;

public class concederPermissoes{

   //id do usuario que concederá a permissão
   private int id1;

   private int id2;

   private String permissoes;

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
   public String getPermissoes(){
   	return permissoes;
   }
   public void setPermissoes(String permissoes){
	this.permissoes = permissoes;
   }

   @Override
   public String toString(){
	return "concederPermissoes{" +  "}";
   }

}


