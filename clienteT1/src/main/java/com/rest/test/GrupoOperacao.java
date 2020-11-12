 
package com.rest.test;


public class GrupoOperacao{

  public int getGrupoOperacao(int dispositivo, int operacao){
    //Wemo
    if(dispositivo==1){
      //checar o status do wemo
      if(operacao==4) return 1;
      //ligar e desligar
      if(operacao==1 || operacao==2) return 2;
      //obter o consumo de energia
      if(operacao==3) return 3;
    }
    //Iris
    if(dispositivo==3){     
      //leitura de luminosidade e temperatura
      if(operacao==1 || operacao==2) return 1;
    }
    //Ar condicionado
    if(dispositivo==7){
      //ligar, desligar e alterar a temperatura
      if(operacao==1 || operacao==2 || operacao==3) return 2;
    }
    //Aparelho de Som
    if(dispositivo==8){
      //passar para anterior e próxima música
      if(operacao==3 || operacao==4) return 1;
      //pausar ou dar play
      if(operacao==1 || operacao==2) return 2;
    }
    //Sensor SmartThings
    if(dispositivo==9){
      //obter valor da temperatura
      if(operacao==1) return 1;
    }
    if(dispositivo==11){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==12){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==13){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==14){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==15){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==16){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==17){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    if(dispositivo==18){
      //getStatus
      if(operacao==3) return 1;
      //desligar e ligar
      if(operacao==1 || operacao==2) return 2;
    }
    return 0;
  }

}
