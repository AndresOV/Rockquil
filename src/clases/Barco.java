/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Henry
 */
public class Barco {
    String nombre;
    String clase;

    public Barco(String nombre, String clase) {
        this.nombre = nombre;
        this.clase = clase;
    }

    public String getClase() {
        return clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
