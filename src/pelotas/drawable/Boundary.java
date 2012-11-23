/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.drawable;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author David
 */
@SuppressWarnings("serial")
public class Boundary implements Drawable {

    private int boundaryx, boundaryy;
    private int boundarywidth, boundaryheight;

    //Constructor
    public Boundary(int boundaryx, int boundaryy, int boundarywidth, int boundaryheight) {
        this.boundaryx = boundaryx;
        this.boundaryy = boundaryy;
        this.boundarywidth = boundarywidth;
        this.boundaryheight = boundaryheight;
    }

    //Getters and Setters
    public int getBoundaryx() {
        return boundaryx;
    }

    public void setBoundaryx(int boundaryx) {
        this.boundaryx = boundaryx;
    }

    public int getBoundaryy() {
        return boundaryy;
    }

    public void setBoundaryy(int boundaryy) {
        this.boundaryy = boundaryy;
    }

    public int getBoundarywidth() {
        return boundarywidth;
    }

    public void setBoundarywidth(int boundarywidth) {
        this.boundarywidth = boundarywidth;
    }

    public int getBoundaryheight() {
        return boundaryheight;
    }

    public void setBoundaryheight(int boundaryheight) {
        this.boundaryheight = boundaryheight;
    }

    //Operaciones
    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(
                this.getBoundaryx(), this.getBoundaryy(),
                this.getBoundarywidth(), this.getBoundaryheight());
    }

    @Override
    public void add(Drawable d) {
        System.out.println("add: Una contorno no puede tener Drawable");
    }

    @Override
    public void remove(Drawable d) {
        System.out.println("remove: Una contorno no puede tener Drawable");
    }

    @Override
    public Drawable getChild(int i) {
        System.out.println("getChild: Una contorno no puede tener Drawable");
        return null;

    }

    @Override
    public Drawable colision(Drawable d) {
        Ball ball;
        ball = (Ball) d;
        int centroX = ball.getPosballx() + ball.getRadio();
        int centroY = ball.getPosbally() + ball.getRadio();
        int radio = ball.getRadio();
        boolean colision = false;

        if (centroX - radio <= this.getBoundaryx()) {
            // La pelota se salió a la izquierda del rectángulo 
            ball.setBalldx(-ball.getBalldx());
            colision = true;
            //return d;
        }
        if (centroX + radio >= this.getBoundarywidth()) {
            // La pelota se salió a la derecha del rectángulo 
            ball.setBalldx(-ball.getBalldx());
            colision = true;
            //return d;
        }
        if (centroY - radio <= this.getBoundaryy()) {
            // La pelota se salió por arriba del rectángulo 
            ball.setBalldy(-ball.getBalldy());
            colision = true;
            //return d;
        }
        if (centroY + radio >= this.getBoundaryheight()) {
            // La pelota se salió por abajo del rectángulo 
            ball.setBalldy(-ball.getBalldy());
            colision = true;
            //return d;
        }
        if (colision) {
            return d;
        }
        return null;
    }
}
