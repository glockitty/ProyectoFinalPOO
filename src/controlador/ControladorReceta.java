/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.IngredienteDAO;
import dao.RecetaDAO;
import java.io.IOException;
import java.util.ArrayList;
import modelo.DetalleReceta;
import modelo.Ingrediente;
import modelo.Receta;
/**
 *
 * @author maiam
 */
public class ControladorReceta {
    
    private RecetaDAO _recetaDAO;
    private IngredienteDAO _ingredienteDAO;
    
    public ControladorReceta() throws IOException {
        this._recetaDAO = new RecetaDAO();
        this._ingredienteDAO = new IngredienteDAO();
    }
    
    
    public ArrayList<Receta> obtenerRecetas() {
        ArrayList<Ingrediente> ingredientes = _ingredienteDAO.listarTodos();
        return _recetaDAO.listarTodas(ingredientes);
    }
    
    
    //valida y agrega un detalleReceta a receta, todavia no se guarda en memoria
    public String agregarIngredienteAReceta(Receta receta, String nombreIngrediente,
            double cantidadPorUnidad) {
        if (cantidadPorUnidad <= 0) {
            return "ERROR, la cantiad debe ser mayor a 0";
        }
        
        ArrayList<Ingrediente> ingredientes = _ingredienteDAO.listarTodos();
        Ingrediente ingrediente = _ingredienteDAO.buscarIngPorNombre(ingredientes, nombreIngrediente);
        
        if (ingrediente == null) {
            return "ERROR, el ingrediente no existe en el inventario";
        }
        
        receta.agregarDetalle(new DetalleReceta(ingrediente, cantidadPorUnidad));
        return "EXITO, el ingrediente se ha agregado a la receta.";
    }
    
    
    public String agregarPasoAReceta(Receta receta, String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return "ERROR, la descripcion del paso no puede estar vacía";
        }
        
        receta.agregarPaso(descripcion);
        return "EXITO, paso agregado a la receta.";
        
    }
    
    
    
    public String guardarReceta(Receta receta) {
        if (receta.getSabor() == null || receta.getSabor().trim().isEmpty()) {
            return "ERROR, la receta debe contener un sabor.";
        }
        
        if (receta.getDetalles().isEmpty()) {
            return "ERROR, la receta debe tener al menos un ingrediente.";
        }
        
        _recetaDAO.agregar(receta);
        return "EXITO: receta guardada correctamente.";
    }
    
    
    
}
