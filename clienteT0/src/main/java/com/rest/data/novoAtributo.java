package com.rest.data;

public class novoAtributo{

   //0 significa que é atributo dos usuários, outros demais valores sao identificadores dos dispositivos
   private int entidade;
	
   //tipo1 = dinamico, tipo2=estatico
   private int tipo;
  
   //nome do atributo
   private String nome;

   private String pai;

   public int getEntidade(){
   	return entidade;
   }
   public void setEntidade(int entidade){
	this.entidade = entidade;
   }
   public int getTipo(){
   	return tipo;
   }
   public void setTipo(int tipo){
	this.tipo = tipo;
   }
   public String getNome(){
	return nome;
   }
   public void setNome(String nome){
	this.nome = nome;
   }
   public String getPai(){
	return nome;
   }
   public void setpai(String nome){
	this.nome = nome;
   }

   @Override
   public String toString(){
	return "novoAtributo{" + "entidade=" + entidade + ", tipo = " + tipo + ", nome=" + nome + ", pai=" + pai + "}";
   }
}
