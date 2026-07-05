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
import modelo.Produccion;
import modelo.Receta;
import modelo.TipoHelado;
import util.ArchivoUtil;

/**
 *
 * @author maiam
 */
public class ProduccionDAO extends ArchivoDAO{
    
    
    public ProduccionDAO() {
        super("reportes.txt");
    }
    
    
    public void agregar(Produccion produccion) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            String linea = produccion.getFecha() + ";"
                    + produccion.getReceta().getTipoHelado().name() + ";"
                    + produccion.getReceta().getSabor() + ";" + 
                    produccion.getCantidad();
            bw.write(linea);
            bw.newLine();
            
        } catch (Exception e) {
            System.out.println("Error al guardar reporte" + e.getMessage());
        }
    }
    
    //se reconstruye receta para reportes.
    public ArrayList<Produccion> listarTodas() {
        ArrayList<Produccion> listaProd = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split("\\|");
                String fecha = partes[0];
                TipoHelado tipo = TipoHelado.valueOf(partes[1]);
                String sabor = partes[2];
                int cantidad = Integer.parseInt(partes[3]);
                
                Receta recetaSimple = new Receta(tipo, sabor);
                listaProd.add(new Produccion(recetaSimple,cantidad, fecha));
            }
            
        } catch (Exception e) {
            System.out.println("Error al leer reportes:" + e.getMessage());
        }
        return listaProd;
    }
    
    
    
}
