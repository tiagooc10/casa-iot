package com.rest.data;

public class Chamado{

   private long id;

   private String assunto;

   private String mensagem;

   private Status status;

   public long getID(){
	return id;
   }
   public void setID(long id){
	this.id = id;
   }
   public String getAssunto(){
	return assunto;
   }
   public void setAssunto(String assunto){
   	this.assunto = assunto;
   }
   public String getMensagem(){
   	return mensagem;
   }
   public void setMensagem(String mensagem){
	this.mensagem = mensagem;
   }
   public Status getStatus(){
	return status;
   }
   public void setStatus(Status status){
	this.status = status;
   }

   @Override
   public int hashCode(){
	int hash = 7;
	hash = 89 * hash + (int) (this.id^ (this.id >>> 32));
	return hash;
   }


   @Override
   public boolean equals(Object obj){
     if (this == obj){
	return true;
     }
     if (obj == null){
	return false;
     }
     if (getClass() != obj.getClass()){
	return false; 
     }
     final Chamado other = (Chamado) obj;
     if (this.id != other.id) {
        return false;
     }
     return true;
   }
   @Override
   public String toString(){
	return "Chamado{" + "id=" + id + ", assunto=" + assunto + ", mensagem=" + mensagem + ", status=" + status;
   }


}


