package main;

import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ServletDeleteUser;
import servlets.SessionServlets;


public class Main {
    public static void main(String[] args) throws Exception{
        DBService dbService = new DBService();


        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new SessionServlets(dbService)), "/api/v1/sessions");
        servletContextHandler.addServlet(new ServletHolder(new ServletDeleteUser(dbService)), "/api/v1/sessionsDelete");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("frontendHtml");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, servletContextHandler});

        Server server = new Server(8000);
        server.setHandler(handlerList);

        server.start();
        server.join();
    }
}