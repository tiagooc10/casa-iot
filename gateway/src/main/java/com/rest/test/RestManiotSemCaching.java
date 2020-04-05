package com.rest.test;

import java.util.List;
import java.util.ArrayList;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.*;

import com.rest.test.analiseContexto.*;
import com.rest.data.*;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.io.DataOutputStream;
import java.io.IOException;

//import org.glassfish.jersey.server.ResourceConfig;



@Path("/rest")
public class RestManiotSemCaching {

  boolean CACHING=false;

  static int ID_C=0;
  static int ID_S=0;

  public static synchronized int getID_SOLICITACAO(){
        ID_S++;
	return ID_S;
  }

/*
  public static synchronized int getID_COMANDO(){
        ID_C++;
	return ID_C;
  }
*/


  //int ID_COMANDO=getID_COMANDO();	

//Primeiramente recebe um login e uma senha fornecidos pelo usuário, que deve estar
//previamente cadastrado no banco
//Envia para o usuário um ID fornecido pela arquitetura MANIOT, armazena esse id no banco, 
//de acordo com o usuario e senha ja fornecidos
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/login")
   public RetornaId create(Login login){
	System.out.println(login.toString());
	RetornaId r = new RetornaId();
	Storage s;
	s = new Storage();
	int randomNum=-1;
        try{
	  //verifica se login e password estao corretos
	  if(s.selectUser(login.getLogin(),login.getPassword())>0){
	    System.out.println("Login Correto");
	    //verifica se o usuario ja possui um id cadastrado, o retornando caso já possua
	    if(s.selectUserRandomID(login.getLogin(),login.getPassword())!=-1){
	      System.out.println("Retornando o ID já cadastrado");
              r.setId(s.selectUserRandomID(login.getLogin(),login.getPassword()));
	      return r;
	    }  
	    //Gera ID e coloca o seu valor no banco, junto do login e senha que foram passados
	    else{
	      System.out.println("Retornando ID gerado (primeiro acesso)");
              randomNum = ThreadLocalRandom.current().nextInt(1000000, 9999999);
	      s.insertUserRandomID(login.getLogin(),login.getPassword(),randomNum);
	    }
	  }
          else{
	    System.out.println("Login ou Senha Incorretos");
   	  }
        }
        catch(Exception e){

	}
	r.setId(randomNum);
	return r;
  }


//Recebe uma solicitacao para acessar, de maneira exclusiva, certos sensores
//formato = id, dispositivo, sensor
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/solexc")
   public Resposta create(SolicitacaoExclusiva se){
        System.out.println(se.toString());

	int ID_COMANDO=getID_SOLICITACAO();	

	//Resposta Retornada pela Requisicao
	Resposta ri = new Resposta();
	//Interface para acessar o banco de dados
	Storage s;
	s = new Storage();
	try{
	  int id=se.getId(), dispositivo=se.getDispositivo(), sensor=se.getSensor();
	  //primeiramente, verifica se o id fornecido é valido
          if(s.verificaIDvalido(id)>0){
	    String idPolitica="";
	    //Análise de politicas 
	    analisadorPoliticas ap = new analisadorPoliticas();
	    boolean resultado=false;
            try{
	      //Analisa primeiro as políticas estáticas para verificar se alguma irá negar ou conceder a permissão
              Map<Integer,String> politicasEstaticas = new HashMap<Integer,String>();
	      politicasEstaticas = s.getPoliticasEstaticas(dispositivo); /*Arrumar - passar o dispositivo como parametro */
	      for(Map.Entry<Integer, String> entry : politicasEstaticas.entrySet()){
	        System.out.println("Processando Políticas");
                int aux=ap.processarPolitica(entry.getValue(),id,dispositivo, 1, ID_COMANDO);  /*Arrumar isso da permissão quando exclusiva */
	        if(aux==3){
	          resultado=true;
		  //Adiciona os ids das politicas que concederam permissao
	          idPolitica+=Integer.toString(entry.getKey()); idPolitica+=" ";
	        }
	        if(aux==0) resultado=false;
	      }
            }
            catch(Exception e){}  
	    if(resultado==false){
	      System.out.println("Não encontrou política estática que permitisse a operação !");
	      return ri; 
	    }
	    //verifica se ninguem esta utilizando um dado sensor
	    //se isso for confirmado associa o dado sensor ao usuario
	    if(s.verifUso(id,dispositivo,sensor)>0){
	      s.DarExc(id,dispositivo,sensor);
	    }
	    else{
	      //busca o id de quem esta utilizando o sensor
	      int id2=s.getIDUser(dispositivo,sensor);
              //verifica quem possui maior prioridade
	      if(s.getPrioridade(id)>s.getPrioridade(id2)){
		//coloca o novo usuario como detentor do acesso ao sensor 
		s.substituirUsuario(dispositivo,sensor,id);
	      }
	    }
	    return ri;
          }
	} 
	catch(Exception e){
	}  

	System.gc();
	return ri;
   }


//Recebe uma solicitacao para acessar certos sensores, de maneira não exclusiva.
//formato = id, dispositivo, sensor, operacao
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/sol")
   public Resposta create(Solicitacao so){

	int ID_COMANDO=getID_SOLICITACAO();
	//Interface de comunicao com o banco de dados
	Storage s = new Storage();
	GrupoOperacao g = new GrupoOperacao();
	//Obtem as informacoes sobre a solicitacao
   	int id=so.getId(), dispositivo=so.getDispositivo(), sensor=so.getSensor(), operacao = so.getOperacao();
	//Resposta Retornada pela Requisicao
	Resposta ri = new Resposta();

	//Verifica se o id é valido
	try{
	  if(s.verificaIDvalido(id)<=0){
	    String[] intArray = {"-1"};
	    ri.setValores(intArray);
	    return ri;
	  }
	  else{
	    System.out.println("ID é válido");
          }
	}
	catch(Exception e){
	}
	
	String idPolitica="";
	//Análise de politicas 
	analisadorPoliticas ap = new analisadorPoliticas();
	boolean resultado=false;
        try{
	  //Analisa primeiro as políticas estáticas para verificar se alguma irá negar ou conceder a permissão
          Map<Integer,String> politicasEstaticas = new HashMap<Integer,String>();
	  politicasEstaticas = s.getPoliticasEstaticas(dispositivo);
	  for(Map.Entry<Integer, String> entry : politicasEstaticas.entrySet()){
	    System.out.println("Processando Política Estática: " + entry.getValue());
            int aux=ap.processarPolitica(entry.getValue(),id,dispositivo, operacao, ID_COMANDO);
	    if(aux==3){
	      System.out.println("Política " + entry.getKey() + " resultou em PERMITIR");
	      resultado=true;
	      idPolitica+=Integer.toString(entry.getKey()); idPolitica+=" ";
	    }
	    else{
	      System.out.println("Essa Política Estática não concedeu permissao");
	    }
	  }
        }
        catch(Exception e){}  
	if(resultado==false){
	  String[] intArray = {"-1"};
	  ri.setValores(intArray);
	  System.out.println("Não encontrou política estática que permitisse a operação !");
	  return ri; 
	}

        try{
   	  //Importante notar que o usuario que pode realizar uma operacao de indice menor, pode realizar todas
   	  //de indice menor, por exemplo, se ele pode configura o wemo (3), também pode atuar sobre ele (2) e realizar leituras do ambiente (1)

	  //Deve verificar se o sensor desejado nao esta sobre uso exclusivo de alguem
	  if(s.verifExc(dispositivo,sensor)>0 /*aqui deve verificar se eh exclusivo de alguem */){
	    System.out.println("Sensor não está em uso exclusivo");
	    //Verifica se eh possivel conceder a permissao da operacao esperada
	    if(true){
   	      //caso seja leitura - a principio nenhum problema, pode conceder
   	      if(g.getGrupoOperacao(dispositivo,operacao)==1){
	        System.out.println("Operacao do grupo 1");
	        s.DarPerm(id,dispositivo,sensor,operacao,idPolitica);
	      }
   	      //caso seja atuação - ligar, desligar, etc - analisar prioridade do usuario
   	      if(g.getGrupoOperacao(dispositivo,operacao)==2 && s.getPrioridade(id)>=3){
		System.out.println("Operacao do grupo 2");
	        boolean aux=true;
	        int prioridade=s.getPrioridade(id);
		//verifica se algum dos que estao associados tem maior prioridade
	        List<Integer> usuarios = new ArrayList<Integer>(); usuarios = s.getUsuariosAssociados(dispositivo,sensor);
	        for(int i=0; i<usuarios.size(); i++){
	          if(s.getPrioridade(usuarios.get(i))>prioridade){
		    aux=false;    
		  }
	        }
	        if(aux){
	          s.DarPerm(id,dispositivo,sensor,operacao,idPolitica);
	        }
		else{
		  System.out.println("Existe algum usuario com prioridade mais alta associado a operacao 2 deste dispositivo");
		  String[] intArray = {"-3"};
	          ri.setValores(intArray);
		}
	      }
   	      //caso seja reconfiguração, alterar frequencia de coleta, outras configurações
   	      if(g.getGrupoOperacao(dispositivo,operacao)==3 && s.getPrioridade(id)>=5){
		System.out.println("Operacao do grupo 3");
	        boolean aux=true;
	        int prioridade=s.getPrioridade(id);
		//verifica se algum dos que estao associados tem maior prioridade
	        List<Integer> usuarios = new ArrayList<Integer>(); usuarios = s.getUsuariosAssociados(dispositivo,sensor);
	        for(int i=0; i<usuarios.size(); i++){
	          if(s.getPrioridade(usuarios.get(i))>prioridade){
		    aux=false;    
		  }
	        }
	        if(aux){
	          s.DarPerm(id,dispositivo,sensor,operacao,idPolitica);
	        }
		else{
		  System.out.println("Existe algum usuario com prioridade mais alta associado a operacao 3 deste dispositivo");
	          String[] intArray = {"-3"};
	          ri.setValores(intArray);
		}		
	      }
	    }
          }
	  //Em uso exclusivo, logo não conseguirá conceder a permissão
	  else{
	    String[] intArray = {"-3"};
	    ri.setValores(intArray);
	    return ri;
	  }
	}catch(Exception e){}

	System.gc();
	return ri;
   }


//Recebe um comando de operacao sobre um sensor, usuário já deve ter a permissão concedida
//formato = id, dispositivo, sensor, operacao
   @POST
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/comando")
   public Resposta create(Requisicao r){

	//Marca quando o comando comecou a ser processado
	long tempoInicial = System.nanoTime();

	//Identificador do comando recebido
	int ID_COMANDO=ObterId.getID_COMANDO();
	
	//Interface de comunicacao com o banco de dados
	Storage s;
	s = new Storage();
	//Resposta a ser retornada pela requisicao
	Resposta ri = new Resposta();

	System.out.println("\n\nCHEGOU O COMANDO DE NUMERO: " + ID_COMANDO + "\n\n");

	r.toString();
	String idComando=String.valueOf(ID_COMANDO);

	//Tratador de Requisicoes
	TratarRequisicao t  = new TratarRequisicao();
	t.setIdComando(idComando);

	//primeiro verifica se o id é valido
	try{
	  if(s.verificaIDvalido(r.getId())<=0){
	    System.out.println("\nID Não é Válido, Retornando -7 como resposta\n");
	    String[] intArray = {"-7"};
	    ri.setValores(intArray);
	    return ri;
	  }
	}
	catch(Exception e){
	}
	System.out.println("ID é válido");
	try{
	  //verifica se o usuario que esta realizando a requisicao sobre o sensor
	  //tem acesso exclusivo a este sensor

	  if(s.verifExc2(r.getId(), r.getDispositivo(), r.getSensor())>0){

	    //Insere no banco a requisicao pretendida
	    System.out.println("Dispositivo Tipo1, tem Exclusividade");
	    s.InsertReq(r.getId(),r.getDispositivo(), r.getSensor(),r.getOperacao());
	    ri = t.Tratar(r.getDispositivo(),r.getSensor(),r.getOperacao(),r.getComplemento());
	  }
	  //Caso não tenha acesso exclusivo, verifica se tem permissão comum para realizar aquela operacao
	  else{
	    //Verifica se o Usuario tem alguma funcao associada que possui essa permissao
	    List<Integer> funcoes = new ArrayList<Integer>(); funcoes = s.getFuncoes(r.getId());
	    for(int i=0; i<funcoes.size(); i++){
	      if(s.verifPermFunc(funcoes.get(i),r.getDispositivo(),r.getSensor(),r.getOperacao())>0){
		System.out.println("Funcao " + funcoes.get(i) + " permite que o usuário realize a operacao");  
	        //Insere no banco o comando pretendido
	        s.InsertReq(r.getId(),r.getDispositivo(), r.getSensor(),r.getOperacao());
	        ri = t.Tratar(r.getDispositivo(),r.getSensor(),r.getOperacao(),r.getComplemento());

		return ri; 
	      }
	    }

	    //Verifica se o usuário possui permissao e também se a permissao se mantém, podendo atualizar a arvore de
	    //delegacoes caso seja necessario, deletar a permissao, etc.
	    int permissao=s.verifPerm(r.getId(),r.getDispositivo(), r.getSensor(),r.getOperacao());
	    Atualizador at=new Atualizador();
	    if(permissao>0 /*&& at.verificarPermissao(permissao)*/){

	      System.out.println("Dispositivo Tipo1, Sem Exclusividade, Permissão Normal");

	      //Analise de Políticas
	      analisadorPoliticas ap = new analisadorPoliticas();
	      boolean resultado=true;
              try{
	        //Lista com todas as políticas dinâmicas do gateway
	        List<String> politicasDinamicas = new ArrayList<>();
		politicasDinamicas = s.getPoliticasDinamicas();
		//Busca a política estática associada a permissão
		System.out.println("OKOKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		int politicaEstatica=s.getIdPoliticaEstatica(permissao);
		System.out.println("Id Politica Estatica: " + politicaEstatica);
		//Busca a politica dinâmica associada a política estática, caso ela tenha a coluna CACHING=0
	        int politicaDinamica=s.getIdPoliticaDinamica(politicaEstatica);
		System.out.println("Id Politica Dinamica: " + politicaDinamica);

		//Verifica o tipo do dispositivo, se for 1, o comando deve ser enviado para que processe a parte
		//dinâmica da análise de contexto
		if(s.getTipoDispositivo(r.getDispositivo())==1){
		  System.out.println("Tipo 1 - Necessário Enviar o Comando para o dispositivo");
		  //Esse metodo irá colocar (idUsuario,comando,politicaDinamica) para ser enviada para o dispositivo
	      EnviarComandoDispositivo e = new EnviarComandoDispositivo();
		  ri=e.enviarComandoDispositivo(ID_COMANDO,r.getId(),r.getDispositivo(), r.getSensor(),r.getOperacao(),politicaDinamica);
		  //Marca o momento que o comando acaba de ser processado -> talvez tem q ficar um pouco acima
		  long tempoFinal = System.nanoTime();
		  String[] valores= ri.getValores();
		  double aux_double = (double)(tempoFinal-tempoInicial)/1000000000.0; 
	      valores[1]= Double.toString(aux_double);		  
	      ri.setValores(valores);
		  return ri;
		}
		else{
	          //Analise das políticas dinâmicas deve ser feito no proprio gateway
	          for(int i=0; i<politicasDinamicas.size(); i++){
		    //Analisa a política dinamica associada a politica estatica que concedeu permissao,
		    //ainda não trata o caso de varias politica dinamicas associadas, ou entao varias estaticas.
		    if((i+1)==politicaDinamica){
	              System.out.println("Processando Políticas");
	              int aux=0;
		      //CACHING DE POLITICAS
		      if(CACHING==true && s.selectCaching(politicaDinamica,r.getId())) aux=4;
                      else aux=ap.processarPolitica(politicasDinamicas.get(i),r.getId(),r.getDispositivo(), r.getOperacao(), ID_COMANDO);
		      //Politica Dinamica concedeu permissão, 
		      //deve acionar o sistema de caching por um intervalo de tempo T=60s, para o usuario em questao; 
	              if(aux==3 || aux==4){
			if(aux==3) System.out.println("Politica Dinamica Permitiu a Operacao!");
			if(aux==4) System.out.println("Politica Dinamica em Sistema de Caching Permitiu a Opercao!");
		        resultado=true;
   			Date date1 = new Date(); Date date2 = new Date();
			Calendar calendar = Calendar.getInstance(); calendar.setTime(date2); calendar.add(Calendar.SECOND, 60);
			date2=calendar.getTime();
			if(aux==3 && CACHING==true){
                          System.out.println("Inserindo Caching para Politica Dinamica " + politicaDinamica + " no intervalo " + date1 + " " + date2);
			  s.insertCaching(politicaDinamica,r.getId(),date1,date2);
			}
		      }
	              if(aux==0) resultado=false;
	            }
                  }
		}
	      }
              catch(Exception e){}

	      if(resultado==false){
	        System.out.println("Alguma política dinâmica não permite que realize a operação !");
	        String[] intArray = {"-3"};
	        ri.setValores(intArray);
	        return ri; 
	      }
	      //Insere no banco o comando pretendido
	      s.InsertReq(r.getId(),r.getDispositivo(), r.getSensor(),r.getOperacao());
	      ri = t.Tratar(r.getDispositivo(),r.getSensor(),r.getOperacao(),r.getComplemento());
	      String[] valores=ri.getValores();
	      System.out.println("Retornando o valor " + valores[0] + " vindo do dispositivo " + r.getDispositivo());

	      //Marca o momento que o comando acaba de ser processado
	      long tempoFinal = System.nanoTime();
	      double aux_double = (double)(tempoFinal-tempoInicial)/1000000000.0;
	      valores[1]=Double.toString(aux_double);
	      ri.setValores(valores);
	      return ri;
	    }
	    //Usuario não tem permissão para realizar o comando, returna -1 informando isso para ele
	    else{
	      System.out.println("Usuario não tem permissão para realizar o comando, retornando -1");
	      String[] intArray = {"-1"};
	      ri.setValores(intArray);
	      return ri;
	    }
          }
        }   
	catch(Exception e){

	}
	String[] intArray = {"-5"};
	ri.setValores(intArray);
	return ri;
   }


   //Método que divide a String S em vários floats
   int[] DividirString(String s){
     int[] resposta = new int[3];
     if(s.length()<=0){
       return resposta;
     }
     String[] a = s.split(" ");
     for(int i=0; i<a.length; i++){
       resposta[i]=Integer.parseInt(a[i]);
     }
     return resposta;
   }
  

//Criar uma nova função, passando o nome e as permissoes associadas
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/criarFuncao")
   public Response create(Funcao f){
	System.out.println(f.toString());
	Storage s = new Storage();	
	try{
	  int id_usuario=f.getId_usuario(), id_funcao=f.getId_funcao();
	  //Usuario deve ter prioridade de administrador para criar 
	  if(s.getPrioridade(id_usuario)>=3){
	    int[] dispositivos=DividirString(f.getComplemento()), sensores=DividirString(f.getSensores()), operacoes=DividirString(f.getOperacoes());
	    Boolean aux=true;
	    //verificação de permissao deve ser feita em tempo real, nesse momento elas somente sao colocadas na tabela functionsPermissions
	    for(int i=0; i<dispositivos.length; i++){
	      s.DarPermFunc(id_funcao,dispositivos[i],sensores[i],operacoes[i]);
	    }
	  }
	  else{
	    System.out.println("Usuario não tem prioridade para Criar Função");
	  }
	} 
	catch(Exception e){

	}  
	return Response.status(Response.Status.OK).build();
   }


//Permite a um usuario delegar, conceder suas permissões para algum outro
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/delegarPermissao")
   public Response create(concederPermissoes cp){
	Storage s = new Storage();
	//indica se todas as permissoes passadas pertencem realmente ao usuario1
	boolean consistencia=true;

	try{
	  //ids dos usuarios, aquele que vai conceder e o que vai receber
	  int id1 = cp.getId1(), id2 = cp.getId2(); String ss = cp.getPermissoes();
	  //ids das permissoes a serem delegadas do usuario 1 para o 2
	  List<Integer> permissoes = new ArrayList<Integer>();
          //verifica se o usuario 1 possui todas as permissoes que esta querendo passar	  
	  int[] aux=s.getPermissoes(id1);
	  String aux2[]=ss.split(" ");
	  for(int i=0;i<aux2.length; i++){
	    permissoes.add(Integer.parseInt(aux2[i]));
	  }
         
          System.out.print("permissoes que " + id1 + " possui: ");
	  for(int i=0; i<aux.length; i++){
	    System.out.print(aux[i] + " ");
	  }
	  System.out.println();

         System.out.print("permissoes a serem concedidas: ");
	  for(int i=0; i<permissoes.size(); i++){
	    System.out.print(permissoes.get(i) + " ");
	  }
	  System.out.println();

	  for(int i=0; i<permissoes.size(); i++){
            if(s.contains(aux, permissoes.get(i))==false){
	      consistencia=false; 
	      System.out.println("permissoes que o usuario nao possui foram passadas como parametro, nada sera concedido");
	    }
          }
	  if(consistencia){
	    for(int i=0; i<permissoes.size(); i++){
	      //s.DarPerm(id2,permissoes.get(i));
	      StorageDelegacaoRevogacao sp = new StorageDelegacaoRevogacao();
	      sp.delegarPermissao(permissoes.get(i),id1,id2);
	    }
	  } 
	} 
	catch(Exception e){

	}  
	return Response.status(Response.Status.OK).build();
   }


//Permite a um usuario delegar, conceder algum atributo não dinâmico para outro usuario
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/delegarAtributo")
   public Response create(delegarAtributo da){
	Storage s = new Storage();
	//indica se todas as permissoes passadas pertencem realmente ao usuario1
	boolean consistencia=true;
	try{
	  //ids dos usuarios, aquele que vai conceder e o que vai receber
	  int id1 = da.getId1(), id2 = da.getId2(); String atributo = da.getAtributo();
	  //verifica se o usuario id1 possui o atributo e um valor associado a ele
	  String valorAtributo=s.getValorAtributoUsuario(id1,atributo);
	  //caso tenha, concede o atributo para o usuario de id2
    	  if(valorAtributo.equals("")==false){
	    s.insertValorAtributoUsuario(id2,atributo,valorAtributo); 
	  }
	} 
	catch(Exception e){
	}  
	return Response.status(Response.Status.OK).build();
   }


//Alterar a permissao para um grupo de usuários
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/concederPermissoesGrupo")
   public Response create(PermissoesGrupo pg){
	Storage s = new Storage();	
	try{
	  //id do usuario que vai conceder permissoes
	  int id = pg.getId();
	  //grupo de usuarios que receberão a permissão
	  int[] grupo = pg.getGrupo();
	  //indica se todas as permissoes passadas pertencem realmente ao usuario1
	  boolean consistencia=true;
	  //permisses a serem concedidas a este grupo
	  int[] permissoes = pg.getPermissoes();


          //verifica se o usuario 1 possui todas as permissoes que esta querendo passar	  
	  int[] aux=s.getPermissoes(id);
	  for(int i=0; i<permissoes.length; i++){
            if(s.contains(aux, permissoes[i])==false){
	      consistencia=false; 
	      System.out.println("permissoes inexistentes foram passadas como parametro, nada sera concedido");
	    }
          }
	  if(consistencia){
	    for(int i=0; i<grupo.length; i++){
	      //concede a cada usuario do grupo as permissoes do vetor
	      for(int j=0; j<permissoes.length; j++){
	        s.DarPerm(grupo[i],permissoes[j]); 
	      }
            }
	  } 
	}catch(Exception e){
	}  
	return Response.status(Response.Status.OK).build();
   }


//Cria uma regra de contexto, a qual deve ser seguida por quem utilizar determinado dispositivo
//Regras devem ser verificadas quando for conceder permissão ou então quando for realizar o requisicao/comando
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/criarRegra")
   public Resposta create(RegraContexto rc){

	//Resposta Retornada pela Requisicao
	Resposta ri = new Resposta();
	Storage s;
	s = new Storage();
	try{
	    s.criarRegra(rc.getDispositivo1(),rc.getSensor1(),rc.getTipo(),rc.getDispositivo2(),rc.getSensor2(),rc.getComp(),rc.getValor());	  
	}catch(Exception e){

	}  
	return ri;
   }


//Adiciona um novo atributo, especificando a entidade a qual ele deve estar associado
//e também um nome para o atributo
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/novoAtributo")
   public Resposta create(novoAtributo na){

	//Resposta Retornada pela Requisicao
	Resposta ri = new Resposta();
	Storage s;
	s = new Storage();
	try{
	    s.insertAtributo(na.getEntidade(), na.getTipo(), na.getNome());
	    String pai=na.getPai();
	    
	}catch(Exception e){

	}  
	return ri;
   }

   @GET
   @Produces(MediaType.TEXT_PLAIN)//APPLICATION_JSON
   @Path("/atributos")
   public String mostraAtributos() {//Parametro via query	

       Storage s = new Storage();	

       List<String> atributos = new ArrayList<String>();
       try{
         atributos = s.getAtributos(0);
       }
       catch(Exception e){}

       String retorno="Atributos dos Usuarios: ";
       for(int i=0; i<atributos.size(); i++){
	 if(i!=atributos.size()-1) retorno+= atributos.get(i)+", ";
	  else retorno+= atributos.get(i)+" ";
       }	

       return retorno;
   }


    public void enviarPoliticaDispositivo(String politica) {

	System.out.println("Enviando Politica: " + politica);
	JSONObject json = new JSONObject(politica);
	int dispositivo = Integer.parseInt((json.get("dispositivo")).toString());
	Ip ii = new Ip();	
	String ip = ii.getIpDispositivo(dispositivo);		
	
        String mensagem = json.toString();

        String mensagem2="";
        for(int i=0; i<mensagem.length(); i++){
         if(mensagem.charAt(i)=='"'){
           mensagem2+="\\"; mensagem2+=mensagem.charAt(i);
         }
         else{
           mensagem2+=mensagem.charAt(i);
         }
        }

        mensagem2 = "{\"comando\":\""+mensagem2+"\"}";

	System.out.println("MENSAGEM A SER ENVIADA: " + mensagem2);
	
       
	try {
          HttpURLConnection connectManiot=null;

          try {
	    //int ID=getID_TESTE();
	    System.setProperty("http.keepAlive", "false");
            //System.out.println("\nVai enviar um comando para o maniot\n");
            URL url = new URL("http://"+ip+":8080/rest/politicas");
            connectManiot = (HttpURLConnection) url.openConnection();
            connectManiot.setDoInput(true);
            connectManiot.setDoOutput(true);
            connectManiot.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connectManiot.setRequestProperty("Accept", "application/json");
            connectManiot.setRequestMethod("POST");
            connectManiot.connect();

	    connectManiot.setReadTimeout(500000);

            DataOutputStream os = new DataOutputStream(connectManiot.getOutputStream());
            os.writeBytes(mensagem2);
            os.flush();
            os.close();

            //System.out.println("String enviada: " + s);
            int responseCode = connectManiot.getResponseCode();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connectManiot.getInputStream(), "utf-8"));
            StringBuilder resposta = new StringBuilder();
            String line = null;
            while ((line = rd.readLine()) != null) {
                resposta.append(line + "\n");
            }
            rd.close();

            String rs = resposta.toString();

            System.out.println("RESPOSTA APOS ENVIO DA POLITICA = " + rs);

          } catch (MalformedURLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } finally{
             if (connectManiot != null) {
                connectManiot.disconnect();
              }
	  }

	} catch (Exception e) {
		e.printStackTrace();
	}
    }


