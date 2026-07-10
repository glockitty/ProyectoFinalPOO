/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.File;
import java.io.IOException;
import util.ArchivoUtil;

/**
 *
 * @author maiam
 */
public abstract class ArchivoDAO {
    protected final File archivo;
    
    public ArchivoDAO(String nombreArchivo) throws IOException {
        ArchivoUtil.crearCarpetaSiNoExiste();
        archivo = new File(ArchivoUtil.Carpeta,nombreArchivo);
        crearArchivoSiNoExiste();
    }
    
    public void crearArchivoSiNoExiste() throws IOException {
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("No se logro crear el archivo" + e.getMessage());
        }
    }
}
