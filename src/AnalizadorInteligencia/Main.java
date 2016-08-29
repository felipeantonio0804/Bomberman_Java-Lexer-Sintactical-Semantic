/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorInteligencia;

import AnalizadorEscenario.LexicoEscenario;
import AnalizadorEscenario.SintacticoEscenario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Felipe
 */
public class Main {
   public static void main(String[] args) throws Exception {
            String ruta="C:\\Users\\Felipe\\Downloads\\Proyecto_Dic-2013\\Entrada\\Algoritmos.ia";
            BufferedReader reader = new BufferedReader(new FileReader(new File(ruta)));
            LexicoInteligencia lexicoEscenario = new LexicoInteligencia(reader);
//            Symbol sim = l.next_token();
//                while(sim.sym!=0){
//                        sim=l.next_token();
//                }
            SintacticoInteligencia sintacticoEscenario = new SintacticoInteligencia(lexicoEscenario);
            
            System.out.println("----------------Inicio Analisis----------------");
            sintacticoEscenario.parse();
            
            System.out.println("----------------Fin Analisis----------------");
    }
}
