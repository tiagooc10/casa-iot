package com.rest.data;

public class PermissoesGrupo{

   //id do usuario que concederá a permissão
   private int id;

   private int[] grupo;

   private int[] permissoes;

   //informacoes do contexto


   public int getId(){
   	return id;
   }
   public void setId(int id){
	this.id = id;
   }
   public int[] getGrupo(){
   	return grupo;
   }
   public void setGrupo(int[] grupo){
	this.grupo = grupo;
   }
   public int[] getPermissoes(){
   	return permissoes;
   }
   public void setPermissoes(int[] permissoes){
	this.permissoes = permissoes;
   }

   @Override
   public String toString(){
	return "concederPermissoes{" +  "}";
   }

}


