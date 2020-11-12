package com.rest.test;


import java.util.*;
import java.util.HashMap;
import java.util.Map;

import com.rest.test.parser.Node;

public class myParser {
    
    boolean bool=false;

    Node g=null;

    String ultimaEntidade="";
    int idUltimaEntidade=-1;

    int id=100;

    //Conta o numero de nodes de uma arvore
    int contarNodes(Node n){
      if(n==null){
        return 0;
      }
      else if(n.getFilhoEsquerda()==null && n.getFilhoDireita()==null){
        return 1;
      }
      else if(n.getFilhoEsquerda()!=null && n.getFilhoDireita()!=null){
	return 1+(contarNodes(n.getFilhoEsquerda())+contarNodes(n.getFilhoDireita()));
      }
      else if(n.getFilhoEsquerda()==null && n.getFilhoDireita()!=null){
	return 1+contarNodes(n.getFilhoDireita());
      }
      else if(n.getFilhoEsquerda()!=null && n.getFilhoDireita()==null){
	return 1+contarNodes(n.getFilhoEsquerda());
      }
      return 0;
    }

    //auxilia o processamento de uma string
    //já deve existir uma arvore com o primeiro elemento
    public void parseAux(Node n, String s, int i){
	if(i>=s.length()){
	  return;
	}
        /*if(n!=null){
          imprimirArvore(n);
	}*/
	System.out.println("Processando caractere: " + s.charAt(i));

	if(Character.isLetter(s.charAt(i))){
	  String a="";

	  while(i<s.length() && (Character.isLetter(s.charAt(i)) || Character.isDigit(s.charAt(i)) || s.charAt(i)=='.' || s.charAt(i)=='>' || s.charAt(i)=='<' || s.charAt(i)=='=')){
            a+=s.charAt(i);
	    i++;
	  }
	  System.out.println("A: " + a);

	  if(n.getFilhoEsquerda()==null){
	    n.setFilhoEsquerda(a,i);
	    parseAux(n.getFilhoEsquerda(),s,i);
	  }
	  else if(n.getFilhoDireita()==null){
	    n.setFilhoDireita(a,i);
	    parseAux(n.getFilhoDireita(),s,i);
	  }
	}
	else if(s.charAt(i)=='&' || s.charAt(i)=='|'){
	  String a=""; a+=s.charAt(i);
	  n.adicionarNodeComoPai(a,i);
	  System.out.println("Depois de adicionar node como pai, temos o pai " + n.getParent().getData() + " do nó corrente " + n.getData());	
	  parseAux(n.getParent(), s, i+1);
	}
	else if(s.charAt(i)=='('){
	    parseAux(n,s,i+1);
	}
	else if(s.charAt(i)==')'){
	    System.out.println(n.getID());
	    parseAux(n.getParent(),s,i+1);
	}
    }


    //Parse Expressao 1 - Determina quem pode realizar as operacoes sobre os dispositivos
    public Set<Set<String>> parseStringExp1(String s){
        int j=0; 
        String[] partes=s.split(" ");
	System.out.println("partes1: " + partes[1]);

	Node n=null;
        int i=0;
        while(!Character.isLetter(partes[1].charAt(i))  && i<partes[1].length()) i++;

	if(i==partes[1].length()){
	  System.out.println("retornando null");
	  return null;
	}
	else{
	  String a="";
	  System.out.println("A: " + a);
	  while(i<partes[1].length() && ( Character.isLetter(partes[1].charAt(i)) || Character.isDigit(partes[1].charAt(i)) || partes[1].charAt(i)=='.' || partes[1].charAt(i)=='>' || partes[1].charAt(i)=='<' || partes[1].charAt(i)=='=' ) ){
            a+=partes[1].charAt(i);
	    i++;
	  }
	  System.out.println("A: " + a);
	  if(n==null){
            n=new Node(a,i);
	  }
	  System.out.println("A: " + a);
	}
	//Gera a primeira versao da arvore
	parseAux(n, partes[1], i);

	System.out.println("IMPRIMINDO VERSÃO 1 " + contarNodes(n) );
	imprimirArvore(n);
	while(n.getParent()!=null){
	  n=n.getParent();
	}
	//Distribuindo os ORs que ele encontrar
	Node x = distribuirOR(n);

	while(x.getParent()!=null){
	  x=x.getParent();
	}
	System.out.println("OK2");
	System.out.println("IMPRIMINDO VERSÃO 2 " + contarNodes(x) );
	imprimirArvore(x);

	String politicaLida=lerPolitica(x);
	//deletar a arvore X e N
	x=null;
	n=null;

        System.out.println("POLITICA LIDA DA ARVORE: " + politicaLida);
	return criarPoliticasAcesso(politicaLida);
    }


