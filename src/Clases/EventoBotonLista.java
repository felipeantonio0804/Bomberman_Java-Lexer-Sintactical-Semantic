/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Almacenamiento.Variable;
import Ventana.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Felipe
 */
public class EventoBotonLista implements ActionListener {
    private Variable variable = null;
    
    public EventoBotonLista(Variable variable) {
        this.variable = variable;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         if(Ventana.variableEventoEditorEscenarios == null){
            Ventana.variableEventoEditorEscenarios= variable;
            System.out.println("VARIABLE CARGADA");
         }
         else{
             System.out.println("YA HAY VARIABLE CARGADA");
         }
    }
}
