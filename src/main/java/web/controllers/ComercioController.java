package web.controllers;

import datos.access.ComercioAccess;
import datos.access.exceptions.*;
import datos.access.result.UpdateResult;
import datos.models.ComercioModel;
import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import helpers.validators.exceptions.*;
import java.util.*;
import javax.servlet.http.*;

public class ComercioController {

    public static void create(HttpServletRequest request, HttpServletResponse response) {
        String[] parametros = {"nombre"};
        try {
            Map<String, String> parameterValues = ValidarParametros.validarParametros(parametros, request);
            ComercioModel comercio = new ComercioModel(parameterValues.get("nombre"), true);
            ComercioAccess comercioAccess = new ComercioAccess();
            SendMessage.sendObject(response, comercioAccess.insertComercio(comercio).toJSON());
        } catch (NullParameterValueException | DuplicateEntryException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }

    public static void selectAll(HttpServletRequest request, HttpServletResponse response) {
        ComercioAccess comercioAccess = new ComercioAccess();
        List<ComercioModel> comercios = comercioAccess.selectAll();
        SendMessage.sendObject(response, comercioAccess.toJSONObjectArray(comercios));
    }

    public static void selectByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> expectedParams = new LinkedHashMap<>();
        expectedParams.put("id_comercio", 0);
        try {
            Map<String, Object> urlParameterValues = ValidarParametros.validarURLParametros(expectedParams, request);
            ComercioAccess comercioAccess = new ComercioAccess();
            ComercioModel comercioModel = comercioAccess.selectById(new ComercioModel((int) urlParameterValues.get("id_comercio")));
            SendMessage.sendObject(response, comercioModel.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void deleteByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> expectedParams = new LinkedHashMap<>();
        expectedParams.put("id_comercio", 0);
        try {
            Map<String, Object> urlParameterValues = ValidarParametros.validarURLParametros(expectedParams, request);
            ComercioAccess comercioAccess = new ComercioAccess();
            UpdateResult updateResult = comercioAccess.deleteComercio(new ComercioModel((int) urlParameterValues.get("id_comercio")));
            SendMessage.sendObject(response, updateResult.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex ) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void updateByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> expectedParams = new LinkedHashMap<>();
        expectedParams.put("id_comercio", 0);
        try {
            Map<String, Object> urlParameterValues = ValidarParametros.validarURLParametros(expectedParams, request);
            ComercioAccess comercioAccess = new ComercioAccess();
            ComercioModel comercioModel = comercioAccess.selectById(new ComercioModel((int) urlParameterValues.get("id_comercio")));
            if (request.getParameter("nombre") != null && !request.getParameter("nombre").trim().equals("")) {
                comercioModel.setNombre(request.getParameter("nombre"));
            }
            if (request.getParameter("esta_activo") != null && !request.getParameter("esta_activo").trim().equals("")) {
                comercioModel.setEstaActivo(Boolean.parseBoolean(request.getParameter("esta_activo")));
            }
            UpdateResult updateResult = comercioAccess.updateComercio(comercioModel);
            SendMessage.sendObject(response, updateResult.toJSON());
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex ) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
}
