package servlets;

import dbService.DBService;
import dbService.dataSets.UsersDataSets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionServlets extends HttpServlet {
    private String contentType = "text/html;charset=utf-8";
    private final DBService dbService;

    public SessionServlets(DBService dbService) {
        this.dbService = dbService;
    }
    // ADD USER AND UPDATE USER
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (name == null | password == null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long id = dbService.getUserId(name);

        if (id != 0) {
            dbService.updateUser(name);
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            dbService.addUser(name,password);
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    public void doGet(HttpServletRequest request,
                 HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");

        if (name == null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSets dataSets = dbService.getUser(dbService.getUserId(name));
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
