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

public class StorageCaching {	

	public StorageCaching(){

	}
	
        //Insere o Comando na tabela auxiliar, para enviar para os dispositivos
	public static void enviarComandoDispositivo(int usuario, int dispositivo, int sensor, int comando, int politicaDinamica) throws SQLException {

		JSONObject json = new JSONObject();
		json.put("usuario", usuario);
		json.put("dispositivo", dispositivo);
		json.put("sensor", sensor);
		json.put("comando", comando);
		json.put("politicaDinamica", politicaDinamica);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;	        

		String message = json.toString();			
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO enviarComandoDispositivo(comando) VALUES('" + message + "')";
			System.out.println(query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'enviarComandoDispositivo'.");
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



	public static void insertCaching(int idPolitica, int idUsuario, Date inicio, Date termino) throws SQLException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime1 = dateFormat.format(inicio);
		String datetime2 = dateFormat.format(termino);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int aux=0;	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO caching VALUES ("+idPolitica+","+idUsuario+",'"+datetime1+"','"+datetime2+"')";
			int update = stmt.executeUpdate(query);

			if(update == 1) {
				System.out.println("Object was successfully added in table 'caching'.");
			}
			else{
				System.out.println("Insert do Caching nao deu certo.");
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


	public static boolean selectCaching(int idPolitica, int idUsuario) throws SQLException {
		int aux=0;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM  caching where idPolitica="+idPolitica+" AND idUsuario=" + idUsuario;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  aux= rs.getInt(1);
			}
			
			if(aux>0){
			  return true;
			}
			return false;

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

		return false;		
	}


	public static void updateCaching() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "DELETE FROM  caching where now()>termino";
			stmt.executeUpdate(query);
		

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

