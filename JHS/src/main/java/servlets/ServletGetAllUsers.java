package servlets;

import dbService.DBServiceImplement;
import dbService.dataSets.UsersDataSets;
import pageGenerator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ServletGetAllUsers extends HttpServlet {
    private String contentType = "text/html;charset=utf-8";
    private final DBServiceImplement dbServiceImplement;

    public ServletGetAllUsers(DBServiceImplement dbServiceImplement) {
        this.dbServiceImplement = dbServiceImplement;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVars = new HashMap<String, Object>();
        List<UsersDataSets> allUsers = new ArrayList<UsersDataSets>();
        try {
            allUsers = dbServiceImplement.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<String>();

        for (UsersDataSets date : allUsers) {
            list.add("Name: " + date.getUser_names() + " : " + "Password: " +  date.getPassword());
        }

        pageVars.put("myOwnList", list);
        pageVars.put("name", "User name not selected");
        pageVars.put("password", "Password not entered");
        System.out.println(pageVars);

        response.setContentType(contentType);
        try {
            response.getWriter().println(PageGenerator.getInstance().getPage("index.html", pageVars));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}