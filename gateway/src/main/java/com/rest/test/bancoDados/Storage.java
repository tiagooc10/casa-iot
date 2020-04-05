package com.rest.test;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.rest.data.Requisicao;
import java.lang.*;
import java.util.*;
import java.util.concurrent.TimeUnit ;

import org.json.JSONObject;

import com.rest.data.RegraContexto;

public class Storage {	

	public Storage(){
		System.out.println("CRIOU O STORAGE");
	}
	
	public static int insertThing(String name, String location) {
		if(name == "" || name == null) {
			System.out.println("Error: Failed to insert object in table 'things'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO things (name, location) VALUES ('" + name + "', '" + location + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'things'.");
				return 1;
			}
				
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'things'.");
			if(name.length() > 50){
				System.out.println("       Data too long for column 'name'.");
			}
			else if(location.length() > 50){
				System.out.println("       Data too long for column 'location'.");
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'things'.");
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
		return 0;
	}
	

	public static int selectUser(String login, String password) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM users WHERE login='" + login + "' AND password='" + password +"'";

			System.out.println(query);
			rs = stmt.executeQuery(query);
			int size=0;
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  size=Integer.parseInt(rs.getString(1));
			}
                        if(size>=1){
			  System.out.println("Usuario cadastrado");
			  return 1;
			}
			else{
			  System.out.println("Usuario não cadastrado");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

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

		return -1;		
	}

       	public static int verificaIDvalido(int id_usuario) throws SQLException {
		System.out.println("VERIFICANDO SE ID É VÁLIDO");

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM users WHERE randomID=" + id_usuario;

			System.out.println(query);
			rs = stmt.executeQuery(query);
			int size=0;
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  size=Integer.parseInt(rs.getString(1));
			}

                        if(size>=1){
			  System.out.println("randomID cadastrado");
			  return 1;
			}
			else{
			  System.out.println("randomID não cadastrado");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to verify randomID in table 'users'.");

			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'things'.");
			e.printStackTrace();
		}

		finally{
			try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
			if(stmt!=null) stmt.close();
			if(rs!=null) rs.close();
		}
		return -1;		
	}


