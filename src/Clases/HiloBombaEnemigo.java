/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Almacenamiento.PosicionInicial;
import Almacenamiento.Variable;
import Ventana.Ventana;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Felipe
 */
public class HiloBombaEnemigo implements Runnable {
 private boolean ejecutandoBomba = false;
 private Thread hiloBomba = null;
 private int x=0,y=0;
 
    public HiloBombaEnemigo(){
        hiloBomba = new Thread(this);
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
    
    private void ejecutarDestruccion(String tipoBomba) {
            destruirEnPosicion(x, y+1);//arriba
            destruirEnPosicion(x, y-1);//abajo
            destruirEnPosicion(x+1,y);//derecha
            destruirEnPosicion(x-1,y);//izquierda
                         
            Ventana.secundarias[x][y] = null; //quito de la matriz secundaria la bomba
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

    private void quitarVida(int x, int y,String tipo) {
            if(Integer.parseInt(Ventana.principales[x][y].getCorazones())>=2){
                int corazones = Integer.parseInt(Ventana.principales[x][y].getCorazones());
                corazones--;
                Ventana.principales[x][y].setCorazones(corazones+"");//le quito un corazon;
                if("enemigo".equals(tipo)){
                    cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y]);
                }
                else{//es el heroe
                    cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y],PanelFondoEvento.fuegoHeroe,PanelFondoEvento.bombaHeroe,PanelFondoEvento.bombaRHeroe);
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
                        PanelFondoEvento.finJuego = true;
                        mostrar("FIN DE JUEGO LOS ENEMIGOS HAN CAIDO.");
                    }
                }
                else{
                     cambiarInformacionLabel(Ventana.detalleObjetos,Ventana.principales[x][y],PanelFondoEvento.fuegoHeroe,PanelFondoEvento.bombaHeroe,PanelFondoEvento.bombaRHeroe);
                     PanelFondoEvento.finJuego = true;
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
        PanelFondoEvento.numeroEnemigos--;
        if(PanelFondoEvento.numeroEnemigos==0){
            return true;
        }
        else{
            return false;
        }
    }

    public void comienza(int x, int y){
        this.x = x;
        this.y = y;        
        if(ejecutandoBomba==false){
            ponerBomba();
        }
    }
    @Override
    public void run() {
        try{
                Ventana.secundarias[x][y] = new Variable("temporal","temporal","\"Imagenes/explota.gif\"");
                Thread.sleep(3700);
                ejecutarDestruccion("bomba");
                ejecutandoBomba = false;
                System.out.println("TERMINO PONER BOMBA");
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void mostrar(String cadena){
        System.out.println(cadena);
    }
}
