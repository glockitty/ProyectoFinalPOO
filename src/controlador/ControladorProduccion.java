/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.IngredienteDAO;
import dao.ProduccionDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import modelo.DetalleReceta;
import modelo.Ingrediente;
import modelo.Produccion;
import modelo.Receta;
import modelo.PasoReceta;

/**
 *
 * @author maiam
 */
public class ControladorProduccion {
    
    private final IngredienteDAO _ingredienteDAO;
    private final ProduccionDAO _produccionDAO;
    
    public ControladorProduccion() throws IOException {
        this._ingredienteDAO = new IngredienteDAO();
        this._produccionDAO = new ProduccionDAO();
    }
    
    //produce de acuerdo a la cantidad segun el tipo de receta
    public String prepararProduccion(Receta receta, int cantidad) {
        if (cantidad <= 0) {
            return "ERROR, la cantidad a producir debe ser mayor a 0.";
        }
        
        ArrayList<Ingrediente> inventario = _ingredienteDAO.listarTodos();
        ArrayList<DetalleReceta> detalles = receta.getDetalles();
        
        if (detalles.isEmpty()) {
            return "ERROR, la receta no tiene ingredientes agregados.";
        }
        
        //validar stock suficiente o no
        for (DetalleReceta detalle : detalles) {
            double necesarioTotal = detalle.calcularCantidadTotal(cantidad);
            Ingrediente ingredienteInventario = _ingredienteDAO.buscarIngPorNombre(
                    inventario, detalle.getIngrediente().getNombre());
            
            if (ingredienteInventario == null) {
                return "ERROR, el ingrediente \"" + detalle.getIngrediente().getNombre()
                        + "\" no existe en el inventario,";
            }
            
            if (!ingredienteInventario.tieneStockSuficiente(necesarioTotal)) {
                return "ERROR, stock insuficiente de  \"" + ingredienteInventario.getNombre() +
                        "\". " + "Se necesitan " + necesarioTotal + " " + 
                        ingredienteInventario.getUnidad() + " y solo hay " +
                        ingredienteInventario.getStock() + ".";        
            }
        }
        
        //descontar stock 
        StringBuilder avisoStockBajo = new StringBuilder();
        
        for (DetalleReceta detalle : detalles ) {
            double necesarioTotal = detalle.calcularCantidadTotal(cantidad);
            Ingrediente ingredienteInventario = _ingredienteDAO.buscarIngPorNombre(
                    inventario,detalle.getIngrediente().getNombre());
            
            ingredienteInventario.disminuirStock(necesarioTotal);
            
            
            //aviso del minimo stock
            if (ingredienteInventario.estaPorAgortarse()) {
                avisoStockBajo.append("\n- ").append(ingredienteInventario.getNombre())
                        .append(" esta por agotarse (quedan ")
                        .append(ingredienteInventario.getStock())
                        .append(" ").append(ingredienteInventario.getUnidad()).append(").");
            }
        }
        _ingredienteDAO.guardarTodos(inventario);
        
        
        //registrar prod en el reporte
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaActual = formato.format(new Date());
        
        Produccion produccion = new Produccion(receta, cantidad, fechaActual);
        _produccionDAO.agregar(produccion);
        
        StringBuilder mensajeFinal = new StringBuilder();
        
        // Mensaje principal de éxito
        mensajeFinal.append("EXITO, Se produjo correctamente ").append(cantidad).append(" unidades de ").append(receta.getIdentificador()).append(".\n");
        
        // Alertas de stock bajo (si es que existen)
        if (avisoStockBajo.length() > 0) { 
            mensajeFinal.append("\nAVISO - ingredientes por agotarse:").append(avisoStockBajo.toString()).append("\n"); 
        }
        
        // Listado de ingredientes escalados según la cantidad producida
        mensajeFinal.append("INGREDIENTES UTILIZADOS:\n");
        for (DetalleReceta detalle : detalles) { 
            // Multiplica la cantidad base de la receta por la cantidad ingresada por el usuario
            double cantidadTotalInsumo = detalle.calcularCantidadTotal(cantidad); 
            mensajeFinal.append(" • ").append(detalle.getIngrediente().getNombre())
                        .append(": ")
                        .append(cantidadTotalInsumo)
                        .append(" ")
                        .append(detalle.getIngrediente().getUnidad())
                        .append("\n"); 
        }
        // Listado de la secuencia de pasos de la receta
        mensajeFinal.append("PASOS DE PREPARACIÓN:\n");
        if (receta.getPasos().isEmpty()) { 
            mensajeFinal.append(" No se registraron pasos para esta receta.\n");
        } else {
            for (PasoReceta paso : receta.getPasos()) { 
                mensajeFinal.append("  ")
                            .append(paso.getNumeroPaso())
                            .append(". ")
                            .append(paso.getDescripcion())
                            .append("\n"); 
            }
        }   
        return mensajeFinal.toString();
    }
    public ArrayList<Produccion> obtenerReportes() {
        return _produccionDAO.listarTodas(); 
    }
}
