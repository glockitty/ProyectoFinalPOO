/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author maiam
 */
public class Receta {
    
    private TipoHelado tipoHelado;
    private String sabor;
    
    private ArrayList<DetalleReceta> detalles;
    private ArrayList<PasoReceta> pasos;

    public Receta(TipoHelado tipoHelado, String sabor) {
        this.tipoHelado = tipoHelado;
        this.sabor = sabor;
        this.detalles = new ArrayList<>();
        this.pasos = new ArrayList<>();
    }

    public TipoHelado getTipoHelado() {
        return tipoHelado;
    }

    public void setTipoHelado(TipoHelado tipoHelado) {
        this.tipoHelado = tipoHelado;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public ArrayList<DetalleReceta> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleReceta> detalles) {
        this.detalles = detalles;
    }

    public ArrayList<PasoReceta> getPasos() {
        return pasos;
    }

    public void setPasos(ArrayList<PasoReceta> pasos) {
        this.pasos = pasos;
    }
    
    //metodos
    public void agregarDetalle(DetalleReceta detalle) {
        detalles.add(detalle);
    }
    
    public void agregarPaso(PasoReceta paso) {
        pasos.add(paso);
    }
    
    private int obtenerSiguienteNumeroDePaso() {
        return pasos.size() + 1;
    }
    
    public void agregarPaso(String descripcion) {
        int siguienteNumero = obtenerSiguienteNumeroDePaso();
        agregarPaso(new PasoReceta(siguienteNumero,descripcion));
    }
    
    public String getIdentificador() {
        return tipoHelado + " - " + sabor;
    }
    
    @Override
    public String toString() {
        return getIdentificador();
    }
}
