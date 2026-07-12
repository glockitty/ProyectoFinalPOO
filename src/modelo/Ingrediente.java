/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maiam
 */
public class Ingrediente {
    
    private String nombre;
    private String unidad;
    private double stock;
    private double stockMinimo;

    public Ingrediente(String nombre, String unidad, double stock, double stockMinimo) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    //métodos
    public boolean estaPorAgortarse() {
        return stock <= stockMinimo;
    }
    
    public void aumentarStock(double cantidad) {
        this.stock += cantidad;
    }
    
    public boolean disminuirStock(double cantidad) {
        if (cantidad > this.stock) {
            return false;
        } else {
            this.stock -= cantidad;
            return true;
        }
    }
    
    public boolean tieneStockSuficiente(double cantidadNecesaria) {
        return this.stock >= cantidadNecesaria;
    }
    
    @Override 
    public String toString() {
        return nombre + " (" + stock + " " + unidad + ")";
    }
}
