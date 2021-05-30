package web.controllers;

import datos.access.CategoriaAccess;
import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.EmptyResultSetException;
import datos.access.exceptions.GeneralException;
import datos.models.CategoriaModel;
import datos.access.interfaces.IAccess;
import datos.access.result.UpdateResult;
import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import helpers.validators.exceptions.InvalidParameterValueException;
import helpers.validators.exceptions.NullParameterValueException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoriaController {
    public static void crear(HttpServletRequest request, HttpServletResponse response) {
        String[] parametros = {"nombre", "descripcion"};
        try {
            Map<String, String> pv = ValidarParametros.validarParametros(parametros, request);
            CategoriaModel categoria = new CategoriaModel(0, pv.get("nombre"), pv.get("descripcion"), true);
            IAccess<CategoriaModel> sa = new CategoriaAccess();
            SendMessage.sendObject(response, sa.insert(categoria).toJSON());
        } catch (NullParameterValueException | DuplicateEntryException | GeneralException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void selectAll(HttpServletRequest request, HttpServletResponse response) {
        IAccess<CategoriaModel> sa = new CategoriaAccess();
        List<CategoriaModel> sucursales = sa.selectAll();
        SendMessage.sendObject(response, sa.toJSONObjectArray(sucursales));
    }
    
    public static void selectByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("id_categoria", 0);
        try {
            Map<String, Object> pv = ValidarParametros.validarURLParametros(urlParams, request);
            IAccess<CategoriaModel> sa = new CategoriaAccess();
            CategoriaModel sm = sa.selectById(new CategoriaModel((Integer)pv.get("id_categoria")));
            SendMessage.sendObject(response, sm.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void updateByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> expectedParams = new HashMap<>();
        expectedParams.put("id_categoria", 0);
        try {
            Map<String, Object> pv = ValidarParametros.validarURLParametros(expectedParams, request);
            IAccess<CategoriaModel> sa = new CategoriaAccess();
            CategoriaModel cm = sa.selectById(new CategoriaModel((Integer)pv.get("id_categoria")));
            if (request.getParameter("nombre") != null && !request.getParameter("nombre").trim().equals("")) {
                cm.setNombre(request.getParameter("nombre"));
            }
            if (request.getParameter("descripcion") != null && !request.getParameter("descripcion").trim().equals("")) {
                cm.setDescripcion(request.getParameter("id_comercio"));
            }
            if (request.getParameter("esta_activa") != null && !request.getParameter("esta_activa").trim().equals("")) {
                cm.setestaActiva(Boolean.parseBoolean(request.getParameter("esta_activa")));
            }
            UpdateResult updateResult =sa.update(cm);
            SendMessage.sendObject(response, updateResult.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex ) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void deleteByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("id_categoria", 0);
        try {
            Map<String, Object> pv = ValidarParametros.validarURLParametros(urlParams, request);
            IAccess<CategoriaModel> sa = new CategoriaAccess();
            UpdateResult ur = sa.delete(new CategoriaModel((Integer)pv.get("id_categoria")));
            SendMessage.sendObject(response, ur.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
}