//Cria uma regra de contexto, a qual deve ser seguida por quem utilizar determinado dispositivo
//Regras devem ser verificadas quando for conceder permissão ou então quando for realizar o requisicao/comando
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/criarPolitica")
   public Resposta create(Politica cp){
        System.out.println(cp.toString());

	//Resposta Retornada pela Requisicao
	Resposta ri = new Resposta();
	//possui métodos relacionados a armazenar, processar, verificar politicas
	analisadorPoliticas ap = new analisadorPoliticas();
	//interface de comunicacao com o banco de dados
	Storage s;
	s = new Storage();

        String politica=cp.getPolitica();
	try{
          ap.armazenarPolitica2(politica);
	  //Verifica se existe alguma politica para enviar para os dispositivos
	  politica = s.getNovaPoliticaDinamica();
	  System.out.println("politica: " + politica);
	  while(!politica.equals("")){
	    System.out.println("Politica Nova para enviar !");
	    enviarPoliticaDispositivo(politica);
	    politica = s.getNovaPoliticaDinamica();
	  }  
	} 
	catch(Exception e){}  
	
        /*try{
          List<String> politicasEstaticas = new ArrayList<>();
	  politicasEstaticas = s.getPoliticasEstaticas();
	  for(int i=0; i<politicasEstaticas.size(); i++){
	    System.out.println("Processando Políticas");
            ap.processarPoliticas(politicasEstaticas.get(i),23829,1,1);
	  }
	  List<String> politicasDinamicas = new ArrayList<>();
	  politicasDinamicas = s.getPoliticasDinamicas();
	  for(int i=0; i<politicasDinamicas.size(); i++){
	    System.out.println("Processando Políticas");
            ap.processarPoliticas(politicasDinamicas.get(i),23829,1,1);
	  }
        }
        catch(Exception e){}*/

	System.gc();
        return ri;
   }


