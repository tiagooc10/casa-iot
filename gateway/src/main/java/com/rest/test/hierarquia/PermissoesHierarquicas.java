package com.rest.test;

import java.util.*;
import com.rest.test.hierarquia.*;

// == testar por igualdade de objeto
//.equals testa por igualdade de valor

public class PermissoesHierarquicas{

    NodeArvorePermissoes m=new NodeArvorePermissoes(0);

    //Adiciona uma permissao como filho do Topo da Arvore, todas as permissoes iniciais ficam aqui
    public void adicionarPermissao(int permissao){
        m.adicionarNodeArvorePermissoes(permissao);
    }

    //Adicionar a permissao como filho da permissao x
    public void adicionarPermissaoHerdandoDe(int x, int permissao){
      if((m.getData())==x){
        //adicionar
      }
      List<NodeArvorePermissoes> aux = new LinkedList<NodeArvorePermissoes>();
      aux.add(m);
      int i=0;
      while(i<aux.size()){
        NodeArvorePermissoes aux2=aux.get(i);
        for(int j=0; j<(aux2.getChildren()).size(); j++){
          if((((aux2.getChildren()).get(j)).getData())==x){
	    //Adicionar o nó aqui como 
     	    NodeArvorePermissoes s = (aux2.getChildren()).get(j);
	    NodeArvorePermissoes r = new NodeArvorePermissoes(permissao);
	    r.setParent(s);
            s.addChildren(r);    
          } 
          aux.add((aux2.getChildren()).get(j));
        }
        i++;
      }
    }

    //Dado uma permissao x, verifica todos aqueles que estão acima de x, por que esses são permissoes
    //que o usuario também "possui"
    public void permissoesQueHerdamDe(int x){

      List permissoesPais = new ArrayList();

      if((m.getData())==x){
        //adicionar
      }
      List<NodeArvorePermissoes> aux = new LinkedList<NodeArvorePermissoes>();
      aux.add(m);
      int i=0;
      while(i<aux.size()){
        NodeArvorePermissoes aux2=aux.get(i);
        for(int j=0; j<(aux2.getChildren()).size(); j++){
          if((((aux2.getChildren()).get(j)).getData())==x){
	    //Encontrou o nó que tem o valor igual a x
     	    NodeArvorePermissoes s = (aux2.getChildren()).get(j);
	    while(s!=null){
	      permissoesPais.add(s.getData());
              s=s.getParent();
	    }	
          } 
          aux.add((aux2.getChildren()).get(j));
        }
        i++;
      } 

	
      System.out.println("Permissoes pais de " + x + ": ");
      for(int k=0; k<permissoesPais.size(); k++){
        System.out.println(permissoesPais.get(k)+" ");
      }
      System.out.println();
    }

    //Imprime todas as permissoes no formato de uma arvore
    public void imprimir(){
	m.imprimirArvore();
    }


    public void carregarArvore(){
	try{
            //StorageArvorePermissoes st = new StorageArvorePermissoes();
	    //st.getArvore();
	}
	catch(Exception e){}
    }
}
