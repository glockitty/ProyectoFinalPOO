/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maiam
 */
public class Produccion {
    private Receta receta;
    private int cantidad;
    private String fecha;

    public Produccion(Receta receta, int cantidad, String fecha) {
        this.receta = receta;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return fecha + " | " + receta.getIdentificador() + " | Cantidad: " + cantidad;
    }
    
}
