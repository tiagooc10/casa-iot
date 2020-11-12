package com.rest.test;

// Developed by: Guillermo Ponce e Thiago Oliveira 
// This module is in charge to verify the presence of the user inside a room. For that it is used wifi 
// connectivity between the server (machine where is running this module) and the 'Internet-Connected-Device', 
// such as a tablet or smartphone.
// The module also stores the user activity in the system database, stores importants events in a log file, 
// controls the 'Wemo Switch' (turns_on, turn_off).
// All configurable properties are defined in the configuration file named as 'PresenceModule.properties' 


// Network connection
import java.net.*;
import java.net.InetAddress;

import java.util.*;
import java.util.Date;

// Logging
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import java.io.*;

// Configuration file
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

// Sql
import java.sql.ResultSet;
import java.sql.SQLException;

// Text
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Other
import java.lang.Math ;

public class PresenceModule { 

    String addr="", logFileName="";
    int timeOut=2000, delayTime=2000, completedPowerOFFWindows=2, powerONWindow=3, powerOFFWindow=3;
    File configFile = new File("PresenceModule.properties");
    Logger logger = null;

    public class Presence extends Thread implements Runnable {
        public void run() {

            loadConfigurationData();
            configureLogger();

            // Prepare counter and auxiliar vector
            int powerOFFCounter = 0;
            int lastConnections[] = new int[powerOFFWindow];

            // Inicialize the vector verifying connectivity to poweron or poweroff the light 
            System.out.println("Connecting "+addr+" ...");
            for (int i=0; i < lastConnections.length ; i++){
                lastConnections[i] = scanAddresses(addr, timeOut, delayTime);
                System.out.print(lastConnections[i]+ " ");

                if (countConnections(lastConnections) >= powerONWindow) {
                    try{
                        powerOFFCounter = 0;
                        //s.insertData(20,"INSIDE");	
                        //System.out.print("-power ON- ");
                        //logger.info("-power ON-");
                    }
                    catch(Exception e){}
                }
            } 

            System.out.println(" ... ");
            if(validateAllConnectionsFalse(lastConnections)){ 
              powerOFFCounter++;  
            }

            // After initial verification, following with infinite loop 

            while (true) {
                // Update vector
                int lastScan = scanAddresses(addr, timeOut, delayTime);
                for (int i = 0; i < powerOFFWindow-1; i++){
                    lastConnections[i] = lastConnections[i+1]; 
                    System.out.print(lastConnections[i]+" ");
                } 
                lastConnections[powerOFFWindow-1] = lastScan;
                System.out.println(lastConnections[powerOFFWindow-1]+" ... ");


		//Verifica se completou as 5 verdadeiras
		//mostrando que a pessoa está no ambiente
                if (countConnections(lastConnections) >= powerONWindow) {
                    try{
                        powerOFFCounter = 0;
                        //s.insertData(20,"INSIDE");	
                        System.out.println("-power ON- ");
                        //logger.info("-power ON-");
                    }
                    catch(Exception e){}
                }           		
               
                boolean areAllConnectionsFalse = validateAllConnectionsFalse(lastConnections);
                // Look if has to turn off the light
                if (areAllConnectionsFalse){
                    powerOFFCounter++;
                }
                else{
                    powerOFFCounter = 0 ;
                }

		//Verifica se completou as 5 falsas duas vezes
		//mostrando que a pessoa não está presente
                if (powerOFFCounter == completedPowerOFFWindows){
                    try{
                        powerOFFCounter = 0;
                        //s.insertData(20, "OUTSIDE");
                        //logger.info("-power OFF-");
                    }
                    catch(Exception e){}
                }
            } 
        }

        // Try to reach a device since the server network by using a ping command
        public int scanAddresses(String addressDevice, int timeOut, int delayTime){
            int wasFound = 0;
            try {
                if (InetAddress.getByName(addressDevice).isReachable(timeOut))
                    wasFound = 1;
                else
                    wasFound = 0;

                delay(delayTime);	      
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            return wasFound;
        }

        public void delay(int time){
            try {
                Thread.sleep(time);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public int countConnections(int[] connections){
    	    int counter = 0;
    	    for(int i = 0; i < connections.length; i++) {
    	      if(connections[i] == 1) counter++;
    	    }    
    	    return counter;
        }


        // Confirm if all the connections of a vector are false
        public boolean validateAllConnectionsFalse(int[] connections){
    	    for(int i = 0; i < connections.length; i++) {
    	       if(connections[i]==1) return false;
    	    }
    	    return true;
        }
        
        public double average(int[] data){  
            int sum = 0;
            for (int d : data) sum += d;
            return (double) sum / data.length;
        }

        public void loadConfigurationData() {
            try {
                FileReader reader = new FileReader(configFile);
                Properties prop = new Properties();
                prop.load(reader);

                // Allocate initial values to variables
                //addr = prop.getProperty("USER_PHONE_IP");
                timeOut = Integer.parseInt(prop.getProperty("PHONE_TIME_OUT"));
                delayTime = Integer.parseInt(prop.getProperty("PHONE_DELAY_TIME"));
                completedPowerOFFWindows = Integer.parseInt(prop.getProperty("COMPLETED_POWER_OFF_WINDOWS"));
                powerONWindow = Integer.parseInt(prop.getProperty("POWER_ON_WINDOW"));
                powerOFFWindow = Integer.parseInt(prop.getProperty("POWER_OFF_WINDOW"));
                logFileName = prop.getProperty("LOG_FILENAME");
               
                reader.close();
            } 
            catch (FileNotFoundException ex) { // file does not exist 
            } 
            catch (IOException ex) { // I/O error
            }
        }

        public void configureLogger(){
            logger = Logger.getLogger(logFileName);
            SimpleFormatter formatter = null;
            FileHandler fileHandler = null;
            try{
                fileHandler = new FileHandler(logFileName+".log", true);  
                formatter = new SimpleFormatter();
                logger.addHandler(fileHandler);  
                fileHandler.setFormatter(formatter);   
            }catch(IOException exception){}
        }

    }

    // Instance the class PresenceModule, initializing the threads: 1) Presence thread
    public PresenceModule(String ip) {

	//o endereco buscado será o do ip passado como parâmetro
	addr = ip;
   
        try{
            Presence p = new Presence();
            Thread t1 = new Thread(p);
            t1.start();
        }
        catch(Exception e){}
    }
	
}
