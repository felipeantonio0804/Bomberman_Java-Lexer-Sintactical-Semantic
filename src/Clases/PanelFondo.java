/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Felipe
 */
public class PanelFondo extends JPanel {
                  private Image imagenFondo = null;
	private boolean dibujaImagen = false;
        
	public PanelFondo(){
                        this.setLayout(null);
	}
	
	public void pintarFondo(String ruta){
                        imagenFondo = new ImageIcon(ruta).getImage();
                        dibujaImagen = true;
                        this.repaint();
	}
	
    @Override
	public void paint(Graphics g){
                        if(dibujaImagen ==true){
                            if(imagenFondo!=null){
                                this.setOpaque(false);
                                g.drawImage(imagenFondo, 0, 0,this.getWidth(),this.getHeight(),this);
                            }
                        }
                        else{
                            this.setOpaque(true);
                        }
                        super.paint(g);
	}
}
