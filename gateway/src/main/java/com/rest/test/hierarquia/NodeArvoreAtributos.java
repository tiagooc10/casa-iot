package com.rest.test.hierarquia;

import java.util.*;


public class NodeArvoreAtributos {
    //Atributo 
    private String data;
    //Nó pai
    private NodeArvoreAtributos parent;
    //Lista de nós filhos
    private List<NodeArvoreAtributos> children;

    public NodeArvoreAtributos(String data){
      this.data=data;
      this.children = new LinkedList<NodeArvoreAtributos>();
    }


    public String getData(){
      return data;
    }
    
    public NodeArvoreAtributos getParent(){
      return parent;
    }
    public void setParent(NodeArvoreAtributos parent){
      this.parent=parent;
    }

    public List<NodeArvoreAtributos> getChildren(){
      return children;  
    }

    public void addChildren(NodeArvoreAtributos r){
      r.setParent(this);
      children.add(r);
    }


    //Adiciona um nó na árvore
    public void adicionarNodeArvoreAtributos(String s){
      NodeArvoreAtributos r = new NodeArvoreAtributos(s);
      children.add(r);
      r.setParent(this);    
    }

    public NodeArvoreAtributos encontrarNodeArvoreAtributos(NodeArvoreAtributos x){
      if(this.data.equals(x.data)){
        return this;
      }
      List<NodeArvoreAtributos> aux = new LinkedList<NodeArvoreAtributos>();
      aux.add(this);
      int i=0;
      while(i<aux.size()){
        NodeArvoreAtributos aux2=aux.get(i);
        for(int j=0; j<(aux2.children).size(); j++){
          if((((aux2.children).get(j)).data).equals(x.data)){
            return (aux2.children).get(j);
          } 
          aux.add((aux2.children).get(j));
        }
        i++;
      }
      return null;
    }
    
    //Imprime todos os nós da árvore nível por nível
    public void imprimirArvore(){
      if(parent==null){
        System.out.println(this.data);
      }
      List<NodeArvoreAtributos> aux = new LinkedList<NodeArvoreAtributos>();
      aux.add(this);
      NodeArvoreAtributos novaLinha=null;
      aux.add(novaLinha);
      int i=0;
      while(i<aux.size()){
        NodeArvoreAtributos aux2=aux.get(i);
        if(aux2==null){
          System.out.println();
          if(i<aux.size()-1)aux.add(novaLinha);
        }
	else{
	  System.out.print("filhos de " + aux2.data+": ");
          for(int j=0; j<(aux2.children).size(); j++){
            System.out.print(((aux2.children).get(j)).data + " ");
            aux.add((aux2.children).get(j));
          }
	  System.out.println();
        }
        i++;
      }
    }
}

