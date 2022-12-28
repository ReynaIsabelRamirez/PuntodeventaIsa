
package Modelo;
public class Detalle {
    private int id;
    private String cod_nombre;
    private int cantidad;
    private double precio;
    private int id_venta;

    public Detalle() {
    }

    public Detalle(int id, String cod_nombre, int cantidad, double precio, int id_venta) {
        this.id = id;
        this.cod_nombre = cod_nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_venta = id_venta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCod_nombre() {
        return cod_nombre;
    }

    public void setCod_nombre(String cod_nombre) {
        this.cod_nombre = cod_nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }



}
