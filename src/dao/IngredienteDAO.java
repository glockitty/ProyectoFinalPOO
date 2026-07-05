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
import java.util.ArrayList;
import modelo.Ingrediente;
import util.ArchivoUtil;
/**
 *
 * @author maiam
 */
public class IngredienteDAO extends ArchivoDAO {
    
    public IngredienteDAO() {
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
                String[] partes = linea.split("\\|");
                String nombre = partes[0];
                String unidad = partes[1];
                double stock = Double.parseDouble(partes[2]);
                double stockMinimo = Double.parseDouble(partes[3]);
                listaIng.add(new Ingrediente(nombre, unidad,stock,stockMinimo));
                
            }
        } catch (Exception e) {
            System.out.println("Error al leer inventario" + e.getMessage());
        }
        return listaIng;
    }
    
    public void guardarTodos(ArrayList<Ingrediente> lista) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for(Ingrediente ing : lista) {
                bw.write(ing.getNombre() + ";" + ing.getUnidad()
                + ";" + ing.getStock() + ";" +ing.getStockMinimo());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar inventario:" + e.getMessage());
        }
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
