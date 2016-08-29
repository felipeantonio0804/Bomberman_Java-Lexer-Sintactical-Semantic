/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;

/**
 *
 * @author Felipe
 */
public class Posicionamiento {
    private String identificador;
    private int x,y;
    
    public Posicionamiento(String identificador, int x, int y){
        this.identificador = identificador;
        this.x = x;
        this.y = y;
    }
    
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
