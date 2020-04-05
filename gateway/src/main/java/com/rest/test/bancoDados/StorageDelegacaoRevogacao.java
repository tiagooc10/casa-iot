package com.rest.test;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.rest.data.Requisicao;
import java.lang.*;
import java.util.*;

import org.json.JSONObject;

import com.rest.data.RegraContexto;
import com.rest.test.PermissoesHierarquicas;

public class StorageDelegacaoRevogacao {	

	public StorageDelegacaoRevogacao(){
		System.out.println("CRIOU O StorageDelegacaoRevogacao");
	}

	public static int getIdPermissao(int entidade, String nome) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		int p1=-1;
		try {			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT id FROM usersPermissions where entidade=" + entidade + "nome='"+nome+"'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  p1=rs.getInt(1); 
			}
			return p1;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

		return p1;		
	}


	public static PermissoesHierarquicas getArvore() throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		PermissoesHierarquicas arvore = new PermissoesHierarquicas();
	
		int p1=-1;
		try {			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM usersPermissions";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  int id=rs.getInt("idPermission"); 
			  int pai=rs.getInt("pai");
			  System.out.println("idPermissao: " + id + " pai: " + pai);
			  if(pai==1)
			    arvore.adicionarPermissao(id);
			  else
			    arvore.adicionarPermissaoHerdandoDe(pai,id);
			}
			arvore.imprimir();
			return arvore;
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

		return arvore;		
	}



	public static List<Integer> getFilhos(int idPai) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		List<Integer> filhos = new LinkedList<Integer>();
	
		int p1=-1;
		try {			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT filho FROM arvorePermissaosFilhos WHERE idPai="+idPai;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  int filho=rs.getInt("filho"); 
			  filhos.add(filho);
			}
			return filhos;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

		return filhos;		
	}


	public static List<Integer> getTodosFilhos(int idPai) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;	

		List<Integer> TodosFilhos = new LinkedList<Integer>();
		List<Integer> aux = new LinkedList<Integer>();
		aux.add(idPai);
		int i=0;
		try {			
			while (i<aux.size()) {
			    int aux2=aux.get(i);
			    List<Integer> filhos=getFilhos(aux2); 
			    for(int k=0; k<filhos.size(); k++){
			        TodosFilhos.add(filhos.get(k));
			    }
			}
			return TodosFilhos;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

		return TodosFilhos;		
	}


	//Revoga uma determinada permissao,  de um usuário, apagando a permissão também as delegações que o dado usuário
	//realizou
	public static void revogarPermissao(int idPermissao) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {			
			List<Integer> TodosFilhos = new LinkedList<Integer>();
			TodosFilhos=getTodosFilhos(idPermissao);
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			int i=0;
			while (i<TodosFilhos.size()) {
			  int aux=TodosFilhos.get(i);
			  String query = "DELETE FROM usersPermissions where idPermission=" + aux;
			  stmt.executeUpdate(query);
			  query = "DELETE FROM arvorePermissoesFilhos where idPermission=" + aux;
			  stmt.executeUpdate(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
		
	}

	//idUsuario1 delegaou permissao idPermission para o ususario idUsuario2
	public static void delegarPermissao(int idPermissao, int idUsuario1, int idUsuario2) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int idDispositivo=0, idSensor=0, idOperation=0;	
		String politicas="";
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM usersPermissions where idPermission=" + idPermissao;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  idDispositivo= rs.getInt("idDispositivo");
			  idSensor=rs.getInt("idSensor");
			  idOperation=rs.getInt("idOperation");
			  politicas=rs.getString("politicas");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(idDispositivo + ", " + idSensor + ", " + idOperation);	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO usersPermissions(idDispositivo,idSensor,idOwner,idOperation,politicas,pai) VALUES ("+idDispositivo+","+idSensor+","+idUsuario2+","+idOperation+",'"+politicas+"',"+idPermissao+")";
			int update = stmt.executeUpdate(query);

			if(update==1) {
				System.out.println("Object was successfully added in table 'usersPermissions'.");
			}
			else{
				System.out.println("Insert do UsersPermission n deu certo.");
			}


			int idPermissaoFilho=-1;
			query = "SELECT * FROM usersPermissions where idDispositivo="+idDispositivo+" AND idSensor="+idSensor+
" AND idOwner="+idUsuario2+" AND idOperation="+idOperation+" AND pai="+idUsuario1;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  idPermissaoFilho=rs.getInt("idPermission");
			}


			query = "INSERT INTO arvorePermissoesFilhos(idPai,idFilho) VALUES("+idPermissao+","+idPermissaoFilho+")";
			System.out.println("query: " + query);
			update = stmt.executeUpdate(query);
			if(update==1) {
				System.out.println("Row was successfully updated in table 'arvorePermissaos'.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}	
	}


	//Insere Politica que possui somente Permissaos estáticos no banco de dados
	public static void updateArvore(String politica, String idsDinamicos) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicasEstaticas(politica,politicaDinamicaAssociada) VALUES('" +"')";
			System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'politicasEstaticas'.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	
	}


	public static ResultSet executeQuery(String query) {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println("error: failed to create a connection object.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("other error:");
			e.printStackTrace();
		}
		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

		return null;
	}
	
	public static boolean verifyForeignKey(String table, String field, int idKey) throws SQLException {

		ResultSet rs = null;

		try{
			String query = "SELECT " + field + " FROM " + table + " WHERE " + field + " = " + idKey;
			rs = executeQuery(query);
			if(!rs.next())
				return false;
			return true;
		}
		catch(Exception e){

		}
		finally{
			try { if (rs != null) rs.close(); } catch (Exception e) {};
		}
		return true;
	}


	public static boolean contains(int[] x, int y){
		for(int i=0; i<x.length; i++){
		  if(x[i]==y){
		    return true;
		  }
		}
		return false;
	}
}

