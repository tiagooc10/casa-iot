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

public class StorageCallback {	

	public StorageCallback(){
		System.out.println("CRIOU O STORAGE CALLBACK");
	}
	
	//Setar a resposta do Comando
	public static void setRespostaComando(String idComando, int resposta) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "insert into respostaComando(idComando,resposta) VALUES("+idComando+","+resposta+")";
		        System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'respostaComando'.");
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


	//Setar a resposta do Dispositivo auxiliar
	public static void insertRespostaDispositivoAuxiliar(String idComando, int resposta) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "insert into respostaDispositivoAuxiliar(idComando,resposta) VALUES("+idComando+","+resposta+")";
		        System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'respostaDispositivoAuxiliar'.");
			}

			 String query2 = "SELECT * FROM respostaDispositivoAuxiliar where idComando="+idComando;
         		 rs = stmt.executeQuery(query2);
          		 while (rs.next()) {
                		String a = rs.getString("idComando");
				int b= rs.getInt("resposta");
				System.out.println("LINHA DO BANCO: " + a + " " + b);
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


	public static int getCountComandos() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int size=-1;		
		try {
			//Mudar o nome da table para ComandosPAraEnviar
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM enviarComandoDispositivo";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  size= rs.getInt(1);
			}
			return size;

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

		return size;		
	}

	public static int getTamanhoPoliticasEstaticas() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int size=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM politicasEstaticas";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  size= rs.getInt(1);
			}
			return size;

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

		return size;		
	}

	public static int getTamanhoPoliticasDinamicas() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int size=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM politicasDinamicas";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  size= rs.getInt(1);
			}
			return size;

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

		return size;		
	}

	public static List<String> getPoliticasDinamicas() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		List<String> politicas = new ArrayList<>();		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM politicasDinamicas";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  String p= rs.getString("politica");
			  politicas.add(p);
			}
			return politicas;

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

		return politicas;		
	}

	public static List<Integer> getIdPoliticasDinamicas(int permissao) throws SQLException {
		List<Integer> ids = new ArrayList<Integer>();	
		String politicas="";

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
			
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT politicas FROM usersPermissions where idPermission="+permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  politicas= rs.getString("politicas");
			}
			
			String[] partes = politicas.split(" ");
			for(int i=0; i<partes.length; i++){
			  ids.add(Integer.parseInt(partes[i]));
			}

			return ids;

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

		return ids;		
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

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int aux=0;	
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

