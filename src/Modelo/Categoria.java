package Modelo;

public class Categoria {

    public int id;
    public String clave;
    public String nombre;

    public Categoria() {

    }

    public Categoria(int id, String clave, String nombre) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
