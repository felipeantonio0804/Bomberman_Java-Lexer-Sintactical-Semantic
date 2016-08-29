/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;

import java.util.HashMap;

/**
 *
 * @author Felipe
 */
public class ListaMetodos {
    private HashMap<String,Nodo> metodos;
    
    public ListaMetodos(){
        metodos = new HashMap<String,Nodo>();
    }
    
    public Nodo buscar(String nombre){
        return metodos.get(nombre.toLowerCase());
    }

    public void agregar(String nombre,Nodo metodo){
        metodos.put(nombre.toLowerCase(),metodo);
    }
}
