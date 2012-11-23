/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.drawable;

import java.awt.Color;
import java.awt.Graphics;
import pelotas.controller.CollisionListener;
import pelotas.score.Score;

/**
 *
 * @author David
 */
@SuppressWarnings("serial")
public class Brick implements Drawable, CollisionListener {
    
    public static final int PUNT = 1;
    private int Brickx, Bricky;
    private int Brickwidth, Brickheight;
    private Score score;

    public Brick(int Brickx, int Bricky, int Brickwidth, int Brickheight, Score score) {
        this.Brickx = Brickx;
        this.Bricky = Bricky;
        this.Brickwidth = Brickwidth;
        this.Brickheight = Brickheight;
        this.score = score;
    }

    public int getBrickx() {
        return Brickx;
    }

    public void setBrickx(int Brickx) {
        this.Brickx = Brickx;
    }

    public int getBricky() {
        return Bricky;
    }

    public void setBricky(int Bricky) {
        this.Bricky = Bricky;
    }

    public int getBrickwidth() {
        return Brickwidth;
    }

    public void setBrickwidth(int Brickwidth) {
        this.Brickwidth = Brickwidth;
    }

    public int getBrickheight() {
        return Brickheight;
    }

    public void setBrickheight(int Brickheight) {
        this.Brickheight = Brickheight;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(
                this.getBrickx(), this.getBricky(),
                this.getBrickwidth(), this.getBrickheight());
    }

    @Override
    public void add(Drawable d) {
        System.out.println("add: Una brick no puede tener Drawable");
    }

    @Override
    public void remove(Drawable d) {
        System.out.println("remove: Una brick no puede tener Drawable");
    }

    @Override
    public Drawable getChild(int i) {
        System.out.println("getChild: Una brick no puede tener Drawable");
        return null;
    }

    @Override
    public Drawable colision(Drawable d) {
        Ball ball;
        ball = (Ball) d;

        // Por la Izquierda
        if ((ball.getBalldx() > 0) &&//Bola direccion derecha
                ((ball.getPosballx() + ball.getRadio() * 2) == this.getBrickx()) &&//Esta en el borde izquierdo o lo a rebasado
                ((ball.getPosbally() + ball.getRadio() * 2) >= this.getBricky() &&//Esta mas abajo del borde superior
                (ball.getPosbally() <= (this.getBricky() + this.getBrickheight())))) {//Esta mas arriba del borde inferior
            ball.setBalldx(-ball.getBalldx());
            score.onCollision(PUNT);
            return d;
        }
        // Por la derecha
        if ((ball.getBalldx() < 0) &&//Bola direccion izquierda
                ball.getPosballx() == (this.getBrickx() + this.getBrickwidth()) &&//Esta en el borde derecho o lo a rebasado
                ((ball.getPosbally() + ball.getRadio() * 2) >= this.getBricky()) &&//Esta por debajo de borde superior
                (ball.getPosbally() <= (this.getBricky() + this.getBrickheight()))) {//Esta por encima del borde inferior
            ball.setBalldx(-ball.getBalldx());
            score.onCollision(PUNT);
            return d;
        }
        // Por abajo
        if ((ball.getBalldy() < 0) && //Bola direccion arriba
                (ball.getPosbally() == (this.getBricky() + this.getBrickheight())) &&//Esta en el borde inferior o lo a rebasado
                ((ball.getPosballx() + ball.getRadio() * 2) >= this.getBrickx()) &&//Esta mas a la derecha del borde izquierdo
                (ball.getPosballx() <= (this.getBrickx() + this.getBrickwidth()))) {//Esta mas a la izquierda del borde derecho
            ball.setBalldy(-ball.getBalldy());
            score.onCollision(PUNT);
            return d;
        }// Por Encima
        if (ball.getBalldy() > 0 && //Bola direccion abajo
                ((ball.getPosbally() + ball.getRadio() * 2) == this.getBricky()) &&//Esta en el borde superior o lo ha rebasado
                ((ball.getPosballx() + ball.getRadio() * 2) >= this.getBrickx()) &&//Esta mas a la derecha que el borde izquierdo
                (ball.getPosballx() <= (this.getBrickx() + this.getBrickwidth()))) {//Esta mas a la izquierda que el borde derecho
            ball.setBalldy(-ball.getBalldy());
            score.onCollision(PUNT);
            return d;
        }
        return null;
    }

    @Override
    public void onCollision(int d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
