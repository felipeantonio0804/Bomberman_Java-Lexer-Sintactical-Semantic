/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;

/**
 *
 * @author Felipe
 */
public class PosicionInicial {
    private Variable variable;
    private String tipo;
    private int x;
    private int y;
           
    public PosicionInicial(Variable variable,String tipo,int x,int y){
        this.variable = variable;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
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
