/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Almacenamiento.PosicionInicial;
import Almacenamiento.Posicionamiento;
import Almacenamiento.Variable;
import Ventana.Ventana;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe
 */
public class EventoBotonTablero implements ActionListener{
    private Variable variable = null;
    private Variable variableSecundaria = null;
    private int x,y;
    
    public EventoBotonTablero(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public EventoBotonTablero(int x, int y, Variable variable) {
        this.x = x;
        this.y = y;
        this.variable = variable;
        //if(variableEventoEditorEscenarios.getTipo().toLowerCase()!="fuego")
        pintarBoton(variable,x,y);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         if(Ventana.variableEventoEditorEscenarios != null&&(this.variable==null||this.variableSecundaria==null)){
            String tipo = Ventana.variableEventoEditorEscenarios.getTipo();  
            if(("heroe".equals(tipo)||"enemigo".equals(tipo)||"obstaculo".equals(tipo))&&(this.variable==null)){
                if ("heroe".equals(tipo)){
                        if(Ventana.primerHeroe==null){
                            this.variable = Ventana.variableEventoEditorEscenarios;
                            pintarBoton(this.variable,x,y);
                            agregarAListaPosiciones(variable.getIdentificador(),x,y);
                            Ventana.primerHeroe = this.variable;
                            System.out.println("ASIGNO PRINCIPAL");
                        }
                        else{
                            mostrar("UN HEROE YA FUE POSICIONADO");
                        }
                }
                else{
                        this.variable = Ventana.variableEventoEditorEscenarios;
                        pintarBoton(this.variable,x,y);
                        agregarAListaPosiciones(variable.getIdentificador(),x,y);
                        System.out.println("ASIGNO PRINCIPAL");
                }
            }
            
            else if(("corazon".equals(tipo)||"fuego".equals(tipo)||"bomba".equals(tipo)||"bombar".equals(tipo))&&(this.variable!=null&&this.variableSecundaria==null)){
               if("obstaculo".equals(variable.getTipo())&& "v".equals(variable.getDestruible().toLowerCase())){
                    this.variableSecundaria = Ventana.variableEventoEditorEscenarios;
                    agregarAListaPosiciones(variableSecundaria.getIdentificador(),x,y);
                    Ventana.casillasEditorEscenarios[x][y].setToolTipText(x+"-"+y+". "+tipo);
//                    Ventana.secundarias[x][y] = this.variableSecundaria;
                    System.out.println("ASIGNO SECUNDARIA");
               }
               else{
                   mostrar("NO SE ASIGNO POWERUP POR QUE PRINCIPAL NO ES OBSTACULO DESTRUIBLE");
               }
            }

            Ventana.variableEventoEditorEscenarios = null;
         }
         
         else{
             Ventana.variableEventoEditorEscenarios = null;
             mostrar("LAS DOS POSICIONES LLENAS O NO HAY CARGADA");
         }
    }
        
    private void pintarBoton(Variable variable, int x, int y){
                ImageIcon imagen = new ImageIcon(quitarComillas(variable.getRuta()));
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
                Ventana.casillasEditorEscenarios[x][y].setIcon(icono);
    }
    
     private String quitarComillas(String ruta){
        String cadena = ruta.substring(1, ruta.length()-1);
        return cadena;
    }

    private void agregarAListaPosiciones(String identificador, int x, int y) {
        Ventana.listaPosiciones.add(new Posicionamiento(identificador,x,y));
    }
    
    public Variable getVariablePrincipal() {
        return variable;
    }

    public Variable getVariableSecundaria() {
        return variableSecundaria;
    }
    
    public void setVariableSecundaria(Variable variableSecundaria){
        this.variableSecundaria = variableSecundaria;
    }
    
    public void mostrar(String cadena){
        JOptionPane.showMessageDialog(null,cadena);
    }
}
