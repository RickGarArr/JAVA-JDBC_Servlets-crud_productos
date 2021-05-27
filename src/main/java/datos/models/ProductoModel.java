package datos.models;

public class ProductoModel {
    private int idProducto;
    private int idSucursal;
    private int idCategoria;
    private String nombre;
    private String marca;
    private String descripcion;
    private Double precio;
    private Boolean estaActivo;
    private short cantidadAlmacen;

    public ProductoModel() {
    }
    
    public ProductoModel(int idProducto, int idSucursal, int idCategoria, String nombre, String marca, String descripcion, Double precio, Boolean estaActivo, short cantidadAlmacen) {
        this.idProducto = idProducto;
        this.idSucursal = idSucursal;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estaActivo = estaActivo;
        this.cantidadAlmacen = cantidadAlmacen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public short getCantidadAlmacen() {
        return cantidadAlmacen;
    }

    public void setCantidadAlmacen(short cantidadAlmacen) {
        this.cantidadAlmacen = cantidadAlmacen;
    }

    @Override
    public String toString() {
        return "ProductoModel{" + "idProducto=" + idProducto + ", idSucursal=" + idSucursal + ", idCategoria=" + idCategoria + ", nombre=" + nombre + ", marca=" + marca + ", descripcion=" + descripcion + ", precio=" + precio + ", estaActivo=" + estaActivo + ", cantidadAlmacen=" + cantidadAlmacen + '}';
    }
    
}
