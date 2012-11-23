/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import pelotas.drawable.Boundary;
import pelotas.drawable.Composite;

/**
 *
 * @author David
 */
public class Renderer extends Thread {

    private boolean stopping;
    private Composite escena;
    private Graphics g;

    public Renderer(Composite escena, Graphics g) {
        this.escena = escena;
        this.g = g;
    }

    @Override
    public void run() {
        Image buffer;
        Graphics Pantalla;
        //Obtenemos los bordes del boundary para:
        //-Crear Buffer para el Graphics y para limpiar
        Boundary contorno = (Boundary) escena.getChild(0);
        buffer = new BufferedImage(
                contorno.getBoundarywidth(), contorno.getBoundaryheight(),
                BufferedImage.TYPE_INT_RGB);
        //Guardamos la Direccion de memoria de el Graphics del JApplet
        Pantalla = g;
        //Creamos un nuevo Graphics q no esta visible
        g = buffer.getGraphics();
        while (!stopping) {
            //Boundary contorno = (Boundary) escena.getChild(0);
            g.clearRect(//Limpiamos la pantalla
                    contorno.getBoundaryx(), contorno.getBoundaryy(),
                    contorno.getBoundarywidth(), contorno.getBoundaryheight());

            escena.render(g);
            //Tratamos el Fondo y mejoramos la Calidad de renderizado de los objetos
            Graphics2D g2 = (Graphics2D) g;
            g2.setBackground(Color.DARK_GRAY);
            RenderingHints qualityHints;
            qualityHints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(qualityHints);
            //Le pasamos el Dibujo completo al Graphics del JApplet evitamos parpadeo
            Pantalla.drawImage(buffer, 0, 0, null);
            try {
                Thread.sleep(10);
                //yield()
            } catch (InterruptedException e) {
                System.out.println("Error Exception");
                System.out.println(e.getMessage());
            }
        }
    }
}