    //Parse Expressao 2 - Determina o grupo de dispositivos sobre os quais é concedida a permissão
    public Set<Set<String>> parseStringExp2(String expressao){

	//Parse Expressao 2 - Determina o grupo de dispositivos sobre os quais é concedida a permissão
	System.out.println("partes4: " + expressao);
	Node n=null;
        int i=0;
        while(!Character.isLetter(expressao.charAt(i))  && i<expressao.length()) i++;
	if(i==expressao.length()){
	  System.out.println("retornando null");
	  return null;
	}
	else{
	  String a="";
	  System.out.println("A: " + a);
	  while(i<expressao.length() && ( Character.isLetter(expressao.charAt(i)) || Character.isDigit(expressao.charAt(i)) || expressao.charAt(i)=='.' || expressao.charAt(i)=='>' || expressao.charAt(i)=='<' || expressao.charAt(i)=='=' ) ){
            a+=expressao.charAt(i);
	    i++;
	  }
	  System.out.println("A: " + a);
	  if(n==null){
            n=new Node(a,i);
	  }
	  System.out.println("A: " + a);
	}
	parseAux(n, expressao, i);

	System.out.println("IMPRIMINDO VERSÃO 1 " + contarNodes(n) );
	imprimirArvore(n);
	while(n.getParent()!=null){
	  n=n.getParent();
	}
	Node y = distribuirOR(n);
	while(y.getParent()!=null){
	  y=y.getParent();
	}
	System.out.println("IMPRIMINDO VERSÃO 2 " + contarNodes(y) );
	imprimirArvore(y);

        System.out.println("POLITICA LIDA DA ARVORE: " + lerPolitica(y));
	return criarPoliticasAcesso(lerPolitica(y));
    }


    //Parse Expressao 2 - Determina o grupo de dispositivos sobre os quais é concedida a permissão
    public String parseStringObjetos(String s){
      int j=0; 
      String[] partes=s.split(" ");

      return partes[4];
    }


    //Parse Expressao 3 - Determina a lista de operaçoes que serao concedidas aos usuarios sobre o grupo de dispositivos
    public String parseStringOperacoes(String s){
      int j=0; 
      String[] partes=s.split(" ");

      return partes[6];	
    }



