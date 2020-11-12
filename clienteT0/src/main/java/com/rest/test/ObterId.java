package com.rest.test;

import java.util.concurrent.atomic.AtomicInteger;

public class ObterId{

	private static AtomicInteger ID_COMANDO = new AtomicInteger(0);

	private static AtomicInteger ID_AUXILIAR = new AtomicInteger(0);

	//private static int ID_COMANDO=0;

	//private static int ID_AUXILIAR=0;

	public static synchronized int getID_COMANDO(){
	        //ID_COMANDO++;
		//return ID_COMANDO;
		return ID_COMANDO.incrementAndGet();
	}

	public static synchronized int getID_AUXILIAR(){
	        //ID_AUXILIAR++;
		//return ID_AUXILIAR;
		return ID_AUXILIAR.incrementAndGet();
	}

}
