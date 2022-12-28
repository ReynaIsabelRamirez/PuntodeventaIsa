
package Modelo;
public class Productos {
    private int id;
    private String codigo;
    private String nombre;
    private String proveedor;
    private String categoria;
    private int stock;
    private double precioventa;
    public Productos() {
    }
    public Productos(int id, String codigo, String nombre, String proveedor, String categoria, int stock, double precioventa) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.stock = stock;
        this.precioventa = precioventa;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getProveedor() {
        return proveedor;
    }
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public double getPrecioventa() {
        return precioventa;
    }
    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }
}
    
    
    