   //Algoritmo de distribuir ORs
   public Node distribuirOR(Node n){
    if(n==null){
        return null;
      }
      if(n.getParent()==null){
        System.out.println(n.getData());
      }
      if(contarNodes(n)<=1){
	return n;
      }

      //Armazena todos os nos em uma lista, separados por um nó nulo que significa mudanca de nivel
      List<Node> aux = new LinkedList<Node>();
      aux.add(n);
      Node novaLinha=null;
      aux.add(novaLinha);
      int i=0;
      while(i<aux.size()){
        Node aux2=aux.get(i);
        if(aux2==null){
          System.out.println();
          if(i<aux.size()-1)aux.add(novaLinha);
        }
	else{
          if(aux2.getFilhoEsquerda()!=null){
	    aux.add(aux2.getFilhoEsquerda());
	  }
          if(aux2.getFilhoDireita()!=null){
	    aux.add(aux2.getFilhoDireita());
	  }
        }
        i++;
      }
      Collections.reverse(aux); 
	
      //Imprime a lista de nodes
      System.out.print("LISTA DE NODES: ");
      for(int j=0; j<aux.size(); j++){
        if(aux.get(j)!=null) System.out.print(aux.get(j).getData()+ "(" + aux.get(j).getID() + ")" + " ");
	else System.out.print(aux.get(j) + " ");
      }
      System.out.println();

      //Executa a distribuirOR_aux em todos os nós da arvore que estao na lista aux
      int count=0;
      do{
	count=0;
        for(int j=0; j<aux.size(); j++){
  	  int bool=0;        
          if(aux.get(j)!=null){
	    //Executa o distribuirOR auxiliar, para 
            bool=distribuirOR_aux(aux.get(j));
	    if(bool==1)imprimirArvore(aux.get(j));
	    if(bool==1){
	      aux.add(aux.get(j)); 
	      aux.add(aux.get(j).getFilhoEsquerda()); aux.add(aux.get(j).getFilhoDireita());

              List<Node> aux3 = new LinkedList<Node>();
              aux3.add(aux.get(j).getFilhoEsquerda());
              int z=0;
              while(z<aux3.size()){
                Node aux4=aux3.get(z);
                if(aux4!=null){
                  if(aux4.getFilhoEsquerda()!=null) aux3.add(aux4.getFilhoEsquerda());
                  if(aux4.getFilhoDireita()!=null) aux3.add(aux4.getFilhoDireita());
	        }
                z++;
              }
	      for(int w=0; w<aux3.size(); w++){
		aux.add(aux3.get(w));
	      }

              List<Node> aux5 = new LinkedList<Node>();
              aux5.add(aux.get(j).getFilhoDireita());
               z=0;
              while(z<aux5.size()){
                Node aux6=aux5.get(z);
                if(aux6!=null){
                  if(aux6.getFilhoEsquerda()!=null) aux5.add(aux6.getFilhoEsquerda());
                  if(aux6.getFilhoDireita()!=null) aux5.add(aux6.getFilhoDireita());
	        }
                z++;
              }
	      for(int w=0; w<aux5.size(); w++){
		aux.add(aux5.get(w));
	      }
	    }
	  }
	  count+=bool;
	  //if(bool==1)aux.add(aux.get(j));

          System.out.print("LISTA DE NODES: ");
          for(int k=0; k<aux.size(); k++){
            if(aux.get(k)!=null) System.out.print(aux.get(k).getData()+ "(" + aux.get(k).getID() + ")" + " ");
	    else System.out.print(aux.get(k) + " ");
          }
          System.out.println();
        }
      }while(count>0);

      for(int j=0; j<aux.size(); j++){
        if(aux.get(j)!=null){
	  //retorna algum nó que não tenha todos os campos nulos
	  //aquele que tem todos campos nulos eh um nó removido da arvore
	  if(!(aux.get(j).getParent()==null && aux.get(j).getFilhoEsquerda()==null && aux.get(j).getFilhoDireita()==null)){
	    System.out.println("Retornando " + aux.get(j).getData());
            return aux.get(j);
	  }
	}
      }
      return null;
    }

