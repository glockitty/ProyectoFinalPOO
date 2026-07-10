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
import modelo.Produccion;
import modelo.Receta;
import modelo.TipoHelado;

/**
 *
 * @author maiam
 */
public class ProduccionDAO extends ArchivoDAO{
    
    
    public ProduccionDAO() throws IOException {
        super("reportes.txt");
    }
    
    
    public void agregar(Produccion produccion) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo,true))) {
            bw.write(convertirALinea(produccion));
            bw.newLine();
            
        } catch (IOException e) {
            System.err.println("Error al guardar reporte" + e.getMessage());
        }
    }
    
    private String convertirALinea(Produccion produccion) {
        return produccion.getFecha() + ";"
                    + produccion.getReceta().getTipoHelado().name() + ";"
                    + produccion.getReceta().getSabor() + ";" 
                    + produccion.getCantidad();
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
                String[] partes = linea.split(";");
                String fecha = partes[0];
                TipoHelado tipo = TipoHelado.valueOf(partes[1]);
                String sabor = partes[2];
                int cantidad = Integer.parseInt(partes[3]);
                
                Receta recetaSimple = new Receta(tipo, sabor);
                listaProd.add(new Produccion(recetaSimple,cantidad, fecha));
            }
            
        } catch (IOException e) {
            System.out.println("Error al leer reportes:" + e.getMessage());
        }
        return listaProd;
    }
    
    
    
}
