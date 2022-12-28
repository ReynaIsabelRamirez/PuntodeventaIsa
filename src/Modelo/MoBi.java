package Modelo;

public class MoBi {

    public float resultadopr;
    public float resultado1;
    public float resultado2;
    public float resultado3;
    public float resultado4;
    public float resultado5;
    public float resultado6;
    public float resultado7;
    public float resultado8;
    public float resultado9;
    public float resultado10;
    public float resultado11;
    public float total;
    public float a;

    public float BilleteMil() {
        resultado1 = a * 1000;
        return resultado1;
    }

    public float BilleteQuinien() {
        resultado2 = a * 500;
        return resultado2;
    }

    public float BilleteDocientos() {
        resultado3 = a * 200;
        return resultado3;
    }

    public float BilleteCien() {
        resultado4 = a * 100;
        return resultado4;
    }

    public float BilleteCincuenta() {
        resultado5 = a * 50;
        return resultado5;
    }

    public float BilleteVeinte() {
        resultado6 = a * 20;
        return resultado6;
    }

    public float MonedaDiez() {
        resultado7 = a * 10;
        return resultado7;
    }

    public float MonedaCinco() {
        resultado8 = a * 5;
        return resultado8;
    }

    public float MonedaDos() {
        resultado9 = a * 2;
        return resultado9;
    }

    public float MonedaUno() {
        resultado10 = a * 1;
        return resultado10;
    }

    public float MonedaCentavos() {
        resultado11 = (float) (a * 0.50);
        return resultado11;
    }

    public float suma() {
        resultadopr = resultado1 + resultado2 + resultado3 + resultado4 + resultado5 + resultado6 + resultado7 + resultado8 + resultado9 + resultado10 + resultado11;
        return resultadopr;
    }

    public float total() {
        total = a + resultadopr;
        return total;
    }
}