    //Auxilia na distribuicao dos ORs
    public int distribuirOR_aux(Node arvore){
      if(arvore==null){
        return 0;
      }
      System.out.println("EXECUTANDO DistribuirOR_Auxiliar em " + arvore.getData() +"(" + arvore.getID() + ")");
      if(arvore.getData().equals("&") && arvore.getParent()!=null){
	if(arvore.getParent().getData().equals("|")){
	  System.out.println("VERIFICOU AS CONDICOES");
	  //Criar dois nós - a,b - do tipo OR para serem filhos do AND
	  Node a = new Node("|",id++);
	  Node b = new Node("|",id++);
	  a.setParent(arvore);
	  b.setParent(arvore);
          //Associa os nós filhos do nó corrente aos nós a e b pela direita
	  arvore.getFilhoEsquerda().setParent(a);
	  a.setFilhoDireita(arvore.getFilhoEsquerda());
	  arvore.getFilhoDireita().setParent(b);
          b.setFilhoDireita(arvore.getFilhoDireita());
	  //Associa os nós - a,b - ao nó corrente 
          arvore.setFilhoEsquerda(a);	   
	  arvore.setFilhoDireita(b);
          //Pega o texto do filho esquerdo do pai (OR) do nó corrente (AND), 
          //para ser associado aos dois filhos do nó corrente
	  if(arvore.getParent().getFilhoEsquerda()!=null){
	    if(arvore.getParent().getFilhoEsquerda()!=arvore){
	      System.out.println(arvore.getParent().getData() + " NÓ ESTA A DIREITA, LOGO COPIARA A PARTE ESQUERDA DA ARVORE");
              //String data = arvore.getParent().getFilhoEsquerda().getData();
              //Copiar a arvore determinada pelo filho esquerdo do nó pai e colocá-la à esquerda
	      Node c = copiaArvore(arvore.getParent().getFilhoEsquerda()); //copia
	      Node d = copiaArvore(arvore.getParent().getFilhoEsquerda()); 
	      System.out.println("data do C: " + c.getData());
	      System.out.println("imprimirArvore copiada:");
	      imprimirArvore(c);
	      System.out.println("fim da impressão");
	      System.out.println("data do D: " + d.getData());
	      c.setParent(a);
	      d.setParent(b);
	      a.setFilhoEsquerda(c);
	      b.setFilhoEsquerda(d);
	      deletaArvore(arvore.getParent().getFilhoEsquerda());
	    }
	  }
	  if(arvore.getParent().getFilhoDireita()!=null){
	    if(arvore.getParent().getFilhoDireita()!=arvore){
	      System.out.println(arvore.getParent().getData() + " NÓ ESTA A ESQUERDA, LOGO COPIARA A PARTE DIREITA DA ARVORE");
              //String data = arvore.getParent().getFilhoDireita().getData();
              //Copiar a arvore determinada pelo filho esquerdo do nó pai e colocá-la à esquerda
	      Node c = copiaArvore(arvore.getParent().getFilhoDireita()); //copia
	      Node d = copiaArvore(arvore.getParent().getFilhoDireita()); 
	      System.out.println("data do C: " + c.getData());
      	      System.out.println("imprimirArvore copiada:");
	      imprimirArvore(c);
	      System.out.println("fim da impressão");
	      System.out.println("data do D: " + d.getData());
	      c.setParent(a);
	      d.setParent(b);
	      a.setFilhoEsquerda(c);
	      b.setFilhoEsquerda(d);
	      deletaArvore(arvore.getParent().getFilhoDireita());
	    }
	  }
	  //Atualiza o pai do nó corrente, que deve ser o pai do seu pai, e deleta o nó pai (w)
	  Node w = arvore.getParent();
	  if(arvore.getParent().getParent()!=null) System.out.println("Setando o pai de " + arvore.getData() + "(" + arvore.getID() + ")"  + ", que era " + arvore.getParent().getData() + "(" + arvore.getParent().getID() + ")" + " para " + arvore.getParent().getParent().getData() + "(" + arvore.getParent().getParent().getID() + ")");

	  else System.out.println("Setando o pai de " + arvore.getData() + "(" + arvore.getID() + ")"  + " para " + arvore.getParent().getParent());

	  arvore.setParent(arvore.getParent().getParent());
	  w.setFilhoEsquerda(null);
	  w.setFilhoDireita(null);
	  w.setParent(null);
	  if(arvore.getParent()!=null){
            if(w==arvore.getParent().getFilhoEsquerda()) arvore.getParent().setFilhoEsquerda(arvore);
            if(w==arvore.getParent().getFilhoDireita()) arvore.getParent().setFilhoDireita(arvore);
	  } 
	  return 1;
	}
      }
      return 0;
    }

    //Copia toda a arvore a partir do nó n
    public Node copiaArvore(Node n){
      if(n==null){
	return null;
      }
      Node x = new Node(n.getData(), id++);
  
      if(n.getFilhoEsquerda()!=null){
	x.setFilhoEsquerda(copiaArvore(n.getFilhoEsquerda()));        
      }
      if(n.getFilhoDireita()!=null){
        x.setFilhoDireita(copiaArvore(n.getFilhoDireita()));
      }
    
      return x;
    }

    //Deleta toda a arvoree a partir do nó n
    public void deletaArvore(Node n){
      if(n==null){
	return;
      }
      if(n.getFilhoEsquerda()!=null){
	deletaArvore(n.getFilhoEsquerda());      
      }
      if(n.getFilhoDireita()!=null){
        deletaArvore(n.getFilhoDireita());
      }
      n.setFilhoEsquerda(null);
      n.setFilhoDireita(null);
      n.setParent(null);
    }

