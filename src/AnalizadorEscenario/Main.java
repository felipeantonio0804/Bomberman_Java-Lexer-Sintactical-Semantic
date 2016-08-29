package AnalizadorEscenario;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Felipe
 */
public class Main {
   public static void main(String[] args) {
       try{
            String ruta="C:\\Users\\Felipe\\Downloads\\Proyecto_Dic-2013\\Entrada\\Escenario1.maze";
            BufferedReader reader = new BufferedReader(new FileReader(new File(ruta)));
            
            
                
            System.out.println("---------------Inicio Analisis---------------");
               
            LexicoEscenario lexicoEscenario = new LexicoEscenario(reader);
//            Symbol sim = l.next_token();
//                while(sim.sym!=0){
//                        sim=l.next_token();
//                }
            SintacticoEscenario sintacticoEscenario = new SintacticoEscenario(lexicoEscenario);
            sintacticoEscenario.parse();
            System.out.println("----------------Fin Analisis----------------");
            System.out.println("---------------ERRORES LEXICOS---------------");
            for(int i=0;i<lexicoEscenario.getListaErrores().size();i++){
                System.out.println("Lexema: "+lexicoEscenario.getListaErrores().get(i).getLexema()+", Fila: "+lexicoEscenario.getListaErrores().get(i).getFila()+", Columna "+lexicoEscenario.getListaErrores().get(i).getColumna());
            }
            System.out.println("---------------ERRORES SINTACTICOS-SEMANTICOS---------------");
            for(int i=0;i<sintacticoEscenario.getListaErrores().size();i++){
                System.out.println("Lexema: "+sintacticoEscenario.getListaErrores().get(i).getLexema()+", Fila: "+sintacticoEscenario.getListaErrores().get(i).getFila()+", Columna "+sintacticoEscenario.getListaErrores().get(i).getColumna()+", Descripcion: "+sintacticoEscenario.getListaErrores().get(i).getDescripcion());
            }
       }
       catch(Exception e){
           System.out.println("error");
       }
    }
}
