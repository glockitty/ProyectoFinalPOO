/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.IngredienteDAO;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Ingrediente;

/**
 *
 * @author maiam
 */
public class ControladorInventario {
    
    private final IngredienteDAO _ingredienteDAO;
    
    public ControladorInventario() throws IOException {
        this._ingredienteDAO = new IngredienteDAO();
    }
    
    public ArrayList<Ingrediente> obtenerIngredientes() {
        return _ingredienteDAO.listarTodos();
    }
    
    
    //Registra nuevo ingrediente, validando que no exista otro igual, y los nmrs sean los correctos
    public String registrarIngredientes(String nombre, String unidad, double
            stock, double stockMinimo) {
        if (nombre == null || nombre.trim().isEmpty() || unidad == null || unidad.trim().isEmpty()) {
            return "ERROR, completa nombre y unidad.";
        }
        if (stock < 0 || stockMinimo < 0) {
            return "ERROR, el stock no puede ser negativo.";
        }
        
        ArrayList<Ingrediente> listaIng = _ingredienteDAO.listarTodos();
        Ingrediente existente = _ingredienteDAO.buscarIngPorNombre(listaIng, nombre);
        
        if (existente != null) {
            return "ERROR, ya existe el ingrediente llamado \"" + nombre+ "\".";
        }
        
        _ingredienteDAO.agregar(new Ingrediente(nombre,unidad,stock,stockMinimo));
        return "EXITO, ingrediente registrado corectamente.";
    }
    
    
    //edita un ingrediente existente y actualiza los datos de este.
    public String editarIngrediente(String nombreOriginal, String nuevoNombre,
            String nuevaUnidad, double nuevoStock, double nuevoStockMinimo) {
        
        if (nuevoNombre.trim().isEmpty() || nuevaUnidad.trim().isEmpty()) {
            return "ERROR, completa nombre y unidad.";
        }
        if (nuevoStock < 0 || nuevoStockMinimo < 0) {
            return "ERROR, el stock no puede ser negativo.";
        }
        
        ArrayList<Ingrediente> listaIng = _ingredienteDAO.listarTodos();
        Ingrediente ing = _ingredienteDAO.buscarIngPorNombre(listaIng, nombreOriginal);
        
        if (ing == null) {
            return "ERROR, no se encontro el ingrediente.";
        }
        
        
        //Si el name cambio , validar si no choca con otro ya existente
        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre)) {
            Ingrediente otro = _ingredienteDAO.buscarIngPorNombre(listaIng, nuevoNombre);
            if (otro != null) {
                return "ERROR, ya existe otro ingrediente llamado \"" + nuevoNombre+ "\".";
            }
        }
        
        ing.setNombre(nuevoNombre);
        ing.setUnidad(nuevaUnidad);
        ing.setStock(nuevoStock);
        ing.setStockMinimo(nuevoStockMinimo);
        
        _ingredienteDAO.guardarTodos(listaIng);
        return "EXITO, ingrediente actualizado.";
    }
    
    public String eliminarIngrediente(String nombre) {
        ArrayList<Ingrediente> listIng = _ingredienteDAO.listarTodos();
        Ingrediente ing = _ingredienteDAO.buscarIngPorNombre(listIng, nombre);
        
        if (ing == null) {
            return "ERROR, no se encontro el ingrediente.";
        }
        
        listIng.remove(ing);
        _ingredienteDAO.guardarTodos(listIng);
        return "EXITO, ingrediente eliminado.";
    }
    
}
