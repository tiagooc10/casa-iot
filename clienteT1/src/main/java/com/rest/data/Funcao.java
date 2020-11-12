package com.rest.data;

public class Funcao{

   private int id_usuario;
	
   private int id_funcao;

   private String d;

   private String sensores;

   private String operacoes;

   private String complemento;
  

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
   public String getSensores(){
	return sensores;
   }
   public void setSensores(String sensores){
	this.sensores = sensores;
   }

   public String getOperacoes(){
	return operacoes;
   }
   public void setOperacoes(String operacoes){
	this.operacoes = operacoes;
   }

   public String getComplemento(){
   	return complemento;
   }
   public void setComplemento(String complemento){
	this.complemento = complemento;
   }

   @Override
   public String toString(){
	return "CriarFuncao{" + "idUsuario=" + id_usuario + ", idFuncao = " + id_funcao + ", dispositivos=" + complemento + ", sensores="
	 + sensores + ", operacao=" + operacoes +  "}";
   }
}
