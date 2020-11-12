package com.rest.test;


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.*;
import java.util.*;

import org.json.JSONObject;

public class StorageCliente {

    public StorageCliente(){

    }

    //Insere Politica que possui somente atributos estáticos no banco de dados
    public static void insertPolitica(int idPolitica, String politica) throws SQLException {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

        try {
	    instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
            String query = "INSERT INTO politicas(idPolitica, politica) VALUES(" + idPolitica + ", '" + politica + "')";

            System.out.println("query: " + query);
            int update = stmt.executeUpdate(query);
            if(update == 1) {
                System.out.println("Row was successfully updated in table 'politicasEstaticas'.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

    }

    //Buscar todas as políticas no banco de dados
    public static List<String> getPoliticas() throws SQLException {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

        List<String> politicas = new ArrayList<>();
        try {
            instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM politicas";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String p= rs.getString("politica");
                politicas.add(p);
            }
            return politicas;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
        return politicas;
    }


    //Buscar todas as políticas no banco de dados
    public static String getPolitica(int idPolitica) throws SQLException {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

        String politica ="";
        try {
            instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM politicas where idPolitica="+idPolitica;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                politica= rs.getString("politica");
            }
            return politica;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
        return politica;
    }



    //Verifica se uma nova politica foi inserida em "politicas", caso tenha sido retorna a politica
    //caso contrario retorna uma String vazia
    public static String getNovaPoliticaDinamica() throws SQLException {

	ConnectMySQL instance=null;
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;


        int id=-1;
        String politica="";
        try {
            instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM politicasDinamicasEnviar";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                id=rs.getInt(1);
                politica= rs.getString(1);
            }
            query = "DELETE FROM politicasDinamicasEnviar where idPolitica=" + id;
            stmt.executeUpdate(query);

            return politica;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
        return politica;
    }


    public static void setRespostaDispositivoAuxiliar(String idComando, int resposta) throws SQLException {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

        try {
            instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
            String query = "INSERT INTO respostaDispositivoAuxiliar(idComando, resposta) VALUES(" + idComando + ", '" + resposta + "')";

            System.out.println("query: " + query);
            int update = stmt.executeUpdate(query);
            if(update == 1) {
                System.out.println("Row was successfully updated in table 'respostaDispositivoAuxiliar'.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}

    }


public static int getRespostaDispositivoAuxiliar(String idComando) throws SQLException {
     
	ConnectMySQL instance = null;
	Statement stmt=null;
	Connection conn = null;
	ResultSet rs=null;

	int respostaFINAL=-14;
	int aux3=0;

	try{
	    instance = ConnectMySQL.getInstance();
            conn=instance.getConnection();

	    while(respostaFINAL==-14 && aux3<4000){
		aux3++;
                int resposta=-14;
                try {
	            int aux=0;
	            stmt = conn.createStatement();
                    String query = "SELECT * FROM respostaDispositivoAuxiliar where idComando='"+idComando+"'";
                    rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        resposta= rs.getInt("resposta"); aux=1;
                    }
	            if(aux==1){
                        query = "DELETE FROM respostaDispositivoAuxiliar where idComando='"+idComando+"'";
                        stmt.executeUpdate(query);
	            }
                    respostaFINAL = resposta;

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	catch(Exception e){

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

        return respostaFINAL;
    }



    //Verifica se um novo comando foi inserido em "enviarComandosDisposito", caso tenha sido retorna o comando
    //caso contrario retorna uma String vazia
    public static String getNovoComando() throws SQLException {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

        int id=-1;
        String comando="";
        try {
	    int aux=0;
            instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM enviarComandoDispositivo";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                id=rs.getInt(1);
                comando= rs.getString("comando");
		aux=1;
            }
	    if(aux==1){
                query = "DELETE FROM enviarComandoDispositivo where idComando="+id;
                stmt.executeUpdate(query);
	    }
            return comando;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
        return comando;
    }


    public static void insertCaching(int idPolitica, int idUsuario, Date inicio, Date termino) throws SQLException {


		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

        int aux=0;	

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String datetime1 = dateFormat.format(inicio);
	String datetime2 = dateFormat.format(termino);

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
	} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}	
     }


     public static boolean selectCaching(int idPolitica, int idUsuario) throws SQLException {


		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
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
	} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}
	return false;		
    }

	public static void updateCaching() throws SQLException {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
	    conn=instance.getConnection();
            stmt = conn.createStatement();
			String query = "DELETE FROM  caching where now()>termino";
			stmt.executeUpdate(query);
		

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
			try { if (rs != null) rs.close(); } catch (Exception e) {};
   			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		}	
	}


    public static ResultSet executeQuery(String query) {

		ConnectMySQL instance=null;
		Connection conn=null;
		Statement stmt=null;
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
			try { if (conn != null) instance.closeConnection(conn); } catch (Exception e) {};
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
