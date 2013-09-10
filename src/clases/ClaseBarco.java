/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Henry
 */
public class ClaseBarco {
    String nombreClase;
    int longitud;
    String color;

    public ClaseBarco(String nombreClase, int longitud, String color) {
        this.nombreClase = nombreClase;
        this.longitud = longitud;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getLongitud() {
        return longitud;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }
    
    
}
