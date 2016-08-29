/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Almacenamiento.PosicionInicial;
import Almacenamiento.Variable;
import Ventana.Ventana;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 *
 * @author Felipe
 */
public class PanelFondoEvento extends PanelFondo implements KeyListener, MouseListener, Runnable{
    public static int numeroEnemigos;
    private PosicionInicial posicionHeroe= null;
    private Thread hiloBomba = null;
    private int xBomba =0, yBomba=0, xBombaR = 0, yBombaR = 0;
    private boolean ejecutandoBomba = false;
    private boolean puestaBombaR = false;
    public static int fuegoHeroe = 1;
    public static int bombaRHeroe = 0;
    public static int bombaHeroe = 1;
    public static boolean finJuego = false;
    
    public PanelFondoEvento(){
        this.addKeyListener(this);
        this.addMouseListener(this);
    }
    
    public void setValoresDefaultHeroe(){
        fuegoHeroe =1;
        bombaRHeroe = 0;
        bombaHeroe =1;
        finJuego = false;
        posicionHeroe = null;
        hiloBomba = null;
        xBomba = 0;
        yBomba =0;
        xBombaR =0;
        yBombaR = 0;
        ejecutandoBomba = false;
        puestaBombaR = false;
    }
    
    public void setPosicionInicial(PosicionInicial posicionInicialHeroe){
        this.posicionHeroe = posicionInicialHeroe;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(finJuego == false){
            if(posicionHeroe!=null){
                String evento = e.getKeyText(e.getKeyCode());
                switch(evento){
                    case "W":{
                        //mostrar("arriba"); 
                        if(comprobarMovimiento("arriba")){
                            modificarMatrizYRepintarLabel("arriba");
                        }
                        break;
                    }
                    case "S":{
                        //mostrar("abajo"); 
                        if(comprobarMovimiento("abajo")){
                            modificarMatrizYRepintarLabel("abajo");
                        }
                        break;
                    }
                    case "A":{
                        //mostrar("izquierda");
                        if(comprobarMovimiento("izquierda")){
                            modificarMatrizYRepintarLabel("izquierda");
                        }
                        break;
                    }
                    case "D":{
                        //mostrar("derecha");
                        if(comprobarMovimiento("derecha")){
                            modificarMatrizYRepintarLabel("derecha");
                        }
                        break;
                    }

                    case "H":{
                        if(ejecutandoBomba == false){
                            xBomba = posicionHeroe.getX();
                            yBomba = posicionHeroe.getY();
                            ponerBomba();
                        }
                        break;
                    }

                    case "J":{
                        if(bombaRHeroe>=1){
                            if(puestaBombaR==false){
                                xBombaR = posicionHeroe.getX();
                                yBombaR = posicionHeroe.getY();
                                Ventana.secundarias[xBombaR][yBombaR] = new Variable("temporal","temporal","\"Imagenes/explota2.gif\"");
                                puestaBombaR = true;
                            }
                            else{
                                ejecutarDestruccion("bombar");
                                puestaBombaR = false;
                            }
                        }
                        break;
                    }   
                }
                comprobarAgarraPowerUp(posicionHeroe);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        this.requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent e) {}

    private void mostrar(String cadena) {
        JOptionPane.showMessageDialog(null,cadena);
    }

    private boolean comprobarMovimiento(String direccion) {
        try{
            int posicionActualX = posicionHeroe.getX();
            int posicionActualY = posicionHeroe.getY();
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
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
                      
        }
        catch(Exception e){//PARA CUANDO SE DE UN NULL POINTER EXCEPTION POR LAS POSICIONES DE LA MATRIZ PRINCIPAL
            return false;
        }
    }

    private void modificarMatrizYRepintarLabel(String direccion) {
            int posicionActualX = posicionHeroe.getX();
            int posicionActualY = posicionHeroe.getY();
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
             Ventana.principales[posicionActualX+incrementoX][posicionActualY+incrementoY] = posicionHeroe.getVariable();//lo muevo a la posicion en que se movio en la matriz
             if(Ventana.secundarias[posicionActualX][posicionActualY]!=null){//si en dado caso hubiera un power up
                pintarLabel(Ventana.casillasJuego[posicionActualX][posicionActualY],Ventana.secundarias[posicionActualX][posicionActualY]);//lo muestro
             }
             else{
                 pintarLabel(Ventana.casillasJuego[posicionActualX][posicionActualY],"");//no muestro nada
             }
             pintarLabel(Ventana.casillasJuego[posicionActualX+incrementoX][posicionActualY+incrementoY],posicionHeroe.getVariable());//pinto en la siguiente posicion a que se movio
             //nuevas coordenadas del heroe
             posicionHeroe.setX(posicionActualX+incrementoX);
             posicionHeroe.setY(posicionActualY+incrementoY);
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
    
    private void cambiarInformacionLabel(JLabel[] etiquetas, Variable variable, int fuego, int bomba, int bombar){
        for(int i=0;i<Ventana.listaPosicionesIniciales.size();i++){
                if("heroe".equals(Ventana.listaPosicionesIniciales.get(i).getTipo())){
                    etiquetas[i].setToolTipText("<html>Identificador: "+variable.getIdentificador()+"<br>Corazones: "+variable.getCorazones()+"<br>Fuego: "+fuego+"<br>Bomba: "+bomba+"<br>BombaR: "+bombar+"</html>");
                }
        }
    }
    
    private void cambiarInformacionLabel(JLabel[] etiquetas, Variable variable){
            for(int i=0;i<Ventana.listaPosicionesIniciales.size();i++){
                if("enemigo".equals(Ventana.listaPosicionesIniciales.get(i).getTipo())&&variable.getIdentificador().equals(Ventana.listaPosicionesIniciales.get(i).getVariable().getIdentificador())){
                    etiquetas[i].setToolTipText("<html>Identificador: "+variable.getIdentificador()+"<br>Corazones: "+variable.getCorazones()+"</html>");
                }
            }
    }
        
    private String quitarComillas(String ruta){
        String cadena = ruta.substring(1, ruta.length()-1);
        return cadena;
    }

    
    public void ponerBomba(){
        System.out.println("EMPEZO PONER BOMBA");
        ejecutandoBomba = true;
        hiloBomba = new Thread(this);
        hiloBomba.start();
    }
    
    @Override
    public void run() {
        try{
            Ventana.secundarias[xBomba][yBomba] = new Variable("temporal","temporal","\"Imagenes/explota.gif\"");
            Thread.sleep(3700);
            ejecutarDestruccion("bomba");
            ejecutandoBomba = false;
            System.out.println("TERMINO PONER BOMBA");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void ejecutarDestruccion(String tipoBomba) {
            int x =0;
            int y=0;
            if(tipoBomba.equals("bomba")){
                x = xBomba;
                y = yBomba;
            }
            else{//bombar
                x = xBombaR;
                y = yBombaR;
            }
            
            destruirEnPosicion(x, y+1);//arriba
            destruirEnPosicion(x, y-1);//abajo
            destruirEnPosicion(x+1,y);//derecha
            destruirEnPosicion(x-1,y);//izquierda
                         
            Ventana.secundarias[x][y] =null; //quito de la matriz secundaria la bomba
            pintarLabel(Ventana.casillasJuego[x][y],"");//se quita la bomba de la posicion
    }

    private void destruirEnPosicion(int x, int y) {
         if(Ventana.principales[x][y]!=null){//determinada posicion
             if(Ventana.principales[x][y].getTipo().equals("obstaculo")&&Ventana.principales[x][y].getDestruible().equals("f")){
                 //no hace nada con los obstaculos que no son destruibles
             }
             else{
                if(Ventana.principales[x][y].getTipo().equals("enemigo")){
                    quitarVida(x,y,"enemigo");                    
                }
                else if(Ventana.principales[x][y].getTipo().equals("heroe")){
                    quitarVida(x,y,"heroe");    
                }
                else{//es obstaculo destruible
                    quitarDePosicion(x,y);
                }
             }
        } 
    }

    private void comprobarAgarraPowerUp(PosicionInicial posicionHeroe) {
        int xHeroe = posicionHeroe.getX();
        int yHeroe = posicionHeroe.getY();
        if(Ventana.secundarias[xHeroe][yHeroe]!=null){
            Variable secundaria = Ventana.secundarias[xHeroe][yHeroe];
            if("fuego".equals(secundaria.getTipo())){
                if(fuegoHeroe<=4){
                    this.fuegoHeroe++;
                }
                Ventana.secundarias[xHeroe][yHeroe] = null;//quito el power up de la matriz secundaria
            }
            else if("bomba".equals(secundaria.getTipo())){
                if(bombaHeroe<=4){
                    this.bombaHeroe++;
                }
                Ventana.secundarias[xHeroe][yHeroe] = null;//quito el power up de la matriz secundaria
            }
            else if("bombar".equals(secundaria.getTipo())){
                if(bombaRHeroe<=0){
                    this.bombaRHeroe++;
                }
                Ventana.secundarias[xHeroe][yHeroe] = null;//quito el power up de la matriz secundaria
            }
            else if("corazon".equals(secundaria.getTipo())){
                try{
                    int corazones = Integer.parseInt(posicionHeroe.getVariable().getCorazones());
                    corazones++;
                    posicionHeroe.getVariable().setCorazones(corazones+"");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                Ventana.secundarias[xHeroe][yHeroe] = null;//quito el power up de la matriz secundaria
            }
            cambiarInformacionLabel(Ventana.detalleObjetos, posicionHeroe.getVariable(), fuegoHeroe,bombaHeroe, bombaRHeroe);
            
        }
    }

    private void quitarVida(int x, int y,String tipo) {
            if(Integer.parseInt(Ventana.principales[x][y].getCorazones())>=2){
                int corazones = Integer.parseInt(Ventana.principales[x][y].getCorazones());
                corazones--;
                Ventana.principales[x][y].setCorazones(corazones+"");//le quito un corazon;
                if("enemigo".equals(tipo)){
                    cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y]);
                }
                else{//es el heroe
                     cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y],fuegoHeroe,bombaHeroe,bombaRHeroe);
                }
            }
            else{
                int corazones = Integer.parseInt(Ventana.principales[x][y].getCorazones());
                corazones--;
                Ventana.principales[x][y].setCorazones(corazones+"");//le quito un corazon;
                if("enemigo".equals(tipo)){
                    cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y]);
                    boolean ultimoEnemigo = disminuirEnemigos();
                    if(ultimoEnemigo == true){
                        finJuego = true;
                        mostrar("FIN DE JUEGO LOS ENEMIGOS HAN CAIDO.");
                    }
                }
                else{
                     cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y],fuegoHeroe,bombaHeroe,bombaRHeroe);
                     finJuego = true;
                     mostrar("EL HEROE A MUERTO, FIN DEL JUEGO.");
                }
                quitarDePosicion(x,y);
            }
    }

    private void quitarDePosicion(int x, int y) {
            Ventana.principales[x][y]=null;//quito
            if(Ventana.secundarias[x][y]!=null){//si hay algo en secundaria
                pintarLabel(Ventana.casillasJuego[x][y],Ventana.secundarias[x][y]);//lo muestro
            }
            else{
                pintarLabel(Ventana.casillasJuego[x][y],"");//lo dejo vacio
            }
    }

    private boolean disminuirEnemigos() {
        numeroEnemigos--;
        if(numeroEnemigos==0){
            return true;
        }
        else{
            return false;
        }
    }

}
