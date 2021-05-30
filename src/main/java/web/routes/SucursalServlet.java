package web.routes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.controllers.SucursalController;

@WebServlet(name = "sucursal", urlPatterns = {"/sucursales/*"})
public class SucursalServlet extends HttpServlet {
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if (request.getMethod().equals("POST")) {
            SucursalController.crear(request, response);
        }
        if (request.getMethod().equals("GET")) {
            if (request.getPathInfo() == null) {
                SucursalController.selectAll(request, response);
            } else {
                SucursalController.selectByID(request, response);
            }
        }
        if (request.getMethod().equals("PUT")) {
            SucursalController.updateByID(request, response);
        }
        if (request.getMethod().equals("DELETE")) {
            SucursalController.deleteByID(request, response);
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
