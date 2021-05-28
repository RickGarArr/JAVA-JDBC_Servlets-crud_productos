package datos.models;

import datos.interfaces.IToJSON;
import javax.json.*;

public class ComercioModel implements IToJSON{
    private int idComercio;
    private String nombre;
    private boolean estaActivo;

    public ComercioModel() { }

    public ComercioModel(int idComercio) {
        this.idComercio = idComercio;
    }
    
    public ComercioModel(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public ComercioModel(String nombre, boolean estaActivo) {
        this.nombre = nombre;
        this.estaActivo = estaActivo;
    }

    public ComercioModel(int idComercio, String nombre, boolean estaActivo) {
        this.idComercio = idComercio;
        this.nombre = nombre;
        this.estaActivo = estaActivo;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return estaActivo;
    }
    
    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    @Override
    public String toString() {
        return "ComercioModel{" + "idComercio=" + idComercio + ", nombre=" + nombre + '}';
    }

    @Override
    public JsonObject toJSON() {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("id_comercio", this.idComercio);
        ob.add("nombre", this.nombre);
        ob.add("esta_activo", this.estaActivo);
        return ob.build();
    }
    
}
