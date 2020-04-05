package com.rest.test.analiseContexto;

import com.rest.data.RegraContexto;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.lang.*;
import java.util.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.rest.test.*;

//return 0 = politica não concede permissão
//return 1 = politica não atua sobre o dispositivo da solicitacao/comando
//return 2 = politica nao atua sobre as operacoes da solicitacao/comando
//return 3 = politica concede permissão

public class analisadorPoliticas{

    public boolean temLetra(String s){
	for(int i=0; i<s.length(); i++){
          if(Character.isLetter(s.charAt(i)))
	    return true;
	}
	return false;
    }

    public int idObjeto(String objeto){
      if(objeto.equals("wemo")) return 1;
      if(objeto.equals("hue")) return 2;
      if(objeto.equals("iris")) return 3;
      if(objeto.equals("rfid")) return 4;
      if(objeto.equals("presenca")) return 5;
      if(objeto.equals("arduino")) return 6;
      if(objeto.equals("arCondicionado")) return 7;
      if(objeto.equals("playerMusica")) return 8;
      if(objeto.equals("sensorSmartThings")) return 9;
      if(objeto.equals("teste1")) return 11;
      if(objeto.equals("teste2")) return 12;
      if(objeto.equals("teste3")) return 13;
      if(objeto.equals("teste4")) return 14;
      if(objeto.equals("teste5")) return 15;
      if(objeto.equals("teste6")) return 16;
      if(objeto.equals("teste7")) return 17;
      if(objeto.equals("teste8")) return 18;
      if(objeto.equals("persiana")) return 20;
      if(objeto.equals("lampadas")) return 22;
  
      if(objeto.contains("teste")){
        String aux=objeto.replace("teste","");
	int id=Integer.parseInt(aux);
        if(id%9==0){
	  return 9;
	}	

	return 10+(id%9);
	
      }

      return -1;
    }

