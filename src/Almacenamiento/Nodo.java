/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class Nodo {
    public String valor;
    public ArrayList<Nodo> hijos;

    public Nodo(String valor) {
        this.valor = valor;
        hijos = new ArrayList<Nodo>();
    }
    
}