//Associa o usuario a uma funcao
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/assocFuncao")
   public Response create(UsuarioFuncao f){
	System.out.println(f.toString());
	Storage s;
	s = new Storage();
	try{
	  int usuario, funcao;
	  usuario=f.getId_usuario(); funcao=f.getId_funcao(); 
	  s.associaUsuarioFuncao(usuario,funcao);
	  //Aqui deve ser feita a verifiacao de que as permissoes associadas a funcao podem ser
	  //colocadas pro usuario em questao
	} 
	catch(Exception e){

	}  
	return Response.status(Response.Status.OK).build();
   }


//Receber uma lista com todas as funcoes disponiveis
   @GET
   @Path("/role")
   public String create(){
	String funcoes="";
	try{
	  Storage s = new Storage();
	  funcoes=s.getFuncoes();
	  return funcoes;
	} 
	catch(Exception e){

	}  
	return funcoes;
   }


//Receber uma lista com todos os dispositivos que esse usuario pode saber que existem no sistema
   @GET
   @Path("/{id}/dispositivos")
   public String getDevices(@PathParam("id") int id){
	String dispositivos="";
	try{
	  Storage s = new Storage();
	  dispositivos=s.getDevices();
	  return dispositivos;
	} 
	catch(Exception e){

	}  
	return dispositivos;
   }


