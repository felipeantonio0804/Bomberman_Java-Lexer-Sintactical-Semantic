/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Almacenamiento.Nodo;
import Almacenamiento.PosicionInicial;
import Almacenamiento.Variable;
import Ventana.Ventana;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe
 */
public class HiloEnemigo implements Runnable{
    private Thread hiloEnemigo =null;
    private HiloBombaEnemigo hiloBomba = null;
    private PosicionInicial enemigo = null;
    private Nodo raiz =null;
    int retardo;
    private boolean termino = false;
    
    public HiloEnemigo(){
        hiloEnemigo = new Thread(this);
        hiloBomba = new HiloBombaEnemigo();
    }
    
    public void setEnemigo(PosicionInicial enemigo) {
        this.enemigo = enemigo;
    }
    
    public void setNodoAlgoritmo(Nodo raiz){
        this.raiz = raiz;
    }
    
    public void comenzar() {
        if(hiloEnemigo!=null&&enemigo!=null&&raiz!=null){
            if(termino==false){
                hiloEnemigo.start();
            }
        }
        else{
            mostrar("HILO NO COMENZO");
        }
    }
    
    //SUPONIENDO QUE EL NODO QUE LE MANDO ES EL DE CUERPO DESDE EL INICIO 
    private void ejecutar(Nodo instrucciones) throws Exception {
        try{
            int i = 0;
            Nodo instruccion;
            while (i < instrucciones.hijos.size()) {
                instruccion = instrucciones.hijos.get(i);

                if (instruccion.valor.equals("IF")) {
                    if (evaluarExpresion(instruccion.hijos.get(0))==false) {//CONDICION
                        mostrar(enemigo.getVariable().getIdentificador()+"_VINO IF");
                        if(instruccion.hijos.get(1)!=null){
                            ejecutar(instruccion.hijos.get(1));//RECURSIVO Y ESTE LA MANDA  NODO CUERPO
                        }    
                    } 
                    else {
                        if (instruccion.hijos.size() == 3) {
                            //PARA CUANDO HALLA ELSE IFS Y ELSE
                            ejecutar(instruccion.hijos.get(2));//NODO OPCIONES
                        }
                    }
                }

                else if (instruccion.valor.equals("WHILE")) {
                    while (evaluarExpresion(instruccion.hijos.get(0))==false) {//CONDICION
                        mostrar(enemigo.getVariable().getIdentificador()+"_VINO WHILE");
                        ejecutar(instruccion.hijos.get(1));//RECURSIVO Y ESTE LE MANDA NODO CUERPO
                    }
                }

                else if (instruccion.valor.equals("MOVE")) {
                    mostrar(enemigo.getVariable().getIdentificador()+"_Move: "+instruccion.hijos.get(0).valor);
                    Thread.sleep(retardo);
                    String direccion = "abajo";
                    if("+Y".equals(instruccion.hijos.get(0).valor)){
                        direccion = "arriba";
                    }
                    else if("-Y".equals(instruccion.hijos.get(0).valor)){
                        direccion = "abajo";
                    }
                    if("+X".equals(instruccion.hijos.get(0).valor)){
                        direccion = "derecha";
                    }
                    if("-X".equals(instruccion.hijos.get(0).valor)){
                        direccion = "izquierda";
                    }
                    if(comprobarMovimiento(direccion)==false){
                        modificarMatrizYRepintarLabel(direccion);
                    }
                }
                else if (instruccion.valor.equals("BOMBA")) {
                     mostrar("Bomba");
                    Thread.sleep(retardo);
                     hiloBomba.comienza(enemigo.getX(),enemigo.getY());
                }

                else if (instruccion.valor.equals("ESPERA")) {
                    mostrar("Espera: "+instruccion.hijos.get(0).valor);
                    int otroRetardo = (int) (Double.parseDouble(instruccion.hijos.get(0).valor)*1000.00);
                    Thread.sleep(otroRetardo);
                    Thread.sleep(retardo);
                }

                else if(instruccion.valor.equals("ELSE IF")){
                    if (evaluarExpresion(instruccion.hijos.get(0))==false) {//CONDICION
                        mostrar("VINO ELSE IF");
                        if(instruccion.hijos.get(1)!=null){
                            ejecutar(instruccion.hijos.get(1));//RECURSIVO Y ESTE LA MANDA  NODO CUERPO     
                        }
                    i = instrucciones.hijos.size();//PARA QUE PARE Y NO SIGA CORRIENDO EN LA LISTA DE LOS DEMAS ELSE IF O ELSE 
                    } 
                }
                else if(instruccion.valor.equals("ELSE")){
                    mostrar("VINO ELSE");
                    if(instruccion.hijos.get(0)!=null){
                        ejecutar(instruccion.hijos.get(0));//PORQUE NO TIENE CONDICION
                    } 
                }
                i++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private boolean evaluarExpresion(Nodo nodo) throws Exception {
        Nodo auxiliar = nodo.hijos.get(0);      
        if(auxiliar.valor.equals("+X")){
            return comprobarMovimiento("derecha");
        }
        else if(auxiliar.valor.equals("-X")){
            return comprobarMovimiento("izquierda");
        }
        else if(auxiliar.valor.equals("+Y")){
            return comprobarMovimiento("arriba");
        }
        else if(auxiliar.valor.equals("-Y")){
            return comprobarMovimiento("abajo");
        }
        return true;//que esta ocupada
    }
    
    private void mostrar(String cadena){
        System.out.println(cadena);
    }

    private boolean comprobarMovimiento(String direccion) {
        try{
            int posicionActualX = enemigo.getX();
            int posicionActualY = enemigo.getY();
            int incrementoX =   0;
            int incrementoY =   0;
            
            if("arriba".equals(direccion)){
                incrementoX = 0;
                incrementoY = 1;
            }
            
            else if("abajo".equals(direccion)){
                incrementoX = 0;
                incrementoY = -1;
            }
             
            else  if("izquierda".equals(direccion)){
                incrementoX = -1;
                incrementoY = 0;
            }
             
            else  if("derecha".equals(direccion)){
                incrementoX = 1;
                incrementoY = 0;
            }
            
            if((posicionActualX+incrementoX>=1)&&(posicionActualX+incrementoX<=15)&&(posicionActualY+incrementoY>=1)&&(posicionActualY+incrementoY<=15)){
                if(Ventana.principales[posicionActualX+incrementoX][posicionActualY+incrementoY]==null){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return true;
            }
                      
        }
        catch(Exception e){//PARA CUANDO SE DE UN NULL POINTER EXCEPTION POR LAS POSICIONES DE LA MATRIZ PRINCIPAL
            return true;
        }
    }

    private void modificarMatrizYRepintarLabel(String direccion) {
            int posicionActualX = enemigo.getX();
            int posicionActualY = enemigo.getY();
            int incrementoX = 0;
            int incrementoY = 0;
            
            if("arriba".equals(direccion)){
               incrementoX = 0;
               incrementoY = 1;
            }
            
            else if("abajo".equals(direccion)){
               incrementoX = 0;
               incrementoY = -1;
            }
             
            else  if("izquierda".equals(direccion)){
               incrementoX = -1;
               incrementoY = 0;
            }
             
            else  if("derecha".equals(direccion)){
               incrementoX = 1;
               incrementoY = 0;
            }
            
             Ventana.principales[posicionActualX][posicionActualY] = null;//quito de la matriz
             Ventana.principales[posicionActualX+incrementoX][posicionActualY+incrementoY] = enemigo.getVariable();//lo muevo a la posicion en que se movio en la matriz
             if(Ventana.secundarias[posicionActualX][posicionActualY]!=null){//si en dado caso hubiera un power up
                pintarLabel(Ventana.casillasJuego[posicionActualX][posicionActualY],Ventana.secundarias[posicionActualX][posicionActualY]);//lo muestro
             }
             else{
                 pintarLabel(Ventana.casillasJuego[posicionActualX][posicionActualY],"");//no muestro nada
             }
             pintarLabel(Ventana.casillasJuego[posicionActualX+incrementoX][posicionActualY+incrementoY],enemigo.getVariable());//pinto en la siguiente posicion a que se movio
             //nuevas coordenadas del enemigo
             enemigo.setX(posicionActualX+incrementoX);
             enemigo.setY(posicionActualY+incrementoY);
    }
    
   private void pintarLabel(JLabel etiqueta, Variable variable){
                ImageIcon imagen = new ImageIcon(quitarComillas(variable.getRuta()));
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(etiqueta.getWidth(),etiqueta.getHeight(), Image.SCALE_DEFAULT));
                etiqueta.setIcon(icono);
    }
    
    private void pintarLabel(JLabel etiqueta, String rutaImagen){
                ImageIcon imagen = new ImageIcon(rutaImagen);
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(etiqueta.getWidth(),etiqueta.getHeight(), Image.SCALE_DEFAULT));
                etiqueta.setIcon(icono);
    } 
   
    private String quitarComillas(String ruta){
        String cadena = ruta.substring(1, ruta.length()-1);
        return cadena;
    }
    
    @Override
    public void run() {
        try{
            //while(PanelFondoEvento.finJuego==false){//DEBERIA HABER UNA BANDERA DE MUERTO EN EL ENEMIGO EN EL PANEL DE FONDO EVENTO
               Nodo algoritmo = raiz;
               if(algoritmo.hijos.get(1)!=null){
                   retardo = (int) (Double.parseDouble(algoritmo.hijos.get(0).valor)*1000.00);
                   ejecutar(algoritmo.hijos.get(1));
               }
             //}
               termino =true;
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    
}
