/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Ingrediente;
/**
 *
 * @author maiam
 */
public class IngredienteDAO extends ArchivoDAO {
    
    public IngredienteDAO() throws IOException {
        super("inventario.txt");
    }
    
    public ArrayList<Ingrediente> listarTodos() {
        ArrayList<Ingrediente> listaIng = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";");
                String nombre = partes[0];
                String unidad = partes[1];
                double stock = Double.parseDouble(partes[2]);
                double stockMinimo = Double.parseDouble(partes[3]);
                listaIng.add(new Ingrediente(nombre, unidad,stock,stockMinimo));
                
            }
        } catch (IOException e) {
            System.out.println("Error al leer inventario" + e.getMessage());
        }
        return listaIng;
    }
    
    public void guardarTodos(ArrayList<Ingrediente> lista) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for(Ingrediente ing : lista) {
                bw.write(convertirALinea(ing));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar inventario:" + e.getMessage());
        }
    }
    
    private String convertirALinea(Ingrediente ingrediente) {
        return ingrediente.getNombre() + ";" 
                + ingrediente.getUnidad() + ";" 
                + ingrediente.getStock() + ";" 
                +ingrediente.getStockMinimo();
    }
    
    public void agregar(Ingrediente nuevo) {
        ArrayList<Ingrediente> listaIng = listarTodos();
        listaIng.add(nuevo);
        guardarTodos(listaIng);
    }
    
    public Ingrediente buscarIngPorNombre(ArrayList<Ingrediente> lista, String nombre) {
        for(Ingrediente ing : lista) {
            if(ing.getNombre().equalsIgnoreCase(nombre)){
                return ing;
            }
        }
        return null;
    }
}
