package web.routes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.controllers.CategoriaController;

@WebServlet(name = "categorias", urlPatterns = { "/categorias", "/categorias/*" })
public class CategoriaServlet extends HttpServlet{
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            if (request.getPathInfo() == null) {
                CategoriaController.selectAll(request, response);
            } else {
                CategoriaController.selectByID(request, response);
            }
        }
        if (request.getMethod().equals("POST")) {
            CategoriaController.crear(request, response);
        }
        if (request.getMethod().equals("PUT")) {
            CategoriaController.updateByID(request, response);
        }
        if (request.getMethod().equals("DELETE")) {
            CategoriaController.deleteByID(request, response);
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