    //Id do atributo para a parte do get atributo dispositivo
    public int idAtributo(String dispositivo, String atributo){
      if(dispositivo.equals("wemo") && atributo.equals("status")) return 1;
      if(dispositivo.equals("wemo") && atributo.equals("consumo")) return 2;
      if(dispositivo.equals("sensorSmartThings") && atributo.equals("temperatura")) return 1;
      if(dispositivo.equals("teste1") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste2") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste3") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste4") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste5") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste6") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste7") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste8") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste9") && atributo.equals("status")) return 1;
      if(dispositivo.equals("teste10") && atributo.equals("status")) return 1;
      if(dispositivo.equals("persiana") && atributo.equals("status")) return 1;
      if(dispositivo.equals("lampadas") && atributo.equals("status")) return 1;

      if(dispositivo.contains("teste")){
	return 1;
      }
      return -1;
    }

    //Id da operacao no modulo que trata requisicoes
    public int idOperacao(String operacao){
      if(operacao.equals("ligar")) return 1;
      if(operacao.equals("desligar")) return 2;
      if(operacao.equals("getStatus")) return 3;

      if(operacao.equals("abrir")) return 1;
      if(operacao.equals("fechar")) return 2;
    
      return -1;
    }

    //Interface de comunicacao com o banco de dados
    Storage ss = new Storage();

    public analisadorPoliticas(){
   
    }

    //FALTA ALTERAR O MODO COMO SE BUSCA O AUX2 NO CASO DA ENTIDADE SER USUARIO, PODE-SE BUSCAR COMUNICANDO DIRETO COM O SENSOR, OU OUTRO JEITO

    public int processarPoliticaAuxiliar(int usuarioComando, String idComando,  List<String> entidades, List<String> atributos, 
	List<String> comparadores, List<String> valores, List<String> booleanos, Integer j){

      if(booleanos.size()==0 || booleanos.get(0).equals("AND")){
        //verificar a comparacao entre Usuario.atributo e Valor
        if(entidades.get(j).equals("usuario")){
          System.out.println("analisar atributo " + atributos.get(j) + " do usuario " + usuarioComando);
	      String aux2="";  
	      try{
	        //Buscar o valor do atributo
	        aux2=ss.getValorAtributoUsuario(usuarioComando,atributos.get(j));
	        System.out.println("valor1:" + aux2);
	      }
	      catch(Exception e){
	      }	
	      //Verificando se a palavra contem alguma letra
	      if(temLetra(aux2)){
 	        if(comparadores.get(j).equals("==")){
	          if(aux2.equals(valores.get(j))) return 1; 
	        }
	        else{
	          System.out.println("Politica tentando comparar algo que nao é == com String");
	          return 0;
	        }
	      }
	      else{
	        if(comparadores.get(j).equals("==")){
	          System.out.println("valor:" + valores.get(j));
	          if((Double.parseDouble(aux2)-Double.parseDouble(valores.get(j)) ) <0.000001 ) return 1;
	        }
	        if(comparadores.get(j).equals(">=")){
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals(">")){
	          System.out.println("Comparando: " + Double.parseDouble(aux2) + " e " + Double.parseDouble(valores.get(j)) );
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<=")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	      }
        }
        //verificar a comparacao entre Dispositivo.atributo e Valor
        if(entidades.get(j).equals("usuario")==false){
	      System.out.println("analisar atributo " + atributos.get(j) + " do dispositivo " + entidades.get(j));
	      String aux2="";
	      try{
	        //Buscar o valor do atributo
	        getValoresAtributos gv = new getValoresAtributos();
	        aux2=gv.getValorAtributo(idObjeto(entidades.get(j)), 1, idAtributo(entidades.get(j),atributos.get(j)), idComando);
	        //aux2=ss.getValorAtributoDispositivo(idObjeto(entidades.get(j)),atributos.get(j));
	        System.out.println("valor2:" + aux2);
	      }
	      catch(Exception e){
	      }
	      //Verificando se a palavra contem alguma letra
	      if(temLetra(aux2)){
 	        if(comparadores.get(j).equals("==")){
	          if(aux2.equals(valores.get(j))) return 1; 
	        }
	        else{
	          System.out.println("Politica tentando comparar algo que nao é == com String");
	          return 0;
	        }
	      }
	      //Caso nao tenha nenhuma letra será tratato como um número
	      else{
 	        if(comparadores.get(j).equals("==")){
	          if(Math.abs((Double.parseDouble(aux2)-Double.parseDouble(valores.get(j)) ) ) <0.000001) return 1;
	        }
	        if(comparadores.get(j).equals(">=")){
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals(">")){
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<=")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	      }
        }
        /*if(resposta!=j+1){
	      System.out.println("Política nao concede permissão !");
              return 0;
	    }
	    if(resposta==entidades.size()){
              System.out.println("Política concede permissão !");
	      return 3;
	    }*/
  	  }
	  else if(booleanos.get(0).equals("OR")){
        //verificar a comparacao entre Usuario.atributo e Valor
        if(entidades.get(j).equals("usuario")){
          System.out.println("analisar atributo " + atributos.get(j) + " do usuario " + usuarioComando);
	      String aux2="";  
	      try{
	        //Buscar o valor do atributo
	        aux2=ss.getValorAtributoUsuario(usuarioComando,atributos.get(j));
	        System.out.println("valor1:" + aux2);
	      }
	      catch(Exception e){
	      }
	      //Verificando se a palavra contem alguma letra
	      if(temLetra(aux2)){
 	        if(comparadores.get(j).equals("==")){
 	          if(aux2.equals(valores.get(j))) return 1;
	        }
	        else{
	          System.out.println("Politica tentando comparar algo que nao é == com String");
	          return 0;
	        }
	      }
	      else{	
	        if(comparadores.get(j).equals("==")){
	          System.out.println("valor:" + valores.get(j));
	          if((Double.parseDouble(aux2)-Double.parseDouble(valores.get(j)) ) <0.000001 ) return 1;
	        }
	        if(comparadores.get(j).equals(">=")){
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals(">")){
	          System.out.println("Comparando: " + Double.parseDouble(aux2) + " e " + Double.parseDouble(valores.get(j)) );
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<=")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	      }
        }
         //verificar a comparacao entre Dispositivo.atributo e Valor
        if(entidades.get(j).equals("usuario")==false){
	      System.out.println("analisar atributo " + atributos.get(j) + " do dispositivo " + entidades.get(j));
	      String aux2="";
	      try{
	        //Buscar o valor do atributo
	        getValoresAtributos gv = new getValoresAtributos();
	        aux2=String.valueOf(gv.getValorAtributo(idObjeto(entidades.get(j)), 1, idAtributo(entidades.get(j),atributos.get(j)), idComando) );
	        //aux2=ss.getValorAtributoDispositivo(idObjeto(entidades.get(j)),atributos.get(j));
	        System.out.println("valor2:" + aux2);
	      }
	      catch(Exception e){
	      }

	      //Verificando se a palavra contem alguma letra
	      if(temLetra(aux2)){
 	        if(comparadores.get(j).equals("==")){
 	          if(aux2.equals(valores.get(j))) return 1;
	        }
	        else{
	          System.out.println("Politica tentando comparar algo que nao é == com String");
	          return 0;
	        }
	      }
	      else{
 	        if(comparadores.get(j).equals("==")){
	          if(Math.abs((Double.parseDouble(aux2)-Double.parseDouble(valores.get(j)) ) ) <0.000001) return 1;
	        }
	        if(comparadores.get(j).equals(">=")){
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals(">")){
	          if(Double.parseDouble(aux2)>Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<=")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	        if(comparadores.get(j).equals("<")){
	          if(Double.parseDouble(aux2)<Double.parseDouble(valores.get(j))) return 1;
	        }
	      }
            }
	  }
      System.out.println("Política nao concede permissão !");
      return 0;	  
    }


    //Recebe como entrada uma politica uma tupla (usuario,objeto,operacao)
    //Processa a política, determinando se ela concede ou não permissão de acesso ao dispositivo
    //precisa retirar espacos,a-A, das strings resultantes
    public int processarPolitica(String s, final int usuarioComando, int objetoComando, int operacaoComando, int ID_COMANDO){
      System.out.println("Iniciando o processamento da politica: " + s);

      String a = Integer.toString(new Integer(usuarioComando));
      String b = Integer.toString(new Integer(objetoComando));
      String c = Integer.toString(new Integer(operacaoComando));

      Random random = new Random();

      int i=0,aux=0;
      //(Entidade.atributo comp Valor) Booleano (Entidade.atributo comp Valor)
      final List<String> entidades = new ArrayList<>(), atributos = new ArrayList<>(), 
        comparadores = new ArrayList<>(), valores = new ArrayList<>(), booleanos = new ArrayList<>();
      //Objetos e operacoes
      String objetos="",operacoes="";
      while(i<s.length()){
	if(s.charAt(i)=='(' && s.charAt(i+1)==')'){ aux++; i=i+2;}
        //Busca os atributos dos usuários
        if(s.charAt(i)=='(' && aux==0){
          i++;
	  while(aux==0){  
	    String entidade="", atributo="", comparador="",valor="",booleano="";      
            while(s.charAt(i)!='.'){
              entidade+=s.charAt(i);
              i++;
            }
	    System.out.println("entidade: " + entidade);
	    entidades.add(entidade);
            i++;
            while(s.charAt(i)!='>' && s.charAt(i)!='=' && s.charAt(i)!='<'){
              atributo+=s.charAt(i);
              i++;
	    }
	    System.out.println("atributo: " + atributo);
	    atributos.add(atributo);
            while(s.charAt(i)=='>' || s.charAt(i)=='=' || s.charAt(i)=='<'){
              comparador+=s.charAt(i);
              i++;
            }
	    System.out.println("comparador: " + comparador);
	    comparadores.add(comparador);
            while(s.charAt(i)!='O' && s.charAt(i)!='A' && s.charAt(i)!=')'){
              valor+=s.charAt(i);
	      i++;
            }
	   System.out.println("valor: " + valor);
	    valores.add(valor);
	    while(!booleano.equals("OR") && !booleano.equals("AND") && s.charAt(i)!=')'){
	      booleano+=s.charAt(i);
	      i++;  
	    }
	    if(s.charAt(i)==')'){
              aux++;
	    }
	    else{
	      booleanos.add(booleano);
	    }
	    System.out.println("booleano: " + booleano);
	  }
        }
        //Busca os objetos
        if(s.charAt(i)=='(' && aux==1){
          i++;
          while(s.charAt(i)!=')'){
            objetos+=s.charAt(i);
            i++;
          }
	  objetos=objetos.replaceAll(","," ");
	  System.out.println("objetos: " + objetos);
	  
          aux++;
        }
        //Busca as operações
        if(s.charAt(i)=='(' && aux==2){
          i++;
          while(s.charAt(i)!=')'){
            operacoes+=s.charAt(i);
            i++;
          }
	  System.out.println("operacoes: " + operacoes);
        }
        i++;
      }
      //Politica permite pois tem uma parte dinamica que será analisada posteriormente
      if(s.contains("Se()") && Integer.parseInt(objetos)==objetoComando && idOperacao(operacoes)==operacaoComando){
        return 3;
      }
      //Analisar se a politica ocorre sobre os objetos e operacoes corretos
      if(Integer.parseInt(objetos)!=objetoComando){
        System.out.println("Politica e Comando não são sobre os mesmos dispositivos ! " + Integer.parseInt(objetos) + " "  + objetoComando );
	return 0;
      }
      if(idOperacao(operacoes)!=operacaoComando){
        System.out.println("Politica e Comando não são sobre as mesmas operacoes !");
	return 0;
      }

      //conta se todos os Componentes estao retornando verdadeiro
      int resposta=0;
      List<Thread> threads = new ArrayList<Thread>();

      //vetor com as respostas para cada uma das politicas
      final int[] respostas = new int[entidades.size()];

      for(int j=0; j<entidades.size(); j++){

        ID_COMANDO=ObterId.getID_AUXILIAR();
        final String idComando=Integer.toString(ID_COMANDO);
        System.out.println("ID COMANDO1: " + idComando);

	final int indice=j;
	Thread t = new Thread(new Runnable() {
    	public void run(){
	  System.out.println("THREAD " + indice + " IRÁ PROCESSAR POLITICA");
	  int aux=processarPoliticaAuxiliar(usuarioComando, idComando, entidades, atributos, comparadores, valores, booleanos, indice);
	  respostas[indice]=aux;
   	}});  
    	threads.add(t);

	/*if(resposta!=j+1){
	  System.out.println("Política nao concede permissão !");
          return 0;
	}
	if(resposta==entidades.size()){
          System.out.println("Política concede permissão !");
	  return 3;
	}*/ 
      }
      for(int k=0; k<threads.size(); k++){
	  threads.get(k).start();
	}
      for(int k=0; k<threads.size(); k++){
	try{
	  threads.get(k).join();
	}
	catch(Exception e){}
      }
      System.out.print("VETOR RESPOSTA: " );
      for(int k=0; k<respostas.length; k++){
        System.out.print(respostas[k]+ " ");
      }
      System.out.println();

      for(int k=0; k<respostas.length; k++){
        if(respostas[k]==0){
	  return 0;
	}
      }
      System.out.println("Política concedeu permissao !");
      return 3;
    }


    //Recebe como entrada uma politica
    //Processa a política, determinando se ela concede ou não permissão de acesso ao dispositivo
    //precisa retirar espacos,a-A, das strings resultantes
    public List<Integer> processarExpressao2(String s){
      System.out.println("Iniciando o processamento da expressao2: " + s);
      myParser mp = new myParser();

      Set<Set<String>> regras = mp.parseStringExp2(s);
      List<Integer> conjuntoDispositivosFinal = new ArrayList<Integer>();

      boolean primeiro_grupo=true;
      for(Set<String> it1: regras){
        List<Integer> conjuntoDispositivos = new ArrayList<Integer>();

        for(String it2: it1){
          System.out.print("POLITICA : ");
          System.out.println(it2);
          String r="",c="",v="";
          int i=0;
          while(i<it2.length()){
            //seleciona a entidade e o atributo
            while(i<it2.length() && it2.charAt(i)!='=' && it2.charAt(i)!='>' && it2.charAt(i)!='<'){
              r+=it2.charAt(i);
              i++;
            }
            //seleciona o comparador
            while(i<it2.length() && (it2.charAt(i)=='=' || it2.charAt(i)=='>' || it2.charAt(i)=='<')){
              c+=it2.charAt(i);
              i++;
            }
                //seleciona o valor
            while(i<it2.length() && it2.charAt(i)!='=' && it2.charAt(i)!='>' && it2.charAt(i)!='<'){
              v+=it2.charAt(i);
              i++;
            }
          }
          String[] EntidadeAtributo=r.split("\\.");
          System.out.println("EntidadeAtributo: " + r); 
          for(int z=0; z<EntidadeAtributo.length; z++){
            System.out.println(z + ": " + EntidadeAtributo[z]);
          }
          String entidade=EntidadeAtributo[0]; 
          String atributo=EntidadeAtributo[1];
          String comparador=c;
          String valor=v;
          String booleano="AND";

          //DENTRO DO COMPONENTE (.OR.OR.OR.)
          //Para cada uma das regras (a.b<10), adiciona os dispositivos associados
          //gerando no final o grupo de dispositivos determinados por todos os ORs 
          System.out.print("Conjunto de Dispositivos: ");
          for(int k=0; k<conjuntoDispositivos.size(); k++){
            System.out.print(conjuntoDispositivos.get(k) +  " ");
          }
          System.out.println();
          Storage bd = new Storage();

          try{
            List<Integer> auxd = new ArrayList<Integer>();
            System.out.println("OK1 " + atributo + " " + comparador + " " + valor);
            auxd = bd.getDispositivosLimitadosPorExpressao(atributo, comparador, valor);
            for(int k=0; k<auxd.size(); k++){
              conjuntoDispositivos.add(auxd.get(k));
            }
          }
          catch(Exception e){
          }
        }

        //ENTRE COMPONENTES AND (...) AND (...) AND (...)
        System.out.print("Conjunto de Dispositivos FINAL: ");
        for(int k=0; k<conjuntoDispositivosFinal.size(); k++){
          System.out.print(conjuntoDispositivosFinal.get(k) +  " ");
        }
        System.out.println();
        Storage bd = new Storage();

        //Adiciona o primeiro grupo à solução final independende de quem estiver contido nele
        if(primeiro_grupo==true){
          for(int k=0; k<conjuntoDispositivos.size(); k++){
            conjuntoDispositivosFinal.add(conjuntoDispositivos.get(k));
          }
        }
        //Retira o membro da solução final caso ele não esteja contido nesse grupo
        else{
          List<Integer> remover = new ArrayList<Integer>();
          for(int k=0; k<conjuntoDispositivos.size(); k++){
            if(!conjuntoDispositivosFinal.contains(conjuntoDispositivos.get(k))){
              remover.add(conjuntoDispositivosFinal.get(k));
            }
          }
          conjuntoDispositivosFinal.removeAll(remover);
        }
      }
    	return conjuntoDispositivosFinal;
    }


    //Recebe uma politica e a armazena no banco de dados
    //Coloca a política na Forma Normal Conjuntiva
    //Separa as partes estáticas e dinâmicas da política
    //Armazena no banco de dados
    public void armazenarPolitica2(String s){
    	myParser mp = new myParser();
      	System.out.println("Armazenar 2");

      	//Politicas geradas com a primeira expressao
      	Set<Set<String>> politicas = mp.parseStringExp1(s);
      	//Operacoes obtidas da politica
      	String objetos = mp.parseStringObjetos(s);
      	//Operacoes obtidas da politica
      	String operacoes = mp.parseStringOperacoes(s);

      	for(Set<String> it1: politicas){
        	List<String> entidades = new ArrayList<>(), atributos = new ArrayList<>(), 
        	comparadores = new ArrayList<>(), valores = new ArrayList<>(), booleanos = new ArrayList<>();

		for(String it2: it1){
	  	    	System.out.print("POLITICA : ");
	  			System.out.println(it2);
	  			String r="",c="",v="";
	  			int i=0;
	  			while(i<it2.length()){
	    			//seleciona a entidade e o atributo
            		while(i<it2.length() && it2.charAt(i)!='=' && it2.charAt(i)!='>' && it2.charAt(i)!='<'){
	      				r+=it2.charAt(i);
	      				i++;
	    			}
	    			//seleciona o comparador
	    			while(i<it2.length() && (it2.charAt(i)=='=' || it2.charAt(i)=='>' || it2.charAt(i)=='<')){
	      				c+=it2.charAt(i);
	      				i++;
	    			}
            		//seleciona o valor
	    			while(i<it2.length() && it2.charAt(i)!='=' && it2.charAt(i)!='>' && it2.charAt(i)!='<'){
	      				v+=it2.charAt(i);
	      				i++;
	    			}
	  			}
	  			String[] EntidadeAtributo=r.split("\\.");
	  			System.out.println("EntidadeAtributo: " + r);	
          		for(int z=0; z<EntidadeAtributo.length; z++){
	    			System.out.println(z + ": " + EntidadeAtributo[z]);
	  			}
	  			entidades.add(EntidadeAtributo[0]); 
	  			atributos.add(EntidadeAtributo[1]);
	  			comparadores.add(c);
	  			valores.add(v);
	  			booleanos.add("AND");
			}
        		processarComponente(entidades, atributos, comparadores, valores, booleanos, objetos, operacoes);
    		}
	}



    //Armazena todas políticas
    //Faz um processamento direto, sem utilizar o parser, arvore e transformacao para FNC
    public void armazenarPolitica(String s){
      System.out.println("Iniciando o processamento da politica");
      int i=0,aux=0;
      //(Entidade.atributo comp Valor) Booleano (Entidade.atributo comp Valor)
      List<List<String> > entidades = new ArrayList<>(), atributos = new ArrayList<>(), 
        comparadores = new ArrayList<>(), valores = new ArrayList<>(), booleanos = new ArrayList<>();
	
      List<String> bool_entre_componentes = new ArrayList<>();

      //Objetos e operacoes
      String objetos="",operacoes="";
      while(i<s.length()){
	List<String> entidades_aux = new ArrayList<>(), atributos_aux = new ArrayList<>(), 
        comparadores_aux = new ArrayList<>(), valores_aux = new ArrayList<>(), booleanos_aux = new ArrayList<>();
	//Busca os atributos dos usuários
        if(s.charAt(i)=='(' && aux==0){
          i++;
	  while(aux<1){ 
	    String entidade="", atributo="", comparador="",valor="",booleano="";      
            while(s.charAt(i)!='.'){
	      if(s.charAt(i)=='('){
	        aux--; entidade=""; i++;
	      }  
              else{
                entidade+=s.charAt(i);
                i++;
	      }
            }
	    System.out.println("entidade: " + entidade);
	    entidades_aux.add(entidade);
	    //entidades.add(entidade);
            i++;
            while(s.charAt(i)!='>' && s.charAt(i)!='=' && s.charAt(i)!='<'){
              atributo+=s.charAt(i);
              i++;
	    }
	    System.out.println("atributo: " + atributo);
	    atributos_aux.add(atributo);
	    //atributos.add(atributo);
            while(s.charAt(i)=='>' || s.charAt(i)=='=' || s.charAt(i)=='<'){
              comparador+=s.charAt(i);
              i++;
            }
	    System.out.println("comparador: " + comparador);
	    comparadores_aux.add(comparador);
	    //comparadores.add(comparador);
            while(s.charAt(i)!='O' && s.charAt(i)!='A' && s.charAt(i)!=')'){
              valor+=s.charAt(i);
	      i++;
            }
	    System.out.println("valor: " + valor);
	    valores_aux.add(valor);
	    //valores.add(valor);
	    while(!booleano.equals("OR") && !booleano.equals("AND") && s.charAt(i)!=')'){
	      booleano+=s.charAt(i);
	      i++;  
	    }
	    if(s.charAt(i)==')'){
              while(s.charAt(i)==')'){
                aux++;
		if(aux<1){
	          System.out.print("Entidades Dentro dos parenteses: ");
	          for(int j=0; j<entidades_aux.size(); j++){
	            System.out.print(entidades_aux.get(j) + " "); 
	          }
		  System.out.println();
		  //Copias para serem adicionadas
		  List<String> add_entidades = new ArrayList<String>(entidades_aux);
		  List<String> add_atributos = new ArrayList<String>(atributos_aux);
		  List<String> add_comparadores = new ArrayList<String>(comparadores_aux);
		  List<String> add_valores = new ArrayList<String>(valores_aux);
		  List<String> add_booleanos = new ArrayList<String>(booleanos_aux);

		  entidades.add(add_entidades); atributos.add(add_atributos); 
	          comparadores.add(add_comparadores); valores.add(add_valores); booleanos.add(add_booleanos);

		  entidades_aux.clear(); atributos_aux.clear(); comparadores_aux.clear(); valores_aux.clear(); booleanos_aux.clear();
	          System.out.println();	
	        }
	        i++;
	      }
	      String bool_entre_componente="";
	      while(s.charAt(i)!='(' && i<s.length()){
	        bool_entre_componente+=s.charAt(i);
	        i++; 	 
	      }
	      if(bool_entre_componente.equals("OR") || bool_entre_componente.equals("AND")){
                bool_entre_componentes.add(bool_entre_componente);
	      }
	    }
	    else{
	      //booleanos.add(booleano);
	    }
	    System.out.println("booleano: " + booleano);
	    booleanos_aux.add(booleano);
	  }
        }
        //Busca os objetos
        if(s.charAt(i)=='(' && aux==1){
          i++;
          while(s.charAt(i)!=')'){
            objetos+=s.charAt(i);
            i++;
          }
          aux++;
	  System.out.println("objetos: " + objetos);
        }
        //Busca as operações
        if(s.charAt(i)=='(' && aux==2){
          i++;
          while(s.charAt(i)!=')'){
            operacoes+=s.charAt(i);
            i++;
          }
	  System.out.println("operacoes: " + operacoes);
        }
        i++;
      }
      //Imprimindo os componentes das políticas
      System.out.println("- COMPONENTES DAS POLITICAS -");
      for(int j=0; j<entidades.size(); j++){
        System.out.println("COMPONENTE " + (j+1) + ":");
        for(int k=0; k<(entidades.get(j)).size(); k++){
	  System.out.print("entidade:" + (entidades.get(j)).get(k) + " ");
	  System.out.print("atributo:" + (atributos.get(j)).get(k) + " ");
	  System.out.print("comparador:" + (comparadores.get(j)).get(k) + " ");
	  System.out.print("valor:" + (valores.get(j)).get(k) + " ");
	  System.out.print("objetos:" + objetos + " ");
          System.out.println("operacoes:" + operacoes + " ");    
        }
	System.out.print("booleanos do componente "+ (j+1) + ": ");
        for(int k=0; k<(booleanos.get(j)).size(); k++){
          System.out.print((booleanos.get(j)).get(k));
	  //System.out.print(" " + (booleanos.get(j)).get(k).equals(""));
	} 
	System.out.println();

	System.out.println("a lista esta vazia: " +  bool_entre_componentes.isEmpty());
      }
      //Caso o Operador entre os componentes seja OR - cada componente vira uma politica separada
      if(bool_entre_componentes.isEmpty() || bool_entre_componentes.get(0).equals("OR")){
	System.out.println("passou");
	for(int j=0; j<entidades.size(); j++){
          for(int k=0; k<(entidades.get(j)).size(); k++){
          
          }
          processarComponente(entidades.get(j),atributos.get(j),comparadores.get(j),valores.get(j),booleanos.get(j),objetos,operacoes);
	  System.out.println("FINAL");
        }
      }
      //Caso o Operador entre os componentes seja AND - ambos devem ser condiderados pra uma unica politica
      else if(bool_entre_componentes.get(0).equals("AND")){
	for(int j=0; j<entidades.size(); j++){
          for(int k=0; k<(entidades.get(j)).size(); k++){
       	       
          }
	  processarComponente(entidades.get(j),atributos.get(j),comparadores.get(j),valores.get(j),booleanos.get(j),objetos,operacoes);
        }
      }
      System.out.print("booleanos entre componentes: ");
      for(int k=0; k<bool_entre_componentes.size(); k++){
        System.out.print(bool_entre_componentes.get(k)+" ");
      }
      System.out.println();
      System.out.println("Terminou de armazenar a politica !");
    }


    //Recebe como parametro as entidades, atributos, comparadoes, valores, etc de uma politica
    //Separa um componente da Politica em parte dinamica e parte Estatica.
    //Armazena as partes estaticas e dinnamicas no banco, realizando a associacao entre elas, etc.
    public void processarComponente(List<String> entidades, List<String> atributos, List<String> comparadores, List<String> valores, List<String> booleanos, String objetos, String operacoes){
      Storage s = new Storage();
      String idDinamico="", idEstatico="", politicaEstatica="Se(", politicaDinamica="Se(";

      for(int i=0; i<atributos.size(); i++){
        if(verificaAtributoDinamico(entidades.get(i),atributos.get(i))){
          //verifica se ja existe algum entidade.atributo, caso exista adiciona o operando entre ele e o proximo
	  if(!idDinamico.equals("")){
            politicaDinamica+=(booleanos.get(i)+entidades.get(i)+"."+atributos.get(i)+comparadores.get(i)+valores.get(i));
	  }
	  else{
	    politicaDinamica+=(entidades.get(i)+"."+atributos.get(i)+comparadores.get(i)+valores.get(i));
	  }
	  idDinamico+=Integer.toString(i);
          idDinamico+=" ";      
        }
        else{
          if(!idEstatico.equals("")){
            politicaEstatica+=(booleanos.get(i)+entidades.get(i)+"."+atributos.get(i)+comparadores.get(i)+valores.get(i));
          }
	  else{
            politicaEstatica+=(entidades.get(i)+"."+atributos.get(i)+comparadores.get(i)+valores.get(i));
	  }
	  idEstatico+=Integer.toString(i);
	  idEstatico+=" ";
	}
      }

      //Cria uma politica para cada um dos objetos possiveis,
      //nesse momento deve ser processada a EXPRESSAO 2 para se obter o grupo de dispositivos
      List<Integer> objetos_ids = processarExpressao2(objetos);

      //Separa as operacoes que foram passadas 
      String[] operacoes_aux = operacoes.split(",");

      for(int m=0; m<objetos_ids.size(); m++){
        for(int n=0; n<operacoes_aux.length; n++){
          String aux1=politicaDinamica+("), Permitir em ("+objetos_ids.get(m)+"), Acoes("+operacoes_aux[n]+")");
          String aux2=politicaEstatica+("), Permitir em ("+objetos_ids.get(m)+"), Acoes("+operacoes_aux[n]+")"); 
          try{
            //Tem somente a parte estática 
            if(!idEstatico.equals("") && idDinamico.equals("") ){
	      System.out.println("Componente tem somente a parte estática");
              s.insertPoliticaEstatica(aux2,"",objetos_ids.get(m));
            }
	    //Tem somente a parte dinâmica
            /*else if(idEstatico.equals("") && !idDinamico.equals("")){
	      System.out.println("Componente tem somente a parte dinâmica");
  	      s.insertPoliticaEstatica(aux2,"");
	      String id=Integer.toString(s.getTamanhoPoliticasEstaticas());
	      s.insertPoliticaDinamica(aux1,id);
	      //caso o tipo==1 do dispositivo alvo, então deve enviar a política dinâmica para ele 
	      if(s.getTipoDispositivo(idObjeto(objetos_aux[m]))==1){
	        int idPolitica=s.getIdUltimaPoliticaDinamica();
	        s.insertPoliticaDinamicaEnviar(idPolitica, aux1, idObjeto(objetos_aux[m]), id);
	      }        
	    }*/
            //Tem partes dinãmica e estática
            else /*if(!idEstatico.equals("") && !idDinamico.equals(""))*/{
	      System.out.println("Componente tem parte estática e dinâmica");
	      String id1=Integer.toString(s.getTamanhoPoliticasDinamicas()+1);
	      s.insertPoliticaEstatica(aux2,id1,objetos_ids.get(m));
	      String id2=Integer.toString(s.getTamanhoPoliticasEstaticas());
	      s.insertPoliticaDinamica(aux1,id2);
	      //caso o tipo==1 do dispositivo alvo, então deve enviar a política dinâmica para ele 
	      if(s.getTipoDispositivo(objetos_ids.get(m))==1){
	        int idPolitica=s.getIdUltimaPoliticaDinamica();
	        s.insertPoliticaDinamicaEnviar(idPolitica, aux1, objetos_ids.get(m), id2);
              }
	    } 
	    System.out.println("Politica Dinamica armazenada: " + aux1);
            System.out.println("Politica Estatica armazenada: " + aux2);
          }
          catch(Exception e){}
	}
      }     
    }

    //Verifica se um atributo é dinâmico ou estático
    public boolean verificaAtributoDinamico(String entidade, String atributo){
      Storage s = new Storage();
      try{
        if(s.getTipoAtributo(atributo)==1){
          return true;
        }
      }catch(Exception e){}
      return false;
    }

    //Descobre sobre qual dispositivo a politica age
    public String getDispositivo(String s){
      int i=0,aux=0;
      String entidade="", atributo="", comparador="", valor="",objetos="",operacoes="";
      while(i<s.length()){
        //Busca os atributos dos usuários
        if(s.charAt(i)=='(' && aux==0){
          i++;
          while(s.charAt(i)!='.'){
            entidade+=s.charAt(i);
            i++;
          }
          i++;
          while(s.charAt(i)!='>' && s.charAt(i)!='=' && s.charAt(i)!='<'){
            atributo+=s.charAt(i);
            i++;
	  }
          while(s.charAt(i)=='>' || s.charAt(i)=='=' || s.charAt(i)=='<'){
            comparador+=s.charAt(i);
            i++;
          }
          while(s.charAt(i)!=')'){
            valor+=s.charAt(i);
	    i++;
          }
	  while(s.charAt(i)!=')') i++;  
          i++;
          aux++;
        }
        //Busca os objetos
        if(s.charAt(i)=='(' && aux==1){
          i++;
          while(s.charAt(i)!=')'){
            objetos+=s.charAt(i);
            i++;
          }
          aux++;
        }
        //Busca as operações
        if(s.charAt(i)=='(' && aux==2){
          i++;
          while(s.charAt(i)!=')'){
            operacoes+=s.charAt(i);
            i++;
          }
        }
        i++;
      }
      return objetos;
    }

}

