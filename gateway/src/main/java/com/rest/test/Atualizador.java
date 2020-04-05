package com.rest.test;

import java.sql.*;
import java.util.*;
import com.rest.test.analiseContexto.*;

public class Atualizador{

  StorageAtualizador s = new StorageAtualizador();


  //verifica se uma permissao se mantém, no momento que alguem envia um comando 
  public boolean verificarPermissao(int permissao, int idComando) throws SQLException{
    boolean foiDelegada=s.permissaoFoiDelegada(permissao);
    boolean verif=true;
    int permissaoDeletar=0;

    //permissao veio de uma delegacao
    if(foiDelegada==true){
      int permissaoPai=s.getIdPermissaoPai(permissao);
      while(permissaoPai!=0){
	//verifica se alguma das politicas associadas a permissao mantém a permissao
	verif=verificaSeAlgumaPolitica(permissaoPai, idComando);
	//faz a permissao ser igual a permissao pai
        permissaoPai=s.getIdPermissaoPai(permissao);
	if(verif==false){
	  permissaoDeletar=permissaoPai;
	}
      }
      if(verif==false){
	s.deletarArvore(permissaoDeletar);
      }
      return verif;
    }
    //permissao veio de alguma politica
    else{
      //Busca as políticas associadas a permissão no banco de dados
      List<Integer> politicasEstaticas = new ArrayList<Integer>();
      politicasEstaticas=s.getIdPoliticasEstaticas(permissao);

      for(int i=0; i<politicasEstaticas.size(); i++){
	if(verificaPoliticaEspecifica(politicasEstaticas.get(i),permissao, idComando)){
	  return true;
	  //Deletar a politica da permissao, apagar a permissao caso n tenha mais nenhuma politica
	}
      }
      return false;
    }
  }

  public boolean verificaPoliticaEspecifica(int idPolitica, int permissao, int idComando)  throws SQLException{
    analisadorPoliticas ap = new analisadorPoliticas();
    if(ap.processarPolitica(s.getPoliticaEstatica(idPolitica),s.getIdOwner(permissao), s.getIdDispositivo(permissao), s.getIdOperation(permissao), idComando)==3){
      return true;
    }
    return false;
  }

  //verifica se alguma das politicas ainda mantém a permissao
  public boolean verificaSeAlgumaPolitica(int permissao, int ID_COMANDO)  throws SQLException{
    analisadorPoliticas ap = new analisadorPoliticas();
    List<Integer> politicasEstaticas = new ArrayList<Integer>();
    politicasEstaticas=s.getIdPoliticasEstaticas(permissao);
    for(int i=0; i<politicasEstaticas.size(); i++){
      System.out.println("Processando Política: " + politicasEstaticas.get(i) );
      String politica=s.getPoliticaEstatica(politicasEstaticas.get(i));
      int aux=ap.processarPolitica(politica,s.getIdOwner(permissao),s.getIdDispositivo(permissao),s.getIdOperation(permissao), ID_COMANDO);
      if(aux==3){
	return true;
      }
    }
    return false;
  }
}
