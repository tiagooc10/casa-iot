package com.rest.test.analiseContexto;

import com.rest.test.TratarRequisicao;
import com.rest.test.Storage;
import com.rest.test.Resposta;

public class getValoresAtributos{

    TratarRequisicao t = new TratarRequisicao();
    Storage s = new Storage();

    String idComando="";

    public String getValorAtributo(int dispositivo, int sensor, int atributo, String idComando){

	t.setIdComando(idComando);

	System.out.println("ID COMANDO2: " + t.getIdComando() + ", DISPOSITIVO: " + dispositivo);

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
	    else if(dispositivo==20) return getAtributoPersiana(sensor,atributo);
	    else if(dispositivo==22) return getAtributoLampadas(sensor,atributo);
        }catch(Exception e){}

        return new Integer(-10).toString();
    }

    //Usuários - idade, localizacao, grupo, funcao
    String getAtributoUsuario(int id, int atributo){
      Resposta ri = new Resposta();
      try{
        //idade
        if(atributo==1){
  	      return s.getValorAtributoUsuario(id,"idade");
        }
        //localizacao, determina se o usuario está presente no ambiente
        if(atributo==1){
          ri=t.Tratar(5,id,1,"");
	      String[] v = ri.getValores();
	      boolean presente=false;
          for(int i=0; i<v.length; i++){
            if(v[i]=="1") presente=true;
          }
          if(presente) return "1";
          else return "0";
        }
        //grupo
        if(atributo==3){
  	      return s.getValorAtributoUsuario(id,"grupo");
        }
        //funcao
        if(atributo==4){
          return s.getValorAtributoUsuario(id,"funcao");
        }
      }
      catch(Exception e){}
      return new Integer(-4).toString();
    }


    //Wemo - status, consumo
    String getAtributoWemo(int sensor, int atributo){
      Resposta ri = new Resposta();
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	    String[] v = ri.getValores();
        return v[0];
      }
      return new Integer(-4).toString();
    }

    //Phillips - status, luminosidade, cor
    String getAtributoPhillipsHue(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(2,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }
      return new Integer(-4).toString();
    }

    //Iris - status, luminosidade, temperatura
    String getAtributoIris(int sensor, int atributo){
      Resposta ri = new Resposta();
  /*     
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }*/
      return new Integer(-4).toString();
    }

    //RFID - quantidade de pessoas, quem está presente
    String getAtributoRFID(int sensor, int atributo){
      Resposta ri = new Resposta();
/*       
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }*/
      return new Integer(-4).toString();
    }
    //Presenca - quantidade de pessoas, Quem está presente
    String getAtributoPresenca(int sensor, int atributo){
      Resposta ri = new Resposta();     
      //quem está presente
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }
      return new Integer(-4).toString();
    }
    //Arduino - status, gas carbonico, temperatura, ruido
    String getAtributoArduino(int sensor, int atributo){
      Resposta ri = new Resposta();
  /*     
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }*/
      return new Integer(-4).toString();
    }
    //Ar Condicionado - status, temperatura
    String getAtributoArCondicionado(int sensor, int atributo){
 /*     Resposta ri = new Resposta;
       
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }*/
      return new Integer(-4).toString();
    }
    //Player Musica - status, música, volume
    String getAtributoPlayerMusica(int sensor, int atributo){
      Resposta ri = new Resposta();
/*       
      //status
      if(atributo==1){
        ri=t.Tratar(1,sensor,4,"");
        String[] v = ri.getValores();
        return v[0];
      }
      //consumo
      if(atributo==2){
        ri=t.Tratar(1,sensor,3,"");
	String[] v = ri.getValores();
        return v[0];
      }*/
      return new Integer(-4).toString();
    }

    //Dispositivos Teste1 - status
    String getAtributoSensorSmartThings(int sensor, int atributo){
      System.out.println("Buscando Info no Sensor SmartThings");
      Resposta ri = new Resposta();
       
      //temperatura
      if(atributo==1){
        ri=t.Tratar(9,sensor,1,"");
        String[] v = ri.getValores();
	System.out.println("Retornando V[0]="+v[0]);
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste1 - status
    String getAtributoDispositivoTeste1(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(11,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste2 - status
    String getAtributoDispositivoTeste2(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(12,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste3 - status
    String getAtributoDispositivoTeste3(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(13,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste4 - status
    String getAtributoDispositivoTeste4(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(14,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste5 - status
    String getAtributoDispositivoTeste5(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(15,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste6 - status
    String getAtributoDispositivoTeste6(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(16,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste7 - status
    String getAtributoDispositivoTeste7(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(17,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Dispositivos Teste8 - status
    String getAtributoDispositivoTeste8(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(18,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }

      return new Integer(-4).toString();
    }

    //Persiana
    String getAtributoPersiana(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      //status
      if(atributo==1){
        ri=t.Tratar(20,sensor,3,"");
        String[] v = ri.getValores();
        return v[0];
      }
      
      return new Integer(-4).toString();
    }

    //Lampadas
    String getAtributoLampadas(int sensor, int atributo){
      Resposta ri = new Resposta();
       
      
      return new Integer(-4).toString();
    }
}
