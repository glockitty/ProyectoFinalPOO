/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maiam
 */
public class PasoReceta {
    private int numeroPaso;
    private String descripcion;

    public PasoReceta(int numeroPaso, String descripcion) {
        this.numeroPaso = numeroPaso;
        this.descripcion = descripcion;
    }

    public int getNumeroPaso() {
        return numeroPaso;
    }

    public void setNumeroPaso(int numeroPaso) {
        this.numeroPaso = numeroPaso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return "Paso " + numeroPaso + ": " + descripcion;
    }
}
