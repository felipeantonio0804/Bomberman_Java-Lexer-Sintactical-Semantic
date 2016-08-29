/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorRecursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author Felipe
 */
public class Main {
   public static void main(String[] args) throws Exception {
            String ruta="C:\\Users\\Felipe\\Downloads\\Proyecto_Dic-2013\\Entrada\\Recursos.rr";
            BufferedReader reader = new BufferedReader(new FileReader(new File(ruta)));
            
            System.out.println("---------------Inicio Analisis---------------");
               
            LexicoRecurso lexicoRecursos = new LexicoRecurso(reader);
//            Symbol sim = l.next_token();
//                while(sim.sym!=0){
//                        sim=l.next_token();
//                }
            SintacticoRecurso sintacticoRecursos = new SintacticoRecurso(lexicoRecursos);
            sintacticoRecursos.parse();
            System.out.println("----------------Fin Analisis----------------");
            System.out.println("---------------ERRORES LEXICOS---------------");
            for(int i=0;i<lexicoRecursos.getListaErrores().size();i++){
                System.out.println("Lexema: "+lexicoRecursos.getListaErrores().get(i).getLexema()+", Fila: "+lexicoRecursos.getListaErrores().get(i).getFila()+", Columna "+lexicoRecursos.getListaErrores().get(i).getColumna());
            }
            System.out.println("---------------ERRORES SINTACTICOS-SEMANTICOS---------------");
            for(int i=0;i<sintacticoRecursos.getListaErrores().size();i++){
                System.out.println("Lexema: "+sintacticoRecursos.getListaErrores().get(i).getLexema()+", Fila: "+sintacticoRecursos.getListaErrores().get(i).getFila()+", Columna "+sintacticoRecursos.getListaErrores().get(i).getColumna()+", Descripcion: "+sintacticoRecursos.getListaErrores().get(i).getDescripcion());
            }
            System.out.println("---------------TABLA SIMBOLOS---------------");
            for(int i=0;i<sintacticoRecursos.getListaVariables().size();i++){
                System.out.println(i+1+") TIPO: "+sintacticoRecursos.getListaVariables().get(i).getTipo()+"   ID: "+sintacticoRecursos.getListaVariables().get(i).getIdentificador()+"  IMAGEN: "+sintacticoRecursos.getListaVariables().get(i).getRuta()+"  CORAZONES: "+sintacticoRecursos.getListaVariables().get(i).getCorazones()+"  ALGORITMO: "+sintacticoRecursos.getListaVariables().get(i).getAlgoritmo()+"  DESTRUIBLE: "+sintacticoRecursos.getListaVariables().get(i).getDestruible());
            }
            
            
    }
}
