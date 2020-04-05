package com.rest.test.hierarquia;

import java.util.*;


public class NodeArvorePermissoes{
    //Atributo 
    private int data;
    //Nó pai
    private NodeArvorePermissoes parent;
    //Lista de nós filhos
    private List<NodeArvorePermissoes> children;

    public NodeArvorePermissoes(int data){
      this.data=data;
      this.children = new LinkedList<NodeArvorePermissoes>();
    }


    public int getData(){
      return data;
    }
    
    public NodeArvorePermissoes getParent(){
      return parent;
    }
    public void setParent(NodeArvorePermissoes parent){
      this.parent=parent;
    }

    public List<NodeArvorePermissoes> getChildren(){
      return children;  
    }

    public void addChildren(NodeArvorePermissoes r){
      r.setParent(this);
      children.add(r);
    }


    //Adiciona um nó na árvore
    public void adicionarNodeArvorePermissoes(int s){
      NodeArvorePermissoes r = new NodeArvorePermissoes(s);
      children.add(r);
      r.setParent(this);    
    }

    public NodeArvorePermissoes encontrarNodeArvorePermissoes(NodeArvorePermissoes x){
      if(this.data==x.data){
        return this;
      }
      List<NodeArvorePermissoes> aux = new LinkedList<NodeArvorePermissoes>();
      aux.add(this);
      int i=0;
      while(i<aux.size()){
        NodeArvorePermissoes aux2=aux.get(i);
        for(int j=0; j<(aux2.children).size(); j++){
          if((((aux2.children).get(j)).data)==x.data){
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
      List<NodeArvorePermissoes> aux = new LinkedList<NodeArvorePermissoes>();
      aux.add(this);
      NodeArvorePermissoes novaLinha=null;
      aux.add(novaLinha);
      int i=0;
      while(i<aux.size()){
        NodeArvorePermissoes aux2=aux.get(i);
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

