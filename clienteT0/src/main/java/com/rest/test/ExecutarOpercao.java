package com.rest.test;

/**
 * Created by toliveiracastro on 19/01/18.
 */

public class ExecutarOpercao {

    int status=0;

    public ExecutarOpercao(){

    }

    public int executar(int comando){
	

        if(comando==1){
            Ligar();
        }
        if(comando==2){
            Desligar();
        }
        if(comando==3){
            return RealizarLeitura();
        }

	return -1;

    }

    public void Ligar(){
        status=1;

    }

    public void Desligar(){
	status=0;
    }

    public int RealizarLeitura(){
        return status;
    }
}
