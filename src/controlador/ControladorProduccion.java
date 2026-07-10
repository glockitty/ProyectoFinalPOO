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

/**
 *
 * @author maiam
 */
public class ControladorProduccion {
    
    private IngredienteDAO _ingredienteDAO;
    private ProduccionDAO _produccionDAO;
    
    public ControladorProduccion() throws IOException {
        this._ingredienteDAO = new IngredienteDAO();
        this._produccionDAO = new ProduccionDAO();
    }
    
    //produce de acuerdo a la cantidad segun el tipo de receta
    public String prepararProduccion(Receta receta, int cantidad) {
        if (cantidad <= 0) {
            return "Error, la cantidad a producir debe ser mayor a 0.";
        }
        
        ArrayList<Ingrediente> inventario = _ingredienteDAO.listarTodos();
        ArrayList<DetalleReceta> detalles = receta.getDetalles();
        
        if (detalles.isEmpty()) {
            return "Error, la receta no tiene ingredientes agregados.";
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
                        .append("esta por agotarse (quedan ")
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
        
        String mensaje = "Se produjo correctamente " + cantidad + " unidades de " + receta.getIdentificador()
                + ".";
        
        if (avisoStockBajo.length() > 0) {
            mensaje += "\n\nAVISO - ingredientes por agotarse:" + avisoStockBajo.toString();
        }
        return mensaje;
    }
    
    public ArrayList<Produccion> obtenerReportes() {
        return _produccionDAO.listarTodas();
    }
}
