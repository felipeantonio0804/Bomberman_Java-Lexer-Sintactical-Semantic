/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;

/**
 *
 * @author Felipe
 */
public class Metodo {

    private String identificador;
    private String  retardo;
    //private String numeroInstrucciones;
    
    public Metodo(String identificador, String  retardo){
        this.identificador = identificador;
        this.retardo = retardo;
        //this.numeroInstrucciones = "0";
    }
    
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getRetardo() {
        return retardo;
    }

    public void setRetardo(String retardo) {
        this.retardo = retardo;
    }
    
}
