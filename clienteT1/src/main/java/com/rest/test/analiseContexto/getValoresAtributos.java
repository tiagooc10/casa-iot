package com.rest.test.analiseContexto;

import com.rest.test.TratarRequisicao;
import com.rest.test.Storage;
import com.rest.test.Resposta;

public class getValoresAtributos{

    TratarRequisicao t = new TratarRequisicao();
    Storage s = new Storage();
 
    String idComando="";



    public double getValorAtributo(int dispositivo, int sensor, int atributo, String idComando){

	this.idComando=idComando;

        try{
	    if(dispositivo==1) return getAtributoWemo(sensor,atributo);
	    else if(dispositivo==2) return getAtributoPhillipsHue(sensor,atributo);
	    else if(dispositivo==3) return getAtributoIris(sensor,atributo);
	    else if(dispositivo==4) return getAtributoRFID(sensor,atributo);
	    else if(dispositivo==5) return getAtributoPresenca(sensor,atributo);
	    else if(dispositivo==6) return getAtributoArduino(sensor,atributo);
	    else if(dispositivo==7) return getAtributoArCondicionado(sensor,atributo);
	    else if(dispositivo==8) return getAtributoPlayerMusica(sensor,atributo);
	    else if(dispositivo==9) return getAtributoSensorSmartThings(sensor,atributo);
            else if(dispositivo==11) return getAtributoDispositivoTeste1(sensor,atributo);
            else if(dispositivo==12) return getAtributoDispositivoTeste2(sensor,atributo);
            else if(dispositivo==13) return getAtributoDispositivoTeste3(sensor,atributo);
            else if(dispositivo==14) return getAtributoDispositivoTeste4(sensor,atributo);
            else if(dispositivo==15) return getAtributoDispositivoTeste5(sensor,atributo);
            else if(dispositivo==16) return getAtributoDispositivoTeste6(sensor,atributo);
            else if(dispositivo==17) return getAtributoDispositivoTeste7(sensor,atributo);
            else if(dispositivo==18) return getAtributoDispositivoTeste8(sensor,atributo);
        }catch(Exception e){}

        return -10;  
    }

    //Usuários - idade, localizacao, grupo, funcao
    double getAtributoUsuario(int id, int atributo){
      Resposta ri = new Resposta();
      try{
        //idade
        if(atributo==1){
  	  return Integer.parseInt(s.getValorAtributoUsuario(id,"idade"));
        }
        //localizacao, determina se o usuario está presente no ambiente
        if(atributo==1){
          ri=t.Tratar(5,id,1,"",idComando);
	  int[] v = ri.getValores();
	  boolean presente=false;
          for(int i=0; i<v.length; i++){
            if(v[i]==1) presente=true;
          }
          if(presente) return 1;
          else return 0;
        }
        //grupo
        if(atributo==3){
  	  return Integer.parseInt(s.getValorAtributoUsuario(id,"grupo"));
        }
        //funcao
        if(atributo==4){
          return Integer.parseInt(s.getValorAtributoUsuario(id,"funcao"));
        }
      }
      catch(Exception e){}
      return -4;
    }


    //Wemo - status, consumo
    double getAtributoWemo(int sensor, int atributo){
      Resposta ri = new Resposta();
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }
      return -4;
    }

    //Phillips - status, luminosidade, cor
    double getAtributoPhillipsHue(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(2,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }
      return -4;
    }

    //Iris - status, luminosidade, temperatura
    double getAtributoIris(int sensor, int atributo){
      Resposta ri = new Resposta();
  /*     
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }*/
      return -4;
    }

    //RFID - quantidade de pessoas, quem está presente
    double getAtributoRFID(int sensor, int atributo){
      Resposta ri = new Resposta();
/*       
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }*/
      return -4;
    }
    //Presenca - quantidade de pessoas, Quem está presente
    double getAtributoPresenca(int sensor, int atributo){
      Resposta ri = new Resposta();     
      //quem está presente
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }
      return -4;
    }
    //Arduino - status, gas carbonico, temperatura, ruido
    double getAtributoArduino(int sensor, int atributo){
      Resposta ri = new Resposta();
  /*     
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }*/
      return -4;
    }
    //Ar Condicionado - status, temperatura
    double getAtributoArCondicionado(int sensor, int atributo){
 /*     Resposta ri = new Resposta;
       
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }*/
      return -4;
    }
    //Player Musica - status, música, volume
    double getAtributoPlayerMusica(int sensor, int atributo){
      Resposta ri = new Resposta();
/*       
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"",idComando);
	int[] v = ri.getValores();
        return v[0];
      }*/
      return -4;
    }

    //Sensor SmartThings em frente à Porta 2303
    double getAtributoSensorSmartThings(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //temperatura
      if(atributo==1){
        ri=t.Tratar(9,sensor,1,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }

    //Dispositivos Teste1 - status
    double getAtributoDispositivoTeste1(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(11,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }

    //Dispositivos Teste2 - status
    double getAtributoDispositivoTeste2(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(12,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }

    //Dispositivos Teste3 - status
    double getAtributoDispositivoTeste3(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(13,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }

    //Dispositivos Teste4 - status
    double getAtributoDispositivoTeste4(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(14,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }

    //Dispositivos Teste5 - status
    double getAtributoDispositivoTeste5(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(15,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }

    //Dispositivos Teste6 - status
    double getAtributoDispositivoTeste6(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(16,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }
    //Dispositivos Teste7 - status
    double getAtributoDispositivoTeste7(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(17,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }
    //Dispositivos Teste8 - status
    double getAtributoDispositivoTeste8(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(18,sensor,3,"",idComando);
        int[] v = ri.getValores();
        return v[0];
      }

      return -4;
    }
}
