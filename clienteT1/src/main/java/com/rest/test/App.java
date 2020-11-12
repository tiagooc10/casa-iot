package com.rest.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.util.thread.*;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.TimeUnit;
import java.net.InetSocketAddress;
import java.net.URI;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.rest.test.analiseContexto.analisadorPoliticas;



public class App {

    public App(){

    }

    public static void main(String[] args) throws Exception {

        int CACHING=1;
        CACHING=Integer.parseInt(args[0]);

	analisadorPoliticas ap = new analisadorPoliticas();

	//ap.processarPolitica("Se(sensorSmartThings.temperatura<30ANDsensorSmartThings.temperatura<30ANDsensorSmartThings.temperatura<30), Permitir em (11), Acoes(getStatus)",9420703,11,3,100);
	//ap.processarPolitica("Se(usuario.idade<10ANDusuario.idade<30ANDusuario.idade<30), Permitir em (11), Acoes(getStatus)",9420703,11,3,100);

	//ap.armazenarPolitica2("Se sensorSmartThings.temperatura<30&(teste3.status==0|teste2.status==0) permitir em Objeto.nome=teste1 operacoes getStatus");

        myParser m = new myParser();
        //String t = "Se a&(b&(c|(d&h)))&(e|f) permitir em b|(c&d) operacoes a1,a2,a3";
	//String u = "Se a&(e|f) permitir em b|(c&d) operacoes a1,a2,a3";
        //m.parseStringExp1(u);

	//ap.armazenarPolitica2("Se usuário.localizacao==ufmg|(usuário.bb<10|(usuário.cc<10&usuário.dd<10))|(usuário.ee<10&usuário.ff<10) permitir em Objeto.localizacao==2103&Objeto.bateria>50 operacoes a1,a2,a3");

        //ap.processarExpressao2("Objeto.localizacao==2103&Objeto.bateria>50");

	Storage s = new Storage();

  	Thread t1 = new Thread(new Runnable() {
    	public void run(){
	    try{
	        Caching ca = new Caching();
	        TimeUnit.MILLISECONDS.sleep(5000);
	    }
	    catch(Exception e){}
   	 }});  
    	t1.start();

        int[] ids = {1,2,3,4,5,6};
        int[] prioridades = {2,20,100,4,200,7};
        int[] reqPorMinuto = {2,20,100,4,200,7};


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(200);


	LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10000);
	//ExecutorThreadPool pool = new ExecutorThreadPool(minThreads, maxThreads, maxIdleTime, TimeUnit.MILLISECONDS, queue);
	ExecutorThreadPool pool = new ExecutorThreadPool(20, 200, 300, TimeUnit.MILLISECONDS, queue);

        Server jettyServer = new Server(threadPool);

	ServerConnector http = new ServerConnector(jettyServer, new HttpConnectionFactory());
	http.setPort(8080);
	http.setIdleTimeout(3000000);

	jettyServer.addConnector(http);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        if(CACHING==1){
          jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",RestClienteT1ComCaching.class.getCanonicalName());
	}
	else{
	  jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",RestClienteT1SemCaching.class.getCanonicalName());
	}

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.stop();
            jettyServer.destroy();
        }
    }
}
