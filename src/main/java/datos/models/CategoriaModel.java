package datos.models;

import datos.interfaces.IToJSON;
import java.util.Objects;
import javax.json.*;

public class CategoriaModel implements IToJSON{
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private boolean estaActiva;

    public CategoriaModel() {
    }

    public CategoriaModel(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public CategoriaModel(boolean estaActiva) {
        this.estaActiva = estaActiva;
    }

    public CategoriaModel(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CategoriaModel(int idCategoria, String nombre, String descripcion, boolean estaActiva) {
        this(nombre, descripcion);
        this.idCategoria = idCategoria;
        this.estaActiva = estaActiva;
    }

    public int getidCategoria() {
        return idCategoria;
    }

    public void setidCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isestaActiva() {
        return estaActiva;
    }

    public void setestaActiva(boolean estaActiva) {
        this.estaActiva = estaActiva;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idCategoria;
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.descripcion);
        hash = 59 * hash + (this.estaActiva ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoriaModel other = (CategoriaModel) obj;
        if (this.idCategoria != other.idCategoria) {
            return false;
        }
        if (this.estaActiva != other.estaActiva) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CategoriaModel{" + "idCategoria=" + idCategoria + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estaActiva=" + estaActiva + '}';
    }

    @Override
    public JsonObject toJSON() {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("id_categoria", this.idCategoria);
        ob.add("nombre", this.nombre);
        ob.add("descripcion", this.descripcion);
        ob.add("esta_activa", this.estaActiva);
        return ob.build();
    }
        
}
