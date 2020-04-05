package com.rest.test.analiseContexto;

import com.rest.test.*;


//Apos uma politica dinamica ser analisada, deve-se suspender sua análise e retornar PERMITIR
//por um intervalo de tempo (t) determinado pelo usuário.

//Essa suspensão pode ser feita por meio de uma coluna no banco de dados "avaliar". Caso esse campo
//esteja em 1, a política deve ser avaliada, caso ele esteja em 0, a política dinâmica não precisa ser avaliada
//e o comando do usuário deve ser permitido.


public class CachingPoliticas{

    Storage s = new Storage();

    public CachingPoliticas(){

    }

    public void delayTime(int idPolitica){
    

    }

}
