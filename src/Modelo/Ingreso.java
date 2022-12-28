
package Modelo;
public class Ingreso {
    private int id;
    private String nombre;
    private String usuario;
    private String clave;
    private String rol;

    public Ingreso() {
    }

    public Ingreso(int id, String nombre, String usuario, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
}
