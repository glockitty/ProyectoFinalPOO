/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;

/**
 *
 * @author maiam
 */
public class ArchivoUtil {
    public static final String Carpeta = "bdatos";
    
    public static void crearCarpetaSiNoExiste() {
            File carpeta = new File(Carpeta);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
        }
    
}
