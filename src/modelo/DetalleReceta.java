/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maiam
 */
public class DetalleReceta {
    
    private Ingrediente ingrediente;
    private double cantidadPorUnidad;

    public DetalleReceta(Ingrediente ingrediente, double cantidadPorUnidad) {
        this.ingrediente = ingrediente;
        this.cantidadPorUnidad = cantidadPorUnidad;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public double getCantidadPorUnidad() {
        return cantidadPorUnidad;
    }

    public void setCantidadPorUnidad(double cantidadPorUnidad) {
        this.cantidadPorUnidad = cantidadPorUnidad;
    }
    
    //metodo
    public double calcularCantidadTotal(int cantidadAPreparar) {
        return cantidadPorUnidad * cantidadAPreparar;
    }
    
    @Override
    public String toString() {
        return ingrediente.getNombre() + " - " + cantidadPorUnidad + " " + 
                ingrediente.getUnidad() + " por unidad";
    }
    
    
}
