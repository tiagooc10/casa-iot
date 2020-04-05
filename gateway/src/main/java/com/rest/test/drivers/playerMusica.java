package com.rest.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;


public class playerMusica{

    public void play(){
        mandarComando("1");
    }
    public void stop(){
        mandarComando("2");
    }
    public void next(){
        mandarComando("3");
    }
    public void prev(){
        mandarComando("4");
    }

    public void mandarComando(String comando){
        try {
            Socket client_socket = new Socket("150.164.10.77", 8950);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));

            writer.write(comando + "\n");
            writer.flush();
            System.out.println("comando enviado para o player");
        }
        catch (Exception e){
            
        }
    }

    public boolean verificaLigado(){
        try {
            Socket client_socket = new Socket("150.164.10.77", 8950);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));

            writer.write("5" + "\n");
            writer.flush();
            //System.out.println("comando enviado");

	    String response = reader.readLine();
            if(response.contains("ligado")){
              return true;
            }
            client_socket.close();
	    
        }
        catch (Exception e){
            
        }
	return false;
    }

}
