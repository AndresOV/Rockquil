/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.*;

/**
 *
 * @author diearbri
 */
public class Imagenes {
    public Image cargar (String ruta){
        return Toolkit.getDefaultToolkit().createImage((getClass().getResource(ruta)));
        
  
    }
    
}
