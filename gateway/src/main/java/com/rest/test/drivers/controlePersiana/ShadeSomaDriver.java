//===================================================================================================
//Shade control module
//Implements every possible control for the shades in Java
//Developer: guilhermejp4@gmail.com
//Created: 31/01/2018
//===================================================================================================

package com.rest.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShadeSomaDriver {	
    private String mac;
    private String scriptExecution;

    private String status="fechada";

    public ShadeSomaDriver(){
        mac = "D4:0C:1A:93:20:11";//Set shades mac address		
        scriptExecution = "python shade_soma.py -t";//Set default execution script
    }

    public int closeShade() {
	String command = "-c move_down";

	status = "fechada";
		
	try {
            Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
	    System.out.println("shade is being closed");
	    return 1;
	}
	catch(Exception ex) {
	    System.out.println("Unable to close shade");
	    return 0;
	}
    }

    public int openShade() {
        String command = "-c move_up";

        status = "aberta";
		
	try {
	    Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
	    System.out.println("shade is being opened");
	    return 1;
	}
	catch(Exception ex) {
	    System.out.println("Unable to open shade");
	    return 0;
        }
    }

    public int setShadePosition(int position) {
	String command = "-c move_target -a";
		
	if (position >= 0 && position <= 100) {
            try {
                String comm = scriptExecution + " " + mac + " " + command + " " + position;
                System.out.println("comm:" + comm);
		Process proc = Runtime.getRuntime().exec(comm);
		System.out.println("Shade moving.");		
		return 1;
            }
	    catch(Exception ex) {
	        System.out.println("Unable to move shade.");
		return 0;
	    }
	}
        else {
	    return 0;
	}
    }

    // ok = 1, problem during execution=0, server connection error=-1
    public int getShadePosition() {
        String command = "-c get_position";
	String s = null;
		
	try {
	    Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
	    BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    while ((s=br.readLine())!=null){
                String[] ss = s.split(" ");
                return Integer.parseInt(ss[1]);
	    }
	}
	catch(Exception ex) {
	    System.out.println("Unable to get position");
	    return 0;
	}
	return -1;
    }

    // ok = 1, problem during execution=0, server connection error=-1
	public int getShadeBattery(){
		String command = "-c get_battery";
		String s = null;
		
		try {
			Process proc = Runtime.getRuntime().exec(scriptExecution + " " + mac + " " + command);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((s=br.readLine())!=null){
                String[] ss = s.split(" ");
                return Integer.parseInt(ss[1]);
			}
		}
		catch(Exception ex) {
			System.out.println("Unable to get battery");
			return 0;
		}
		return -1;
	}

	public String getStatus(){
		return "aberta";
		
	}

    public void Test() {
        System.out.println("starting testing shade...");
        String command = "python test.py";
        System.out.println(getShadeBattery());
        System.out.println(getShadePosition());
        setShadePosition(0);
        System.out.println("test finished!");
    }
}

