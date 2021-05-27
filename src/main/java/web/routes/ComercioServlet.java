package web.routes;

import helpers.response.SendMessage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.controllers.ComercioController;

@WebServlet(name = "comercio", urlPatterns = {"/comercios", "/comercios/*"})
public class ComercioServlet extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        requestURI = requestURI.endsWith("/") ? requestURI.substring(1, requestURI.length() - 1) : requestURI.substring(1);
        
        if (request.getMethod().equals("POST") && request.getServletPath().equals("/comercios")) {
            ComercioController.create(request, response);
        }
        if (request.getMethod().equals("GET") && requestURI.equals("crud_productos/comercios")) {
            ComercioController.select(request, response);
        } else if (request.getMethod().equals("GET") && request.getServletPath().equals("/comercios")){
            ComercioController.selectOptions(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.processRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.processRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.processRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.processRequest(request, response);
        } catch (ServletException | IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