    //Imprime todos os nós da árvore nível por nível
    public void imprimirArvore(Node n){

      if(n.getParent()==null){
        System.out.println(n.getData());
      }
      else{
	imprimirArvore(n.getParent());
      }

      List<Node> aux = new LinkedList<Node>();
      aux.add(n);
      Node novaLinha=null;
      aux.add(novaLinha);
      int i=0;
      while(i<aux.size()){
        Node aux2=aux.get(i);
        if(aux2==null){
          System.out.println();
          if(i<aux.size()-1)aux.add(novaLinha);
        }
	else{
	  System.out.print("filhos de " + aux2.getData() + "(" + aux2.getID() + ")" +": ");
          if(aux2.getFilhoEsquerda()!=null){
            System.out.print((aux2.getFilhoEsquerda()).getData() + "(" + (aux2.getFilhoEsquerda()).getID() + ")" + " ");
	    aux.add(aux2.getFilhoEsquerda());
	  }
          if(aux2.getFilhoDireita()!=null){
            System.out.print((aux2.getFilhoDireita()).getData() + "(" + (aux2.getFilhoDireita()).getID() + ")" + " ");
	    aux.add(aux2.getFilhoDireita());
	  }
	  System.out.println();
        }
        i++;
      }
    }

    //Realiza a leitura de uma politica
    public String lerPolitica(Node n){
      if(n==null){
	return "";
      }
      if(n.getFilhoEsquerda()==null && n.getFilhoDireita()==null){
        return n.getData();
      }
      if(n.getFilhoEsquerda()!=null && n.getFilhoDireita()==null){
        return "(" + lerPolitica(n.getFilhoEsquerda()) + ")" + n.getData();
      }
      if(n.getFilhoEsquerda()==null && n.getFilhoDireita()!=null){
	return n.getData() + "(" + lerPolitica(n.getFilhoDireita()) + ")";
      }
      if(n.getFilhoEsquerda()!=null && n.getFilhoDireita()!=null){
	if(n.getData()=="|")
          return "" + lerPolitica(n.getFilhoEsquerda()) + "" + n.getData() + "" + lerPolitica(n.getFilhoDireita()) + "";
	else
        return "" + lerPolitica(n.getFilhoEsquerda()) + "" + n.getData() + "" + lerPolitica(n.getFilhoDireita()) + "";
      }
      
      return "";
    }
 

    //Cria uma politica de acesso
    public Set<Set<String>> criarPoliticasAcesso(String s){

      String[] componentes = s.split("&");

      Set<Set<String>> resposta=null;
      //Processa todos os componentes e retorna o set com as politicas processadas
      resposta= funcao(componentes);
      if(resposta!=null) return resposta;


      if(componentes.length==1){
	Set<Set<String>> set= new HashSet<Set<String>>();
	Set<String> aux = new HashSet<String>();
	aux.add(componentes[0]);
	set.add(aux);

	return set;
      }

      if(componentes.length==4){
        for(int i=0; i<componentes.length; i++){
	  System.out.println("COMPONENTE: " + componentes[i]);
	  String[] subcomponentes=componentes[i].split("\\|");
          for(int j=0; j<subcomponentes.length; j++){
	    System.out.println("	SUBCOMPONENTE: " + subcomponentes[j]);
	  }
        }

  	String[] c1 = componentes[0].split("\\|");
	String[] c2 = componentes[1].split("\\|");
	String[] c3 = componentes[2].split("\\|");
	String[] c4 = componentes[3].split("\\|");

	for(int i=0; i<c1.length; i++){
	  c1[i] = c1[i].replace("(", "");
	  c1[i] = c1[i].replace(")", "");
	}
	for(int i=0; i<c2.length; i++){
	  c2[i] = c2[i].replace("(", "");
	  c2[i] = c2[i].replace(")", "");
	}
	for(int i=0; i<c3.length; i++){
	  c3[i] = c3[i].replace("(", "");
	  c3[i] = c3[i].replace(")", "");
	}
	for(int i=0; i<c4.length; i++){
	  c4[i] = c4[i].replace("(", "");
	  c4[i] = c4[i].replace(")", "");
	}
	
        Set<Set<String>> set= new HashSet<Set<String>>();

	for(int m=0; m<c1.length; m++){
	  for(int n=0; n<c2.length; n++){
	    for(int o=0; o<c3.length; o++){
	      for(int p=0; p<c4.length; p++){
		Set<String> aux = new HashSet<String>();
		aux.add(c1[m]); aux.add(c2[n]); aux.add(c3[o]); aux.add(c4[p]);
		set.add(aux);
		//ELIMINAR TODOS GRANDES QUE CONTEM UM PEQUENO DENTRO DELE	        
		//System.out.println("POLITICA : " + c1[m] + " & " + c2[n] + " & " + c3[o] + " & " + c4[p]);
	      }
	    }
	  }
	}
	for(Iterator<Set<String>> i = set.iterator(); i.hasNext();){ 
	  Set<String> a = i.next();
	  for(Iterator<Set<String>> j = set.iterator(); j.hasNext();){ 
	    Set<String> b = j.next();
	    if(a!=b && a.containsAll(b)){
	      System.out.println(a + " contém " + b);
	      i.remove(); 
	      break;
	    }
	    
	  }
	}

	for(Set<String> it: set){ 
	  System.out.print("POLITICA : ");
	  System.out.print(it);
	  System.out.println();
	}
	return set;
      }

      return null;
    }

