package web.controllers;

import datos.access.SucursalAccess;
import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.EmptyResultSetException;
import datos.access.exceptions.GeneralException;
import datos.models.SucursalModel;
import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import helpers.validators.exceptions.NullParameterValueException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import datos.access.interfaces.IAccess;
import datos.access.result.UpdateResult;
import helpers.validators.exceptions.InvalidParameterValueException;
import java.util.HashMap;
import java.util.List;

public class SucursalController {
    
    public static void crear(HttpServletRequest request, HttpServletResponse response) {
        String[] parametros = {"nombre", "id_comercio"};
        try {
            Map<String, String> pv = ValidarParametros.validarParametros(parametros, request);
            SucursalModel sucursal = new SucursalModel(0, Integer.parseInt(pv.get("id_comercio")), pv.get("nombre"), true);
            IAccess<SucursalModel> sa = new SucursalAccess();
            SendMessage.sendObject(response, sa.insert(sucursal).toJSON());
        } catch (NullParameterValueException | DuplicateEntryException | GeneralException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void selectAll(HttpServletRequest request, HttpServletResponse response) {
        IAccess<SucursalModel> sa = new SucursalAccess();
        List<SucursalModel> sucursales = sa.selectAll();
        SendMessage.sendObject(response, sa.toJSONObjectArray(sucursales));
    }
    
    public static void selectByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("id_sucursal", 0);
        try {
            Map<String, Object> pv = ValidarParametros.validarURLParametros(urlParams, request);
            IAccess<SucursalModel> sa = new SucursalAccess();
            SucursalModel sm = sa.selectById(new SucursalModel((Integer)pv.get("id_sucursal")));
            SendMessage.sendObject(response, sm.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void updateByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> expectedParams = new HashMap<>();
        expectedParams.put("id_sucursal", 0);
        try {
            Map<String, Object> urlParameterValues = ValidarParametros.validarURLParametros(expectedParams, request);
            IAccess<SucursalModel> sa = new SucursalAccess();
            SucursalModel sm = sa.selectById(new SucursalModel((Integer) urlParameterValues.get("id_sucursal")));
            if (request.getParameter("nombre") != null && !request.getParameter("nombre").trim().equals("")) {
                sm.setNombre(request.getParameter("nombre"));
            }
            if (request.getParameter("id_comercio") != null && !request.getParameter("id_comercio").trim().equals("")) {
                sm.setIdComercio(Integer.parseInt(request.getParameter("id_comercio")));
            }
            if (request.getParameter("esta_activa") != null && !request.getParameter("esta_activa").trim().equals("")) {
                sm.setActiva(Boolean.parseBoolean(request.getParameter("esta_activa")));
            }
            UpdateResult updateResult =sa.update(sm);
            SendMessage.sendObject(response, updateResult.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex ) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void deleteByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("id_sucursal", 0);
        try {
            Map<String, Object> pv = ValidarParametros.validarURLParametros(urlParams, request);
            IAccess<SucursalModel> sa = new SucursalAccess();
            UpdateResult ur = sa.delete(new SucursalModel((Integer)pv.get("id_comercio")));
            SendMessage.sendObject(response, ur.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
}
