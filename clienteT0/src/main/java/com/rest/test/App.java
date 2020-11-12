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


public class App {

    public App(){

    }

    public static void main(String[] args) throws Exception {

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

  
	jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",RestClienteT0SemCaching.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.stop();
            jettyServer.destroy();
        }
    }
}