    //Verifica se é a ultima permutacao, aquela que contém todos valores iguais a t. Ex.: 1111, 3333, 8888;
    boolean ultima(String s, int t){
      for(int i=0; i<s.length(); i++){
        if(Character.getNumericValue(s.charAt(i))!=t) return false;
      }
      return true;
    }

    //Gera todas as permutacoes de 0 ate o número de elementos de cada conjunto para i números, coloca essa permutacao na string s - 000, 001, 002,...
    //Precisa ser modificada para numero dos mais de uma casa decimal 
    Set<Set<String>> gerarPermutacoes(int i, int aux, String s, List<List<String>> c){
      System.out.println("PARAMETROS: i=" + i + ", aux=" + aux + ", s=" + s);
      if(aux==i){
        Set<Set<String>> resposta= new HashSet<Set<String>>();

        Set<String> aux2 = new HashSet<String>();
        for(int x=0; x<s.length(); x++){
          aux2.add(c.get(x).get(Character.getNumericValue(s.charAt(x))));
	  System.out.println("Adicionando o elemento " + c.get(x).get(Character.getNumericValue(s.charAt(x))) );
        }
        resposta.add(aux2);
        System.out.println(s);

        return resposta;
      }

      Set<Set<String>> resposta= new HashSet<Set<String>>(); 

      for(int k=0; k<c.get(aux).size(); k++){
        Set<Set<String>> setAux=gerarPermutacoes(i,aux+1,s+k, c);
	if(setAux!=null){
	  resposta.addAll(setAux);
	}
      }
   
      return resposta;
    }

    //recebe como entrada os componentes, que foram separados pelos &: (A|B|C) AND (C|D|E) AND (F|G|H)
    //retorna um conjunto com a politica processada
    Set<Set<String>> funcao(String[] componentes){

      //coloca todos os componentes em uma lista de listas.
      List<List<String>> c = new ArrayList<List<String>>();
      for(int i=0; i<componentes.length; i++){
        String[] aux = componentes[i].split("\\|");
        List<String> aux2 = Arrays.asList(aux);
        c.add(aux2); 
      }	
    

      for(int i=0; i<c.size(); i++){
	System.out.print("COMPONENTE " + (i+1) + ": ");	
	for(int j=0; j<c.get(i).size(); j++){
	  System.out.print(c.get(i).get(j)+" ");
        }
	System.out.println("\n");
      }

      //remove os parenteses dos componentes
      for(int i=0; i<c.size(); i++){
        List<String> aux=c.get(i);
        for(int j=0; j<aux.size(); j++){
          aux.set(j, aux.get(j).replace("(", "")); aux.set(j, aux.get(j).replace(")", ""));
        }
      }
     
      //recebe todas as permutacoes dos componentes
      Set<Set<String>> resposta = gerarPermutacoes(c.size(),0,"",c);

        for(Iterator<Set<String>> it1 = resposta.iterator(); it1.hasNext();){ 
            Set<String> a = it1.next();
	    for(Iterator<Set<String>> it2 = resposta.iterator(); it2.hasNext();){ 
	      Set<String> b = it2.next();
	      if(a!=b && a.containsAll(b)){
	        System.out.println(a + " contém " + b);
	        it1.remove(); 
	        break;
	      }    
	    }
          }
          for(Set<String> it: resposta){ 
	    System.out.print("POLITICA : ");
	    System.out.print(it);
	    System.out.println();
          }

      return resposta;
    }

}

