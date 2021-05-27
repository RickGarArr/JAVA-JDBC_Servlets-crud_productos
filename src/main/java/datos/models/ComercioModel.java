package datos.models;

public class ComercioModel {
    private int idComercio;
    private String nombre;

    public ComercioModel() { }

    public ComercioModel(int idComercio) {
        this.idComercio = idComercio;
    }


    public ComercioModel(int idComercio, String nombre) {
        this.idComercio = idComercio;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "ComercioModel{" + "idComercio=" + idComercio + ", nombre=" + nombre + '}';
    }
    
}
