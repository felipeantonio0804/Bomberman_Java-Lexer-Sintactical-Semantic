/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;

/**
 *
 * @author Felipe
 */
public class ErrorSintactico {
     private String lexema;
    private int columna;
    private int fila;
    private String descripcion;

    public ErrorSintactico(String lexema,int fila,int columna,String descripcion){
        this.lexema = lexema;
        this.columna = columna;
        this.fila = fila;
        this.descripcion = descripcion;
    }
    
     public int getColumna() {
        return columna;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getFila() {
        return fila;
    }

    public String getLexema() {
        return lexema;
    }
}
