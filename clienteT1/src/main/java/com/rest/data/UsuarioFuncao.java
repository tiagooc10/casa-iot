package com.rest.data;

public class UsuarioFuncao{

   private int id_usuario;

   private int id_funcao;
  

   public int getId_usuario(){
   	return id_usuario;
   }
   public void setId_usuario(int id_usuario){
	this.id_usuario = id_usuario;
   }
   public int getId_funcao(){
   	return id_funcao;
   }
   public void setId_funcao(int id_funcao){
	this.id_funcao = id_funcao;
   }

   @Override
   public String toString(){
	return "UsuarioFuncao{" + "usuario=" + id_usuario + ", funcao=" + id_funcao + "}";
   }
}
