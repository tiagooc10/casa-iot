package com.rest.test;

//import com.mashape.unirest.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.json.JSONObject;


public class AirCond{


    public static  List<String> login() throws UnirestException{
		// HttpResponse<String> response = Unirest.post("http://150.164.10.75:8080/api/test")
		HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/login")
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cache-control", "no-cache")
				  .field("username", "USERNAMETIAGO")
				  .field("password", "SENHATIAGO")
				  .asString();
		//System.out.println(response.getHeaders());
		//System.out.println(response.getHeaders().get("Set-Cookie").getClass().getName());
		return response.getHeaders().get("Set-Cookie");
    }
	

    public static List <String> getDevice(int id) throws UnirestException {
		JSONObject device;
		List<String> params = new ArrayList<String>();
		
		HttpResponse<JsonNode> response = Unirest.get("https://painel.dcc.ufmg.br/midea/detail/" + Integer.toString(id))
				  .header("accept", "application/json, text/plain, */*")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", "session=.eJwlzs1ugzAQBOBXqfacQzHhgpSbSQXSLiIysdaXKCFEscE98KMER3n30vY6I30zLzjdhna8QzoNc7uBk71C-oKPC6SA4epZ3S3r6mlcLUhzxK6wqDHhcOgo5LGRhTWSY1Tdwp4fpTz2pHEhmT9ZUP-bG5fH5DGw3_eoq3h1_nvXJOQLt9qfFJoteVr3ssDh2KPMEqPwUaomIrl6X7UoVZ2U2lh0nUCx74zKIhL1QiLbwXsD89gOf_9hmQf7be00zv4M7x8c6EyD.DPiMAA.mPVHlsFOK9PP0VA8LGsaki5qRZg")
				  .header("cache-control", "no-cache")
				  .asJson();
		
		device = (JSONObject) response.getBody().getObject().get("device");
		params.add((String) device.get("estado"));
		params.add(device.get("set_temp").toString());
		params.add((String) device.get("swing").toString());
		params.add((String) device.get("fan"));
		return params;
		
	}
	
    public static void setDevice(int id, String Status,String Temp, String Swing) throws UnirestException {
		HttpResponse<String> response = Unirest.post("https://painel.dcc.ufmg.br/midea/edit/" + Integer.toString(id))
				  .header("origin", "https://painel.dcc.ufmg.br")
				  .header("upgrade-insecure-requests", "1")
				  .header("content-type", "application/x-www-form-urlencoded")
				  .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
				  .header("x-devtools-emulate-network-conditions-client-id", "dfc781d4-4de5-4a54-a230-12b081f0187d")
				  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
				  .header("referer", "https://painel.dcc.ufmg.br/midea/edit/197")
				  .header("accept-encoding", "gzip, deflate, br")
				  .header("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
				  .header("cookie", login().get(0))
				  //.header("cookie", "session=.eJwlzs1ugzAQBOBXqfacQzHhgpSbSQXSLiIysdaXKCFEscE98KMER3n30vY6I30zLzjdhna8QzoNc7uBk71C-oKPC6SA4epZ3S3r6mlcLUhzxK6wqDHhcOgo5LGRhTWSY1Tdwp4fpTz2pHEhmT9ZUP-bG5fH5DGw3_eoq3h1_nvXJOQLt9qfFJoteVr3ssDh2KPMEqPwUaomIrl6X7UoVZ2U2lh0nUCx74zKIhL1QiLbwXsD89gOf_9hmQf7be00zv4M7x8c6EyD.DPiU2w.hbdtn74FKMRxrvULDEAmGwEYPGE")
				  //.header("cookie", "session=.eRwlzs1ugzAQBOBXqfacQzHhgpSbSQXSLiIysdaXKCFEscE98KMER3n30vY6I30zLzjdhna8QzoNc7uBk71C-oKPC6SA4epZ3S3r6mlcLUhzxK6wqDHhcOgo5LGRhTWSY1Tdwp4fpTz2pHEhmT9ZUP-bG5fH5DGw3_eoq3h1_nvXJOQLt9qfFJoteVr3ssDh2KPMEqPwUaomIrl6X7UoVZ2U2lh0nUCx74zKIhL1QiLbwXsD89gOf_9hmQf7be00zv4M7x8c6EyD.DPiU2w.hbdtn74FKMRxrvULDEAmGwEYPGE")
				  .header("cache-control", "no-cache")
				  .field("ligado", Status) // on - off
				  .field("temp", Temp) // 17 ~ 26
				  //.field("fan", Fan) //number:0 = high - number:1 = medium - number:2 = low - number:3 = auto
				  .field("swing", Swing) //on - off
				  .asString();
    }
	
	
    public static void list() {

    }

    //Liga o Ar condicionado
    public void setOn(int sensor){
        
      try{
        setDevice(197,"on","20","on");
      }
      catch(Exception e){}
 
    }

    //Desliga o Ar Condicionado
    public void setOff(int sensor){
      try{
        setDevice(197,"off","20","on");
      }
      catch(Exception e){}

    }

    //Altera a temperatura dO Ar Condicioando
    public void setTemperatura(int sensor, int temperatura){

      try{
        setDevice(197,"on",Integer.toString(temperatura),"on");
      }
      catch(Exception e){}

    }
	
    /*public static void main( String[] args ) throws Exception{
    	List<String> params = new ArrayList<String>();
    	//System.out.println(App.login().get(0));
    	App.setDevice(197,"off","20","on");
    	//params = App.getDevice(197);
    	//System.out.println(params.get(0));
    	//System.out.println(params.get(1));
    	//System.out.println(params.get(2));
    	//System.out.println(params.get(3));
    }*/
}
