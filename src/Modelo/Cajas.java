
package Modelo;

public class Cajas {
    public int id;
    public float inicio;
    public float totalfin;
    public String fecha;
    

    public Cajas() {
    }

    public Cajas(int id, float inicio, float totalfin, String fecha) {
        this.id = id;
        this.inicio = inicio;
        this.totalfin = totalfin;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getInicio() {
        return inicio;
    }

    public void setInicio(float inicio) {
        this.inicio = inicio;
    }

    public float getTotalfin() {
        return totalfin;
    }

    public void setTotalfin(float totalfin) {
        this.totalfin = totalfin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
    
   
}
