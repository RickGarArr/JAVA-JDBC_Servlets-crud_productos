package web.controllers;

import datos.access.ComercioAccess;
import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.EmptyResultSetException;
import datos.access.exceptions.GeneralException;
import datos.models.ComercioModel;
import helpers.response.SendMessage;
import helpers.validators.ValidarParametros;
import helpers.validators.exceptions.InvalidParameterValueException;
import helpers.validators.exceptions.NullParameterValueException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ComercioController {
    public static void create(HttpServletRequest request, HttpServletResponse response) {
        String[] parametros = {"nombre"};
        try {
            Map<String, String> parameterValues = ValidarParametros.validarParametros(parametros, request);
            ComercioModel comercio = new ComercioModel(0, parameterValues.get("nombre"));
            ComercioAccess comercioAccess = new ComercioAccess();
            comercioAccess.insertComercio(comercio);
            SendMessage.sendMessages(response, 200, "Comercio creado correctamente");
        } catch (NullParameterValueException | DuplicateEntryException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
    
    public static void select(HttpServletRequest request, HttpServletResponse response) {
        ComercioAccess comercioAccess = new ComercioAccess();
        List<ComercioModel> comercios = comercioAccess.selectAll();
        SendMessage.sendObject(response, comercioAccess.toJSONArray(comercios));
    }
    
    public static void selectOptions(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> expectedParams = new LinkedHashMap<>();
        expectedParams.put("id_comercio", 0);
        try {
            Map<String, Object> urlParameterValues = ValidarParametros.validarURLParametros(expectedParams, request);
            ComercioAccess comercioAccess = new ComercioAccess();
            ComercioModel comercioModel = comercioAccess.selectById(new ComercioModel((int) urlParameterValues.get("id_comercio")));
            SendMessage.sendObject(response, comercioAccess.toJSONObject(comercioModel));
        } catch (GeneralException | InvalidParameterValueException | EmptyResultSetException ex) {
            SendMessage.sendErrors(response, ex.getMessage());
        }
    }
}
