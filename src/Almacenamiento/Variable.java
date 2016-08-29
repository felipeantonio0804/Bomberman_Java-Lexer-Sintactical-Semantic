/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Almacenamiento;

/**
 *
 * @author Felipe
 */
public class Variable {
    private String identificador;
    private String tipo;
    private String[] atributos;
   
    public Variable(String identificador, String tipo){
        this.identificador = identificador;
        this.tipo = tipo;
        atributos = new String [4] ;
    }
    
    public Variable(String identificador, String tipo,String ruta){
        this.identificador = identificador;
        this.tipo = tipo;
        atributos = new String [4] ;
        atributos[0] = ruta;
    }
    
    public void setRuta(String ruta){
         atributos[0] = ruta;
    }
    
    public String getRuta(){
         return  atributos[0];
    }
    
     public void setCorazones(String corazones){
         atributos[1] = corazones;
    }
    
    public String getCorazones(){
         return  atributos[1];
    }
    
    public void setAlgoritmo(String algoritmo){
         atributos[2] = algoritmo;
    }
    
    public String getAlgoritmo(){
         return  atributos[2];
    }
    
    public void setDestruible(String destruible){
         atributos[3] = destruible;
    }
    
    public String getDestruible(){
         return  atributos[3];
    }
    
    public String getIdentificador() {
        return identificador;
    }

    public String getTipo() {
        return tipo;
        
    }
}
