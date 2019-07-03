package servlets;

import com.google.gson.Gson;
import dbService.DBServiceImplement;
import dbService.dataSets.UsersDataSets;
import pageGenerator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SessionServlets extends HttpServlet {
    private String contentType = "text/html;charset=utf-8";
    private final DBServiceImplement dbServiceImplement;

    public SessionServlets(DBServiceImplement dbServiceImplement) {
        this.dbServiceImplement = dbServiceImplement;
    }
    // ADD USER AND UPDATE USER
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVars = new HashMap<String, Object>();
        pageVars.put("name", "User name not selected");
        pageVars.put("password", "Password not entered");
        pageVars.put("myOwnList", Arrays.asList("Not Selected","1"));
        String name = request.getParameter("name");
        if (name == null) {
            name = "NONE";
        }
        String password = request.getParameter("password");
        if (password == null) {
            password = "NONE";
        }
        if (name == null | password == null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long id = dbServiceImplement.getUserId(name);

        if (id != 0) {
            dbServiceImplement.updateUser(name, password);
            response.setContentType(contentType);
            try {
                response.getWriter().println(PageGenerator.getInstance().getPage("index.html", pageVars));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            dbServiceImplement.addUser(name,password);
            response.setContentType(contentType);
            try {
                response.getWriter().println(PageGenerator.getInstance().getPage("index.html", pageVars));
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    public void doGet(HttpServletRequest request,
                 HttpServletResponse response) throws IOException, ServletException {
        Map<String, Object> pageVars = new HashMap<String, Object>();
        String name = request.getParameter("name");
        Gson json = new Gson();

        if (name == null) {
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String pass;
        try {
            UsersDataSets usersDataSet = dbServiceImplement.getUser(dbServiceImplement.getUserId(name));
            pass = usersDataSet.getPassword();
        } catch (Exception e) {
            pass = "Not exists";
            name = "Name " + name + " not exists in database !";
        }

        pageVars.put("name", name);
        pageVars.put("password", "Password: " + pass);
        pageVars.put("myOwnList", Arrays.asList("Not Selected"));
        UsersDataSets dataSets = dbServiceImplement.getUser(dbServiceImplement.getUserId(name));
        response.setContentType(contentType);
        try {
            response.getWriter().println(PageGenerator.getInstance().getPage("index.html", pageVars));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}