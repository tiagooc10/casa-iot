package com.rest.data;

public class getDispositivos{

   private int[] permissoes;


   public int[] getPermissoes(){
   	return permissoes;
   }
   public void setPermissoes(int[] permissoes){
	this.permissoes = permissoes;
   }

   @Override
   public String toString(){
	return "getPermissoes do Id{" + "id=" + "}";
   }

}


