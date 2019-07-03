package servlets;

import dbService.DBServiceImplement;
import pageGenerator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private String contentType = "text/html;charset=utf-8";
    private final DBServiceImplement dbServiceImplement;


    public MainServlet(DBServiceImplement dbServiceImplement) {
        this.dbServiceImplement = dbServiceImplement;
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {

        Map<String, Object> pageVars = new HashMap<String, Object>();
        pageVars.put("name", "User name not selected");
        pageVars.put("password", "Password not entered");
        pageVars.put("myOwnList", Arrays.asList("Not Selected"));
        response.setContentType(contentType);
        try {
            response.getWriter().println(PageGenerator.getInstance().getPage("index.html", pageVars));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
