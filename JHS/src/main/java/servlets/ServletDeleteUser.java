package servlets;

import dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDeleteUser extends HttpServlet {
    private String contentType = "text/html;charset=utf-8";
    private final DBService dbService;

    public ServletDeleteUser(DBService dbService) {
        this.dbService = dbService;
    }

    public void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        if (name == null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        dbService.deleteUser(name);
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
