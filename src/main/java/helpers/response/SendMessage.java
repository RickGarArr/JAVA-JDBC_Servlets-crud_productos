package helpers.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.json.*;
import javax.servlet.http.HttpServletResponse;

public abstract class SendMessage {

    public static void sendErrors(HttpServletResponse response, String... errores) {
        response.setContentType("application/json");
        response.setStatus(400);
        JsonObjectBuilder ob = Json.createObjectBuilder();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        PrintWriter out;
        try {
            out = response.getWriter();
            ob.add("ok", false);
            for (String error : errores) {
                if (error.contains("&")) {
                    String[] subErrors = error.split("&");
                    for (String subError : subErrors) {
                        ab.add(subError);
                    }
                } else {
                    ab.add(error);
                }
            }
            ob.add("errores", ab.build());
            Writer writer = new StringWriter();
            Json.createWriter(writer).writeObject(ob.build());
            String jsonString = writer.toString();
            out.print(jsonString);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void sendMessages(HttpServletResponse response, int status, String... mensajes) {
        response.setContentType("application/json");
        response.setStatus(status);
        JsonObjectBuilder ob = Json.createObjectBuilder();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        PrintWriter out;
        try {
            out = response.getWriter();
            ob.add("ok", true);
            for (String message : mensajes) {
                if (message.contains("&")) {
                    String[] subMessages = message.split("&");
                    for (String subMessage : subMessages) {
                        ab.add(subMessage);
                    }
                } else {
                    ab.add(message);
                }
            }
            ob.add("messages", ab.build());
            Writer writer = new StringWriter();
            Json.createWriter(writer).writeObject(ob.build());
            String jsonString = writer.toString();
            out.print(jsonString);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void sendObject(HttpServletResponse response, JsonObject jsonObject) {
        response.setContentType("application/json");
        response.setStatus(200);
        try {
            PrintWriter out = response.getWriter();
            Writer writer = new StringWriter();
            Json.createWriter(writer).writeObject(jsonObject);
            String jsonString = writer.toString();
            out.print(jsonString);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
