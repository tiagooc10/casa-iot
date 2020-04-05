package com.rest.test;

import java.util.*;
import com.rest.test.hierarquia.*;

// == testar por igualdade de objeto
//.equals testa por igualdade de valor

public class AtributosHierarquicos{

    NodeArvoreAtributos m=new NodeArvoreAtributos("Topo");

    //Adiciona um atributo como filho do Topo da Arvore, todos atributos iniciais ficam
    //aqui
    public void adicionarAtributo(String atributo){
        m.adicionarNodeArvoreAtributos(atributo);
    }

    //Adicionar o atributo como filho do atributo x
    public void adicionarAtributoHerdandoDe(String x, String atributo){
      if((m.getData()).equals(x)){
        //adicionar
      }
      List<NodeArvoreAtributos> aux = new LinkedList<NodeArvoreAtributos>();
      aux.add(m);
      int i=0;
      while(i<aux.size()){
        NodeArvoreAtributos aux2=aux.get(i);
        for(int j=0; j<(aux2.getChildren()).size(); j++){
          if((((aux2.getChildren()).get(j)).getData()).equals(x)){
	    //Adicionar o nó aqui como 
     	    NodeArvoreAtributos s = (aux2.getChildren()).get(j);
	    NodeArvoreAtributos r = new NodeArvoreAtributos(atributo);
	    r.setParent(s);
            s.addChildren(r);    
          } 
          aux.add((aux2.getChildren()).get(j));
        }
        i++;
      }
    }

    //Dado um atributo x, verifica todos aqueles que estão acima de x, por que esses são os atributos
    //que o usuario também "possui"
    public void atributosQueHerdamDe(String x){

      List atributosPais = new ArrayList();

      if((m.getData()).equals(x)){
        //adicionar
      }
      List<NodeArvoreAtributos> aux = new LinkedList<NodeArvoreAtributos>();
      aux.add(m);
      int i=0;
      while(i<aux.size()){
        NodeArvoreAtributos aux2=aux.get(i);
        for(int j=0; j<(aux2.getChildren()).size(); j++){
          if((((aux2.getChildren()).get(j)).getData()).equals(x)){
	    //Encontrou o nó que tem o valor igual a x
     	    NodeArvoreAtributos s = (aux2.getChildren()).get(j);
	    while(s!=null){
	      atributosPais.add(s.getData());
              s=s.getParent();
	    }	
          } 
          aux.add((aux2.getChildren()).get(j));
        }
        i++;
      } 

	
      System.out.println("Atributos pais de " + x + ": ");
      for(int k=0; k<atributosPais.size(); k++){
        System.out.println(atributosPais.get(k)+" ");
      }
      System.out.println();
    }

    //Imprime todos os atributos no formato de uma arvore
    public void imprimir(){
	m.imprimirArvore();
    }


    public void carregarArvore(){
	try{
            StorageArvoreAtributos st = new StorageArvoreAtributos();
	    st.getArvore();
	}
	catch(Exception e){}
    }
}

