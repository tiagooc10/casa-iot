package com.rest.test;

import java.util.concurrent.TimeUnit;



public class Caching{

    StorageCaching s = new StorageCaching();
    

    public Caching(){

        while(true){
	    try{
                s.updateCaching(); 
		TimeUnit.MILLISECONDS.sleep(5000);
	    }
	    catch(Exception e){}        
	}

    }


}
