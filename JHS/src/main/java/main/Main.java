package main;

import dbService.DBServiceImplement;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MainServlet;
import servlets.ServletDeleteUser;
import servlets.ServletGetAllUsers;
import servlets.SessionServlets;


public class Main {
    public static void main(String[] args) throws Exception{
        DBServiceImplement dbServiceImplement = new DBServiceImplement();

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new MainServlet(dbServiceImplement)), "/");
        servletContextHandler.addServlet(new ServletHolder(new SessionServlets(dbServiceImplement)), "/api/v1/sessions");
        servletContextHandler.addServlet(new ServletHolder(new ServletDeleteUser(dbServiceImplement)), "/api/v1/sessionsDelete");
        servletContextHandler.addServlet(new ServletHolder(new ServletGetAllUsers(dbServiceImplement)), "/api/v1/selectAll");

//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase("frontendHtml");

//        HandlerList handlerList = new HandlerList();
//        handlerList.setHandlers(new Handler[]{resourceHandler, servletContextHandler});

        Server server = new Server(8000);
        server.setHandler(servletContextHandler);

        server.start();
        server.join();
    }
}