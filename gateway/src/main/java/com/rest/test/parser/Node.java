
package com.rest.test.parser;

import java.util.*;


public class Node {

    //Identificador
    private int id;
    //Atributo 
    private String data;
    //Nó pai
    private Node parent=null;
    //Nó esquerda
    private Node left=null;
    //Nó direita
    private Node right=null;

    public Node(String data, int id){
      this.data=data;
      this.id=id;  
    }

    public int getID(){
      return id;
    }

    public void setID(int id){
      this.id=id;
    }

    public String getData(){
      return data;
    }

    public void setData(String data){
      this.data=data;
    }

    public Node getParent(){
      return parent;
    }

    public Node getFilhoEsquerda(){
      return left;
    }

    public Node getFilhoDireita(){
      return right;
    }

    public void setParent(Node parent){
      this.parent=parent;
    }


    //Adiciona um nó na árvore
    public void setFilhoEsquerda(String s, int id){
      System.out.println("Adicionando " + s + "(" + id + ")" + " a esquerda de " + this.getData() + "(" + this.getID() + ")");
      Node r = new Node(s, id);
      this.left=r;
      r.setParent(this);      
    }

    public void setFilhoEsquerda(Node n){
      if(n==null) System.out.println("Adicionando null " + " a esquerda de " + this.getData() + "(" + this.getID() + ")");
      else System.out.println("Adicionando " + n.getData() + "(" + n.getID() + ")" + " a esquerda de " + this.getData() + "(" + this.getID() + ")");
      if(n!=null) n.setParent(this); 
      this.left=n;   
    }

    //Adiciona um nó na árvore
    public void setFilhoDireita(String s, int id){
      System.out.println("Adicionando " + s + "(" + id + ")"  + " a direita de " + this.getData() + "(" + this.getID() + ")");
      Node r = new Node(s, id);
      this.right=r;
      r.setParent(this);    
    }

    public void setFilhoDireita(Node n){
      if(n==null) System.out.println("Adicionando null " + " a direita de " + this.getData() + "(" + this.getID() + ")");
      else System.out.println("Adicionando " + n.getData() + "(" + n.getID() + ")"  + " a direita de " + this.getData() + "(" + this.getID() + ")");
      this.right=n; 
      if(n!=null) n.setParent(this);   
    }

    public int filhoEsquerdaOuDireita(Node x){
      if(x.getID()==this.parent.left.id && x.getData()==this.parent.left.data) return 1;
      if(x.getID()==this.parent.right.id && x.getData()==this.parent.right.data) return 0;

      return -1;
    }

    //Adiciona um nó na árvore
    public void adicionarNodeComoPai(String s, int id){
      Node x = new Node(s, id);
      System.out.println("Adicionando " + x.getData() + "(" + x.getID() + ")"  + " como pai de " + this.getData() + "(" + this.getID() + ")");
      x.setParent(this.getParent());
      if(this.getParent()!=null) this.getParent().setFilhoDireita(x);
      x.setFilhoEsquerda(this);
      /*if(filhoEsquerdaOuDireita(this)==1) this.getParent().setFilhoEsquerda(x);
      else this.getParent().setFilhoDireita(x);  */
      this.setParent(x); 

      System.out.println(this.getParent().getFilhoEsquerda().getData());
    }


/*
   //Adicionar um nó na árvore como filho do de x
    public void adicionarNodeHerdandoDe(String x, String atributo, int idPai, int idFilho){
      System.out.println("Adicionando entidade: " + atributo + ", tendo como pai: " + x);
      if((this.getData()).equals(x) && this.getID()==idPai){
	Node r = new Node(atributo,idFilho);
	r.setParent(this);
        this.addChildren(r);  
	return;
      }
      List<Node> aux = new LinkedList<Node>();
      aux.add(this);
      int i=0;
      while(i<aux.size()){
        Node aux2=aux.get(i);
        for(int j=0; j<(aux2.getChildren()).size(); j++){
          if((((aux2.getChildren()).get(j)).getData()).equals(x) && (((aux2.getChildren()).get(j)).getID())==idPai){
     	    Node s = (aux2.getChildren()).get(j);
	    Node r = new Node(atributo,idFilho);
	    r.setParent(s);
            s.addChildren(r); 
	    return;   
          } 
          aux.add((aux2.getChildren()).get(j));
        }
        i++;
      }
    }
*/

/*
    //Adiciona um nó na árvore
    public void adicionarOperandoArvore(String pai, String operando, int idPai, int idFilho){
      System.out.println("Adicionando operando: " + operando + ", tendo como pai: " + pai);
      if((this.getData()).equals(pai) && this.getID()==idPai){
	Node r = new Node(operando,idFilho);
	r.setParent(this);
        this.addChildren(r);  
	this.setData(operando); r.setData(pai); this.setID(idFilho); r.setID(idPai);
	return;
      }
      List<Node> aux = new LinkedList<Node>();
      aux.add(this);
      int i=0;
      while(i<aux.size()){
        Node aux2=aux.get(i);
        for(int j=0; j<(aux2.getChildren()).size(); j++){
          if((((aux2.getChildren()).get(j)).getData()).equals(pai) && (((aux2.getChildren()).get(j)).getID())==idPai){
     	    Node s = (aux2.getChildren()).get(j);
	    //adicinar o nó Y no lugar de X, e colocar X como filho de Y
	    Node r = new Node(operando,idFilho);
	    r.setParent(s);
            s.addChildren(r);    
	    s.setData(operando); r.setData(pai); s.setID(idFilho); r.setID(idPai);
	    return;
          } 
          aux.add((aux2.getChildren()).get(j));
        }
        i++;
      }      
    }
*/

/*
    public Node encontrarNode(Node x){
      if(this.data.equals(x.data)){
        return this;
      }
      List<Node> aux = new LinkedList<Node>();
      aux.add(this);
      int i=0;
      while(i<aux.size()){
        Node aux2=aux.get(i);
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
*/


    //Imprime todos os nós da árvore nível por nível
    public void imprimirArvore(){
      if(parent==null){
        System.out.println(this.data);
      }
      List<Node> aux = new LinkedList<Node>();
      aux.add(this);
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
	  System.out.print("filhos de " + aux2.data + "(" + aux2.id + ")" +": ");
          if(aux2.left!=null){
            System.out.print((aux2.left).data + "(" + (aux2.left).id + ")" + " ");
	    aux.add(aux2.left);
	  }
          if(aux2.right!=null){
            System.out.print((aux2.right).data + "(" + (aux2.right).id + ")" + " ");
	    aux.add(aux2.right);
	  }
	  System.out.println();
        }
        i++;
      }
    }
}