	public static int insertUserRandomID(String login, String password, int randomID) {
		if(login == "" || login == null) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			System.out.println("       Column 'login' cannot contain an empty set.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "UPDATE users SET randomID='" + randomID + "' WHERE login='" + login + "' AND password='" + password +"'";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'users'.");
				return 1;
			}
		} catch (SQLException e) {
		
			e.printStackTrace();

		}  catch (Exception e) {
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

		return 0;		
	}
	
	public static int selectUserRandomID(String login, String password) {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT randomID from users WHERE login='" + login + "' AND password='" + password +"'";
			rs = stmt.executeQuery(query);
                       // iterate through the java resultset
                       while (rs.next()){
                         if(rs.getInt("randomID")>0){
			   return rs.getInt("randomID");
                         }
                         return -1;
                       }
		} catch (SQLException e) {
		
			e.printStackTrace();

		}  catch (Exception e) {
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

		return 0;		
	}

	public static int insertUser(String name, String login, String password) {
		if(name == "" || name == null) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}
		if(login == "" || login == null) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			System.out.println("       Column 'login' cannot contain an empty set.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO users (name, login, password) VALUES ('" + name + "', '" + login + "', '" + password + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'users'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			if(name.length() > 50){
				System.out.println("       Data too long for column 'name'.");
			}
			else if(login.length() > 20){
				System.out.println("       Data too long for column 'login'.");
			}
			else if(password.length() > 20){
				System.out.println("       Data too long for column 'password'.");
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'users'.");
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

		return 0;		
	}
	
	public static int insertAplication(String name, String description) {
		if(name == "" || name == null) {
			System.out.println("Error: Failed to insert object in table 'aplications'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO aplications (name, description) VALUES ('" + name + "', '" + description + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'aplications'.");
				return 1;
			}
				
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'aplications'.");
			if(name.length() > 20){
				System.out.println("       Data too long for column 'name'.");
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'aplications'.");
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

		return 0;	
	}
	
	public static int insertResource(int idThing, String name, int idAdm) throws SQLException {	
		if(!Storage.verifyForeignKey("things", "idThing", idThing)) {
			System.out.println("Error: Failed to insert object in table 'resource'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(name == "") {	
			System.out.println("Error: Failed to insert object in table 'resource'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;


		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO resources (idThing, name, idAdm) VALUES ('" + idThing + "', '" + name + "', '" + idAdm + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'resources'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'resources'.");

			if(name.length() > 50){
				System.out.println("       Data too long for column 'name'.");		
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'resources'.");
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

		return 0;		
	}
	
	
	public static int insertPermission(int idResource, int idAplication, int idUser, String permission) throws SQLException {
		if(!Storage.verifyForeignKey("resource", "idResource", idResource)) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("aplications", "idAplication", idAplication)) {	
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("users", "idUser", idUser)) {	
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;


		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO permissions (idResource, idAplication, idUser, permission) "
						+ "VALUES ('" + idResource + "', '" + idAplication + "', '" + idUser + "', '" + permission + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'permissions'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			if(permission.length() > 3){
				System.out.println("       Data too long for column 'permissions'.");
			}
			else
				e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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

		return 0;		
	}
	
	public static int insertData(int idResource, String value) throws SQLException {
		if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to insert object in table 'data'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		// Get current date and time
		Date date = new Date();
        // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);


		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO data (idResource, value, time) " + "VALUES (" + idResource + ", '" + value + "', '" + datetime + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				//System.out.println("Object was successfully added in table 'data'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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

		return 0;		
	}

	public static int selectData(int idResource) throws SQLException {
		/*if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to select object in table 'data'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}*/
		// Get current date and time
		Date date = new Date();
                // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM data WHERE idResource=" + idResource + " AND time >= DATE_SUB(now(), INTERVAL 20 SECOND)";
			//String query = "SELECT * FROM data WHERE idResource=" + idResource;
			rs = stmt.executeQuery(query);
      
                       // iterate through the java resultset
                       while (rs.next()){
                         String v = rs.getString("value");
                         return Integer.parseInt(v);
                       }

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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

		return -1;		
	}


	public static String selectDataPresenca(int idResource) throws SQLException {
		/*if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to select object in table 'data'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}*/
		// Get current date and time
		Date date = new Date();
                // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM data WHERE idResource=" + idResource + " AND time >= DATE_SUB(now(), INTERVAL 20 SECOND)";
			//String query = "SELECT * FROM data WHERE idResource=" + idResource;
			rs = stmt.executeQuery(query);
      
                       // iterate through the java resultset
                       while (rs.next()){
                         String v = rs.getString("value");
                         return v;
                       }

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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

		return "";		
	}

	
	public static int selectDataWemo(int sensor) throws SQLException {
	
		Date date = new Date();
                // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			//String query = "SELECT * FROM data WHERE idResource=" + idResource + " AND time >= DATE_SUB(now(), INTERVAL 20 SECOND)";
			String query = "SELECT * FROM dataWemo WHERE idSensor=" + sensor;
			rs = stmt.executeQuery(query);
      
                       // iterate through the java resultset
                       while (rs.next()){
                         int v = rs.getInt("valor");
                         return v;
                       }

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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


		return -1;		
	}

	public static int selectDataIris(int sensor) throws SQLException {
	
		Date date = new Date();
                // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			//String query = "SELECT * FROM data WHERE idResource=" + idResource + " AND time >= DATE_SUB(now(), INTERVAL 20 SECOND)";
			String query = "SELECT * FROM dataIris WHERE idSensor=" + sensor;
			rs = stmt.executeQuery(query);
      
                       // iterate through the java resultset
                       while (rs.next()){
                         String v = rs.getString("valor");
                         return Integer.parseInt(v);
                       }

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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

		return -1;		
	}

	public static int selectDataArCond(int sensor) throws SQLException {
	
		Date date = new Date();
                // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			//String query = "SELECT * FROM data WHERE idResource=" + idResource + " AND time >= DATE_SUB(now(), INTERVAL 20 SECOND)";
			String query = "SELECT * FROM dataIris WHERE idSensor=" + sensor;
			rs = stmt.executeQuery(query);
      
                       // iterate through the java resultset
                       while (rs.next()){
                         String v = rs.getString("valor");
                         return Integer.parseInt(v);
                       }

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
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

		return -1;		
	}



	public static int insertScheduling(int idThing, int idResource, int idUser, int action, String newValue, int date, int time, int count, String others) throws SQLException {
		if(!Storage.verifyForeignKey("things", "idThing", idThing)) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("users", "idUser", idUser)) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO scheduling (idThing, idResource, idUser, action, newValue, date, time, count, others) "
						+ "VALUES (" + idThing + ", " + idResource + ", " + idUser + ", " + action + ", '" + newValue + "', "
						+ date + ", " + time +  ", " + count + ", '" + others + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'schduling'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
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

		return 0;		
	}



	//Verifica se o sensor esta sobre uso de alguem
	//retornando 1 caso nao esteja e 0 caso esteja sendo utilizado
	public static int verifUso(int id, int id_dispositivo, int id_sensor) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			//Id do sensor esta presenta na tabela usersPermissions com idOperation=0 somente se ele esta sendo utilizado por alguem
			//abaixo se verifica se tem alguem utilizando

			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM usersPermissions WHERE idDispositivo=" + id_dispositivo + " AND idSensor=" + id_sensor;

			System.out.println(query);
			rs = stmt.executeQuery(query);
			int size=0;
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  size=Integer.parseInt(rs.getString(1));
			}
                        if(size==0){
			  System.out.println("Ninguem utilizando sensor, conceder permissao");
			  return 1;
			}
			else{
			  System.out.println("Sensor em uso por outro usuario");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

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

		return -1;		
	}



	//Verifica se o sensor esta sobre uso exclusivo de alguem
	//retornando 1 caso nao esteja e 0 caso esteja sendo utilizado
	public static int verifExc(int id_dispositivo, int id_sensor) throws SQLException {
		
		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			//Id do sensor esta presenta na tabela usersPermissions somente se ele esta sendo utilizado por alguem
			//abaixo se verifica se tem alguem utilizando

			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM usersPermissions WHERE idDispositivo=" + id_dispositivo + " AND idSensor=" + id_sensor + " AND idOperation=0";

			System.out.println(query);
			rs = stmt.executeQuery(query);
			int size=0;
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  size=Integer.parseInt(rs.getString(1));
			}
                        if(size==0){
			  System.out.println("Ninguem utilizando senso exclusivamente");
			  return 1;
			}
			else{
			  System.out.println("Sensor utilizado exclusivamente por outro usuario");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

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

		return -1;		
	}


	//Verifica se o usuario tem permissao para realizar uma operacao,
	//retornando 1 caso afirmativo e 0 caso contrário
	public static int verifPerm(int id, int id_dispositivo, int id_sensor, int id_operacao) throws SQLException {
		int id_permissao=0;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			//Id do sensor esta presenta na tabela usersPermissions somente se ele esta sendo utilizado por alguem
			//abaixo se verifica se tem alguem utilizando

			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idPermission FROM usersPermissions WHERE idDispositivo=" + id_dispositivo + " AND idSensor=" + id_sensor + " AND idOwner=" + id + " AND idOperation=" + id_operacao;

			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  id_permissao=Integer.parseInt(rs.getString(1));
			}
                        if(id_permissao>=1){
			  System.out.println("Usuario tem permissão (" + id_permissao + ") para realizar essa operação");
			  return id_permissao;
			}
			else{
			  System.out.println("Usuario não tem permissão para realizar essa operação");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to select object from table 'usersPermissions'.");

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

		return -1;		
	}

	public static int DarPerm(int id_usuario, int id_dispositivo, int id_sensor, int operacao, String idPolitica) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		//verifica se o usuario nao tem a permissao
		if(verifPerm(id_usuario,id_dispositivo,id_sensor,operacao)==0){
			try {
				instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
				String query = "INSERT INTO usersPermissions (idDispositivo, idSensor, idOwner, idOperation, politicas) " + "VALUES ('" + id_dispositivo + "', '" + id_sensor + "', '" + id_usuario + "','" + operacao + "','" + idPolitica + "')";
				int update = stmt.executeUpdate(query);
				if(update == 1) {
					System.out.println("Row was successfully updated in table 'usersPermissions'.");
					return 1;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  catch (Exception e) {
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
		return 0;	
	}


	//Verifica se a funcao tem alguma permissao para realizar uma operacao,
	//retornando 1 caso afirmativo e 0 caso contrário
	public static int verifPermFunc(int funcao, int dispositivo, int sensor, int operacao) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {

			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM functionsPermissions WHERE idDispositivo=" + dispositivo + " AND idSensor=" + sensor + " AND idFunction=" + funcao + " AND idOperation=" + operacao;

			System.out.println(query);
			rs = stmt.executeQuery(query);
			int size=0;
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  size=Integer.parseInt(rs.getString(1));
			}
                        if(size>=1){
			  System.out.println("Funcao tem permissão para realizar essa operação");
			  return 1;
			}
			else{
			  System.out.println("Funcao não tem permissão para realizar essa operação");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

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

		return -1;		
	}

	public static int DarPermFunc(int id_usuario, int id_dispositivo, int id_sensor, int operacao) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO functionsPermissions (idDispositivo, idSensor, idFunction, idOperation) " + "VALUES ('" + id_dispositivo + "', '" + id_sensor + "', '" + id_usuario + "','" + operacao + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'functionsPermissions'.");
				return 1;
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

		return 0;		
	}


// Outra versao com outro construtor, conceder permissao com base no ID da permissao

	public static int DarPerm(int id_usuario, int id_permissao) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {

			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "CREATE TEMPORARY TABLE tmp select idDispositivo,idSensor,idOwner,idOperation from usersPermissions where idPermission= " + id_permissao;
			String query2 = "UPDATE tmp set idOwner=" + id_usuario;
			String query3 = "INSERT INTO usersPermissions (idDispositivo, idSensor, idOwner, idOperation) select idDispositivo, idSensor, idOwner, idOperation from tmp";
			String query4 = "DROP table tmp";

			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);

			int update = stmt.executeUpdate(query3);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'usersPermissions'.");
				stmt.executeUpdate(query4);
				return 1;
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

		return 0;		
	}



	public static int DarExc(int id_usuario, int id_dispositivo, int id_sensor) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO usersPermissions (idDispositivo, idSensor, idOwner, idOperation) " + "VALUES ('" + id_dispositivo + "', '" + id_sensor + "', '" + id_usuario + "', '" + "0" + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'usersPermissions'.");
				return 1;
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

		return 0;		
	}

	//Verifica se o sensor está sobre uso exclusivo de algum usuário especifico de ID = id
	//Exclusividade é determinada pelo 0 no idPermission
	public static int verifExc2(int id, int id_dispositivo, int id_sensor) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			//Id do sensor esta presenta na tabela usersPermissions somente se ele esta sendo utilizado por alguem
			//abaixo se verifica se tem alguem utilizando

			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM usersPermissions WHERE idDispositivo=" + id_dispositivo + " AND idSensor=" + id_sensor + " AND idOwner=" + id + " AND idOperation=0";

			System.out.println(query);
			rs = stmt.executeQuery(query);
			int size=0;
			while (rs.next()) {
    			  System.out.println(rs.getString(1)); //gets the first column's rows.
			  size=Integer.parseInt(rs.getString(1));
			}
                        if(size>0){
			  System.out.println("Sensor está associado ao usuario corretamente");
			  return 1;
			}
			else{
			  System.out.println("Usuario nao tem exclusividade sobre o uso do sensor");
			  return 0;
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		}  catch (Exception e) {
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

		return -1;		
	}

	public static int InsertReq(int id, int id_dispositivo, int id_sensor, int id_operation) throws SQLException {
		
		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO req (idOwner, idDispositivo, idSensor, idOperation) " + "VALUES (" + id + ", " + id_dispositivo + ", " + id_sensor + ", " + id_operation + ")";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'req'.");
				return 1;
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

		return 0;		
	}


	public static Requisicao SelectRequisicao(int id_req) throws SQLException {
		Requisicao rq=new Requisicao();
		rq.setId(-1);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM req where idReq > " + id_req + " LIMIT 1";
			rs = stmt.executeQuery(query);
			int id=0,dispositivo=0,sensor=0,operacao=0;
			while (rs.next()) {
			  id=rs.getInt(2); dispositivo=rs.getInt(3); sensor=rs.getInt(4); operacao=rs.getInt(5);
			  rq.setId(id);
			  rq.setDispositivo(dispositivo);
			  rq.setSensor(sensor);
			  rq.setOperacao(operacao);
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

		return rq;		
	}


	public static List<Integer> getUsuariosAssociados(int dispositivo, int sensor) throws SQLException{
		List<Integer> usuarios = new ArrayList<Integer>();

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {	
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idOwner FROM usersPermissions where idDispositivo = " + dispositivo + " AND idSensor = " + sensor;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  usuarios.add(rs.getInt(1)); 
			}
			return usuarios;

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

		return usuarios;		
	}


	public static int[] getPermissoes(int id_usuario) throws SQLException{
		List<Integer> permissoes = new ArrayList<Integer>();

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
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

		int[] aux=new int[2];
		return aux;		
	}


	public static int getPrioridade(int id) throws SQLException {		
		int p1=-1;

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT priority FROM users where randomID=" + id;
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

	public static void insertFuncao(int id_funcao, String nome) throws SQLException {
		int id=-1;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT name FROM funcoes";
			rs = stmt.executeQuery(query);

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

	public static String getFuncoes() throws SQLException {
		String funcoes="";		
		int id=-1;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT name FROM funcoes";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  funcoes += rs.getString(1) + " "; 
			}
			return funcoes;

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

		return funcoes;		
	}

	public static List<Integer> getFuncoes(int usuario) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

	        List<Integer> funcoes = new ArrayList<Integer>();		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idFunction FROM usersFunctions WHERE idUser = " + usuario;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  funcoes.add(rs.getInt("idFunction")); 
			}
			return funcoes;

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

		return funcoes;		
	}

	public static void associaUsuarioFuncao(int usuario, int funcao) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
		
		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO usersFunctions(idUser,idFunction) VALUES(" + usuario + ", " + funcao + ")";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'usersFunctions'.");
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

	//Insere Atributo
	public static void insertAtributo(int entidade, int tipo, String nome) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO atributos(idUser,tipo,nome) VALUES(" + entidade + ", " + tipo + ", " + nome + ")";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'atributos'.");
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
	public static void insertPolitica(String politica) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicas(politica) VALUES('" + politica + "')";

			System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'politicas'.");
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
	public static void insertPoliticaEstatica(String politica, String idsDinamicos, int dispositivo) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicasEstaticas(politica,politicaDinamicaAssociada,dispositivo) VALUES('" + politica + "','"+
				idsDinamicos+"',"+dispositivo+")";

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

	//Insere Politica que possui atributo dinâmico no banco de dados
	public static void insertPoliticaDinamica(String politica, String idsEstaticos) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicasDinamicas(politica,politicaEstaticaAssociada) VALUES('" + politica + "','"+ idsEstaticos+ "')";

			System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'politicasDinamicas'.");
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

	//Insere Politica que possui atributo dinâmico na tabela auxiliar, para enviar para os dispositivos
	public static void insertPoliticaDinamicaEnviar(int idPolitica,String politica, int dispositivo, String idsEstaticos) throws SQLException {


		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		JSONObject json = new JSONObject();
		json.put("id", idPolitica);
		json.put("politica",politica);
		json.put("dispositivo",dispositivo);
		json.put("politicaEstaticaAssociada",idsEstaticos);

		String message = json.toString();			
	
				
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO politicasDinamicasEnviar(politica) VALUES('" + message + "')";

			System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'politicasDinamicas'.");
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

        //Insere o Comando na tabela auxiliar, para enviar para os dispositivos
	public static void enviarComandoDispositivo(int usuario, int dispositivo, int sensor, int comando, int politicaDinamica) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		JSONObject json = new JSONObject();
		json.put("usuario", usuario);
		json.put("dispositivo", dispositivo);
		json.put("sensor", sensor);
		json.put("comando", comando);
		json.put("politicaDinamica", politicaDinamica);

	        

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


	public static int getTipoDispositivo(int dispositivo) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT type FROM things where idThing=" + dispositivo;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id = rs.getInt(1); 
			}
			return id;

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

		return id;		

	}

	public static int getTipoDispositivo(String dispositivo) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT type FROM things where name=" + dispositivo;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id = rs.getInt(1); 
			}
			return id;

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

		return id;		

	}


	public static List<Integer> getDispositivosLimitadosPorExpressao(String atributo, String comparador, String valor) throws SQLException{	
		System.out.println("TT1");

		if(comparador.equals("==")) comparador="=";

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		List<Integer> dispositivos = new ArrayList<Integer>();

		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idThing from things,valoresAtributosDispositivos WHERE idThing=dispositivo AND atributo='" + atributo + "' AND valor" + comparador + "'"+valor+"'";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			   dispositivos.add(rs.getInt(1)); 
			}
			return dispositivos;

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

		return dispositivos;		
	}



	public static int getTipoAtributo(String nome) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT tipo FROM atributos where nome='" + nome + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id = rs.getInt(1); 
			}
			return id;

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

		return id;		

	}


	public static List<String> getAtributos(int entidade) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		List<String> atributos = new ArrayList<String>();		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT nome FROM atributos where entidade='" + entidade + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  atributos.add(rs.getString(1)); 
			}
			return atributos;

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

		return atributos;		

	}



	public static String getValorAtributoUsuario(int usuario, String atributo) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		String valor="";		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT valor FROM valoresAtributosUsuarios where usuario=" + usuario + " AND atributo='" + atributo + "'";
			System.out.println("query: " + query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  valor = rs.getString(1); 
			}
			return valor;

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

		return valor;		

	}

	public static void insertValorAtributoUsuario(int usuario, String atributo, String valor) throws SQLException {	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO valoresAtributosUsuarios(usuario,atributo,valor) VALUES("+usuario+",'" + atributo + "','"+valor+"')";

			System.out.println("query: " + query);
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'valoresAtributosUsuarios'.");
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

	public static String getValorAtributoDispositivo(int dispositivo, String atributo) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		String valor="";		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT valor FROM valoresAtributosDispositivos where dispositivo=" + dispositivo + " AND atributo='" +
				atributo + "'";
			System.out.println("query: " + query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  valor = rs.getString(1); 
			}
			return valor;

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

		return valor;		
	}


	public static String getDevices() throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		String devices="";		
		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT name FROM things";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  devices += rs.getString(1); 
			}
			return devices;

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

		return devices;		
	}

	public static int getIDUser(int id_dispositivo, int id_sensor) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int id=-1;		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idOwner FROM usersPermissions where idDispositivo=" + id_dispositivo + " AND idSensor=" + id_sensor;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id=rs.getInt(1); 
			}

			return id;

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

		return id;		
	}


	public static void substituirUsuario(int id_dispositivo, int id_sensor, int id_usuario) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "UPDATE usersPermissions SET idOwner=" + id_usuario + " WHERE idSensor=" + id_sensor + " AND idDispositivo=" + id_dispositivo;
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'usersPermissions'.");
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


	public static int criarRegra(int idDispositivo1, int idSensor1, int tipo, int idDispositivo2, int idSensor2, String comp, String valor) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "INSERT INTO contextRules (idDispositivo1, idSensor1, idTipo, idDispositivo2, idSensor2, comp, valor) " + "VALUES (" + idDispositivo1 + ", " + idSensor1 + ", " + tipo + ", " + idDispositivo2 + ", " + idSensor2 + ", '" + comp + "', '" + valor + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Row was successfully updated in table 'contextRules'.");
				return 1;
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

		return 0;		
	}



	public static List<RegraContexto> getRegras(int idDispositivo, int idSensor) throws SQLException {
		List<RegraContexto> regras = new ArrayList<>();	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM contextRules where idDispositivo1=" + idDispositivo + " AND idSensor1=" + idSensor;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  RegraContexto r = new RegraContexto();
			  r.setTipo(rs.getInt("idTipo"));
			  r.setDispositivo1(rs.getInt("idDispositivo1"));
			  r.setSensor1(rs.getInt("idSensor1"));
			  r.setDispositivo2(rs.getInt("idDispositivo2"));
			  r.setSensor2(rs.getInt("idSensor2"));
			  r.setComp(rs.getString("comp"));
			  r.setValor(rs.getString("valor"));
			  regras.add(r);
			}
			return regras;

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

		return regras;		
	}

	public static Map<Integer,String> getPoliticasEstaticas(int dispositivo) throws SQLException {

		Map<Integer,String> m = new HashMap<Integer,String>();		

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
		
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM politicasEstaticas where dispositivo="+dispositivo;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  Integer id=rs.getInt("idPolitica");
			  String p= rs.getString("politica");
			  m.put(id,p);
			}
			return m;

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

		return m;		
	}

	public static int getIdPoliticaEstatica(String politica) throws SQLException {
		int id=-1;	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT idPolitica FROM politicasEstaticas where politica='" + politica + "'";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id= rs.getInt(1);
			}
			return id;

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

		return id;		
	}

	public static int getIdPoliticaEstatica(int permissao) throws SQLException {
		int id=-1;	

		System.out.println("PERMISSÂO:" + permissao);

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT politicas FROM usersPermissions where idPermission=" + permissao;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id= Integer.parseInt(rs.getString(1).trim());
			}
			return id;

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

		return id;		
	}


	//Verifica se uma nova politica foi inserida em "politicas", caso tenha sido retorna a politica
	//caso contrario retorna uma String vazia
	public static String getNovaPoliticaDinamica() throws SQLException {
		int id=-1;	
		String politica="";

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT * FROM politicasDinamicasEnviar";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  id=rs.getInt(1);
			  politica= rs.getString("politica");
			}
			query = "DELETE FROM politicasDinamicasEnviar where idPolitica=" + id;
			stmt.executeUpdate(query);
		 	
			return politica;

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

		return politica;		
	}


	//Verifica se um novo comando foi inserido em "enviarComandosDisposito", caso tenha sido retorna o comando
	//caso contrario retorna uma String vazia
	public static String getNovoComando() throws SQLException {
		int id=-1;	
		String comando="";	

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

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

	//Buscar a resposta do Comando
	public static int getRespostaComando(String idComando) throws SQLException {

	   int respostaFINAL=-15;

	   ConnectMySQL instance = null;
	   Statement stmt=null;
	   Connection conn=null;
	   ResultSet rs=null;
	   String query="";

	   int aux3=0;

	   try{

	       instance=ConnectMySQL.getInstance();
	       conn=instance.getConnection();

	       while(respostaFINAL==-15 && aux3<4000){
	           aux3++;
	           int resposta=-15;	
	           try {
		       int aux=0;
			
		       stmt = conn.createStatement();
		       query = "SELECT resposta FROM respostaComando where idComando='"+idComando+"'";
		       rs = stmt.executeQuery(query);
		       while (rs.next()) {
		           resposta=rs.getInt("resposta"); aux=1;
		       }
		       if(aux==1){
		           query = "DELETE FROM respostaComando where idComando='"+idComando+"'";
			   stmt.executeUpdate(query);			
		       }
		       respostaFINAL = resposta;

		   } catch (SQLException e) {
			e.printStackTrace();
		   }
	       	   respostaFINAL = resposta;
	           TimeUnit.MILLISECONDS.sleep(200);
	       }	
	   }	
	   catch(Exception e){
	       
	   }
	   finally{
	       if(rs!=null)rs.close();
	       if(stmt!=null) stmt.close();
	       try {
            conn.close();
            
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            
        }
	       query=null;
	   }
	

	   return respostaFINAL;	
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
			String query = "insert into respostaComando(idComando,resposta) VALUES('"+idComando+"',"+resposta+")";
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
			String query = "insert into respostaDispositivoAuxiliar(idComando,resposta) VALUES('"+idComando+"',"+resposta+")";
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
		System.out.println("TT1");
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


	public static void deletarPermissoes() throws SQLException {

		System.out.println("DELETANDO PERMISSOES ARMAZENADAS");

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;
	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "DELETE FROM  usersPermissions";
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


	public static int getReqDiminuir1PorCentoBateriaDispositivo(int dispositivo) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int aux=0;	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT requisicoes FROM ReqDiminuir1PorCentoBateriaDispositivo where dispositivo="+dispositivo;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  aux= rs.getInt(1);
			}
			return aux;

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

		return aux;		
	}

	//ADICIONAR O TEMPO, PARA QUE SEJA NO ULTIMO MINUTO
	//OU ENTAO FAZER UMA MEDIA COM O HISTORICO RECENTE, PRA VER QUANTAS DA POR MINUTO
	public static int getReqPorMinuto(int id_app, int dispositivo) throws SQLException {

		ConnectMySQL instance = null;
		Statement stmt=null;
		Connection conn = null;
		ResultSet rs=null;

		int aux=0;	
		try {
			instance=ConnectMySQL.getInstance();
			conn=instance.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) FROM req where dispositivo="+dispositivo+" AND id="+id_app;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
			  aux= rs.getInt(1);
			}
			return aux;

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

		return aux;		
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

