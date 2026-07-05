/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.DetalleReceta;
import modelo.Ingrediente;
import modelo.PasoReceta;
import modelo.Receta;
import modelo.TipoHelado;
import util.ArchivoUtil;

/**
 *
 * @author maiam
 */
public class RecetaDAO extends ArchivoDAO{
    
    public RecetaDAO() {
        super("recetas.txt");
    }
    
    //leer archivo receta, se necesita la lista de ingredientes del inventario para poder asociar
    //cada DetalleReceta con el ingrediente
    public ArrayList<Receta> listarTodas(ArrayList<Ingrediente> ingredientesDisponibles) {
        ArrayList<Receta> listaRec = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                
                String[] partes = linea.split("\\|", 4);
                TipoHelado tipo = TipoHelado.valueOf(partes[0]);
                String sabor = partes[1];
                
                Receta receta = new Receta(tipo, sabor);
                
                //DetalleReceta, ingrediente y cantidad
                if (partes.length > 2 && !partes[2].trim().isEmpty()) {
                    String[] tockensIngredientes = partes[2].split(",");
                    for (String token : tockensIngredientes) {
                        String[] datos = token.split(":");
                        String nombreIngrediente = datos[0];
                        double cantidad = Double.parseDouble(datos[1]);
                        
                        Ingrediente ingrediente = buscarIngredientePorNombre(
                        ingredientesDisponibles,nombreIngrediente);
                        
                        if (ingrediente != null) {
                            receta.agregarDetalle(new DetalleReceta(ingrediente,cantidad));
                        }
                    }
                }
                
                //PasoReceta
                if (partes.length > 3 && !partes[3].trim().isEmpty()) {
                    String[] tokensPasos = partes[3].split(",");
                    int numero = 1;
                    for (String descripcionPaso : tokensPasos) {
                        receta.agregarPaso(new PasoReceta(numero, descripcionPaso));
                        numero++;
                    }
                }
                listaRec.add(receta);
            }
            
        } catch (Exception e) {
            System.out.println("Error al leer recetas" + e.getMessage());
        }
        return listaRec;
    }
    
    
    public void agregar(Receta receta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))){
            bw.write(convertirALinea(receta));
            bw.newLine();
                    
        } catch (Exception e) {
            System.out.println("Error al guardar receta" + e.getMessage());
        }
    }
    
    private String convertirALinea(Receta receta) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(receta.getTipoHelado().name()).append("|");
        sb.append(receta.getSabor()).append("|");
        
        ArrayList<DetalleReceta> detalles = receta.getDetalles();
        for (int i = 0; i < detalles.size(); i++) {
            DetalleReceta d = detalles.get(i);
            sb.append(d.getIngrediente().getNombre()).append(":")
            .append(d.getCantidadPorUnidad());
            
            if (i < detalles.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("|");
        
        
        ArrayList<PasoReceta> pasos = receta.getPasos();
        for (int i = 0; i < pasos.size(); i++) {
            sb.append(pasos.get(i).getDescripcion());
            if (i < pasos.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    
    private Ingrediente buscarIngredientePorNombre(ArrayList<Ingrediente> listaIngredientes,
            String nombre) {
        for(Ingrediente ing : listaIngredientes) {
            if (ing.getNombre().equalsIgnoreCase(nombre)) {
                return ing;
            }
        }
        return null;
    }
    
}
