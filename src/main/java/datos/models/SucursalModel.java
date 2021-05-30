package datos.models;

import datos.interfaces.IToJSON;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class SucursalModel implements IToJSON {
    private int idSucursal;
    private int idComercio;
    private String nombre;
    private boolean estaActiva;

    public SucursalModel() {
    }

    public SucursalModel(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public SucursalModel(int idComercio, String nombre, boolean estaActiva) {
        this.idComercio = idComercio;
        this.nombre = nombre;
        this.estaActiva = estaActiva;
    }
    
    

    public SucursalModel(int idSucursal, int idComercio, String nombre, boolean estaAciva) {
        this.idSucursal = idSucursal;
        this.idComercio = idComercio;
        this.nombre = nombre;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
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
    
    public boolean isActiva() {
        return this.estaActiva;
    }
    
    public void setActiva( boolean estaActiva) {
        this.estaActiva = estaActiva;
    }

    @Override
    public String toString() {
        return "SucursalModel{" + "idSucursal=" + idSucursal + ", idComercio=" + idComercio + ", nombre=" + nombre + '}';
    }
    
    @Override
    public JsonObject toJSON() {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("id_sucursal", this.idSucursal);
        ob.add("id_comercio", this.idComercio);
        ob.add("nombre", this.nombre);
        ob.add("esta_activa", this.estaActiva);
        return ob.build();
    }
    
}