//Receber uma lista com todas as permissões associadas a um dado usuario
   @GET
   @Path("/{id}/permissoes")
   public getPermissoes getPermissoes(@PathParam("id") int id){
	getPermissoes gp = new getPermissoes();
	int[] permissoes;

	try{
	  Storage s = new Storage();
	  String devices;
	  permissoes=s.getPermissoes(id);
	  gp.setPermissoes(permissoes);

	} 
	catch(Exception e){
	}

	return gp;  
   }


    @GET
    @Produces(MediaType.TEXT_PLAIN)//APPLICATION_JSON
    public String RequesAccess(@QueryParam("user") String user) {//Parametro via query	
        return "O usuario " + user + " recebeu o ID: 100";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("users/{id}")
    public String GetID(@PathParam("id") long id) {//Parametro via path
	return "O usuario de ID: " + id + " foi recuperado";
    }

/*

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/")
   public List<Chamado> listChamados(){

	Chamado c1 = new Chamado();
	c1.setID(1);
	c1.setAssunto("Assunto 1:");
	c1.setMensagem("Mensagem 1:");
	c1.setStatus(Status.NOVO);

	Chamado c2 = new Chamado();
	c2.setID(2);
	c2.setAssunto("Assunto: 2");
	c2.setMensagem("Mensagem: 2");
	c2.setStatus(Status.NOVO);

	Chamado c3 = new Chamado();
	c3.setID(3);
	c3.setAssunto("Assunto: 3");
	c3.setMensagem("Mensagem: 3");
	c3.setStatus(Status.FECHADO);

	List<Chamado> chamados = new ArrayList<>();
	chamados.add(c1);
	chamados.add(c2);
	chamados.add(c3);

	return chamados;
   }

*/

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("{id}/")
   public Chamado getChamado(@PathParam("id") long id){
	   Chamado c1 = new Chamado();
	   c1.setID(id);
	   c1.setAssunto("Assunto " + id);
	   c1.setMensagem("Mensagem " + id);
	   c1.setStatus(Status.NOVO);
	return c1;
   }
/*
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/")
   public Response create(Chamado chamado){
	   System.out.println(chamado.toString());
	return Response.status(Response.Status.OK).build();   
   }

   @PUT	
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/")
   public Response update(Chamado chamado){
	   return Response.status(Response.Status.OK).build();
   }
   @DELETE
   @Path("{id}/")
   public Response delete(@PathParam("id") long id){
	   System.out.println("Deletando ID: " + id);
	   return Response.status(Response.Status.OK).build();
   }

*/
}
