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
import com.rest.test.AtributosHierarquicos;

public class StorageArvoreAtributos {	

	public StorageArvoreAtributos(){

		System.out.println("CRIOU O StorageArvoreAtributos");

	}

	public static int getIdAtributo(int entidade, String nome) throws SQLException {		
		int p1=-1;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT id FROM atributos where entidade=" + entidade + "nome='"+nome+"'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  p1=rs.getInt(1); 
			}
			return p1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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


	public static String getNomeAtributo(int id) throws SQLException {		
		String p1="";

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT nome FROM atributos where id=" + id;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  p1=rs.getString(1); 
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

	public static AtributosHierarquicos getArvore() throws SQLException {	
	
		AtributosHierarquicos arvore = new AtributosHierarquicos();

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		int p1=-1;
		try {			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM atributos";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  int id=rs.getInt("id"); 
			  int pai=rs.getInt("pai");
			  System.out.println("idAtributo: " + id + " pai: " + pai);
			  System.out.println("idAtributo: " + getNomeAtributo(id) + " pai: " + getNomeAtributo(pai));
			  if(pai==1)
			    arvore.adicionarAtributo(getNomeAtributo(id));
			  else
			    arvore.adicionarAtributoHerdandoDe(getNomeAtributo(pai),getNomeAtributo(id));
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


	//Insere Politica que possui somente atributos estáticos no banco de dados
	public static void insertArvore() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicasEstaticas(politica,politicaDinamicaAssociada) VALUES('" + "')";
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

	//Inserir atributo
	public static void insertAtributoArvore() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO arvoreAtributos(id,pai) VALUES('" + "')";
			System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'arvoreAtributos'.");
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

	//Insere Politica que possui somente atributos estáticos no banco de dados
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

	
	//Insere Politica que possui somente atributos estáticos no banco de dados
	public static void insertPoliticaEstatica(String politica, String idsDinamicos) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicasEstaticas(politica,politicaDinamicaAssociada) VALUES('" + politica + "','"+
				idsDinamicos+"')";
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


	//Verifica se um novo comando foi inserido em "enviarComandosDisposito", caso tenha sido retorna o comando
	//caso contrario retorna uma String vazia
	public static String getNovoComando() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int id=-1;	
		String comando="";	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM enviarComandoDispositivo";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id=rs.getInt(1);
			  comando= rs.getString("comando");
			}

			query = "DELETE FROM enviarComandoDispositivo where idComando="+id;
			stmt.executeUpdate(query);			

			return comando;

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

		return comando;		
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

