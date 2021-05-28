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
        
        if (request.getMethod().equals("POST")) {
            ComercioController.create(request, response);
        }
        if (request.getMethod().equals("GET")) {
            if (request.getPathInfo() == null) {
                ComercioController.selectAll(request, response);       
            } else {
                ComercioController.selectByID(request, response);
            }
        }
        if (request.getMethod().equals("DELETE")) {
            ComercioController.deleteByID(request, response);
        }
        if (request.getMethod().equals("PUT")) {
            ComercioController.updateByID(request, response);
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
