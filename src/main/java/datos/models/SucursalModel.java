package datos.models;

public class SucursalModel {
    private int idSucursal;
    private int idComercio;
    private String nombre;

    public SucursalModel() {
    }

    public SucursalModel(int idSucursal, int idComercio, String nombre) {
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

    @Override
    public String toString() {
        return "SucursalModel{" + "idSucursal=" + idSucursal + ", idComercio=" + idComercio + ", nombre=" + nombre + '}';
    }
    
}
