package com.rest.test;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.rest.data.Requisicao;
import java.lang.*;
import java.util.*;
import java.io.IOException;

import org.json.JSONObject;

import com.rest.data.RegraContexto;

public class StorageAtualizador {	

	public StorageAtualizador(){
		System.out.println("CRIOU O StorageAtualizador");
	}


	public static String getPoliticaEstatica(int idPolitica) throws SQLException {

		String p="";		
		ConnectMySQL instance=null;
		Statement stmt=null;
		Connection conn=null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT politica FROM politicasEstaticas where idPolitica=" + idPolitica;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  p= rs.getString("politica");
			  
			}
			return p;

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

		return p;		
	}



	public static int[] getPermissoes(int id_usuario) throws SQLException{
		List<Integer> permissoes = new ArrayList<Integer>();

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

		try {	
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idPermission FROM usersPermissions where idOwner = " + id_usuario;
			rs = stmt.executeQuery(query);
			while (rs.next()){
			  permissoes.add(rs.getInt(1)); 
			}
			int[] aux = new int[permissoes.size()];
			for(int i=0; i<permissoes.size(); i++){
			  aux[i]=permissoes.get(i);
			}
			return aux;

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

		int[] aux=new int[2];
		return aux;		
	}


	public static int getIdOwner(int permissao) throws SQLException{
		int id=0;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {	
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idOwner FROM usersPermissions where idPermission = " + permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()){
			  id=rs.getInt(1); 
			}

			return id;

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


		return id;		
	}

	public static int getIdDispositivo(int permissao) throws SQLException{
		int id=0;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {	
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idDispositivo FROM usersPermissions where idPermission = " + permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()){
			  id=rs.getInt(1); 
			}

			return id;

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

		return id;		
	}

	public static int getIdOperation(int permissao) throws SQLException{
		int id=0;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {	
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idOperation FROM usersPermissions where idPermission = " + permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()){
			  id=rs.getInt(1); 
			}
			return id;
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

		return id;		
	}


	public static List<Integer> getIdPoliticasEstaticas(int permissao) throws SQLException {
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

		return ids;		
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

		return ids;		
	}


	public static boolean permissaoFoiDelegada(int permissao) throws SQLException {

		int pai = getIdPoliticaEstatica(permissao);

		if(pai==0){
		  return false;
		}
		
		return true;
	}


	public static int getIdPoliticaEstatica(int permissao) throws SQLException {
		int id=-1;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT pai FROM usersPermissions where idPermission=" + permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id= rs.getInt(1);
			}
			return id;

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

		return id;		
	}



	public static int getIdPoliticaDinamica(int politicaEstatica) throws SQLException {
		int id=-1;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT politicaDinamicaAssociada FROM politicasEstaticas where idPolitica=" + politicaEstatica;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id= rs.getInt(1);
			}
			
			return id;		

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

		return id;		
	}



	public static int getIdUltimaPoliticaDinamica() throws SQLException {
		int id=-1;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM politicasDinamicas ORDER BY idPolitica DESC LIMIT 1";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id= rs.getInt("idPolitica");
			}
			return id;

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

		return id;		
	}



	public static int getIdPermissaoPai(int permissao) throws SQLException {
		int pai=0;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT pai FROM usersPermissions where idPermission="+permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  pai= rs.getInt(0);
			}
			return pai;

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

		return pai;		
	}


	//Deleta todas as permissoes a partir da permissaoRaiz, incluindo as delegacoes
	public static void deletarArvore(int permissaoRaiz) throws SQLException {
		List<Integer> deletar = new ArrayList<Integer>();
		deletar.add(permissaoRaiz);
		int i=0;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		while(i<deletar.size()){
		  permissaoRaiz=deletar.get(i);	
		  try {
			  instance=ConnectMySQL.getInstance();
			  conn=instance.getConnection();
		  	  stmt = conn.createStatement();
			  String query = "SELECT idPermission FROM usersPermissions where pai=" + permissaoRaiz;
			  rs = stmt.executeQuery(query);
			  int id=-1;
			  while (rs.next()) {
			    id= rs.getInt(1);
			    deletar.add(id);
		  	  }
			  query = "DELETE FROM usersPermissions where idPermission=" + permissaoRaiz;
			  stmt.executeUpdate(query);

		  } catch (SQLException e) {
			  e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		  i++;
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
		catch (Exception e) {
			e.printStackTrace();
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

