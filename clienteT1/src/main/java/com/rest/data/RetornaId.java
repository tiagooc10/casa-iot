package com.rest.data;

public class RetornaId{

   private int id;


   public int getId(){
   	return id;
   }
   public void setId(int id){
	this.id = id;
   }

   @Override
   public String toString(){
	return "Id{" + "id=" + id + "}";
   }

}


