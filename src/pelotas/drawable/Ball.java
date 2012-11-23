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
public class Ball implements Drawable {

    private int posballx, posbally;
    private int balldx, balldy;
    private int radio;
    private Color color;

    //Construtor
    public Ball(int posballx, int posbally, int balldx, int balldy, int radio, Color color) {
        this.posballx = posballx;
        this.posbally = posbally;
        this.balldx = balldx;
        this.balldy = balldy;
        this.radio = radio;
        this.color = color;
    }

    //Getters and Setters
    public int getPosballx() {
        return posballx;
    }

    public void setPosballx(int posballx) {
        this.posballx = posballx;
    }

    public int getPosbally() {
        return posbally;
    }

    public void setPosbally(int posbally) {
        this.posbally = posbally;
    }

    public int getBalldx() {
        return balldx;
    }

    public void setBalldx(int balldx) {
        this.balldx = balldx;
    }

    public int getBalldy() {
        return balldy;
    }

    public void setBalldy(int balldy) {
        this.balldy = balldy;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    //Operaciones()

    public void move() {
        this.posballx += this.getBalldx();
        this.posbally += this.getBalldy();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(this.color);
        g.fillOval(
                this.getPosballx(), this.getPosbally(),
                this.getRadio() * 2, this.getRadio() * 2);
    }

    @Override
    public void add(Drawable d) {
        System.out.println("add: Una bola no puede tener Drawable");
    }

    @Override
    public void remove(Drawable d) {
        System.out.println("remove: Una bola no puede tener Drawable");
    }

    @Override
    public Drawable getChild(int i) {
        System.out.println("getChild: Una bola no puede tener Drawable");
        return null;
    }

    @Override
    public Drawable colision(Drawable d) {
        Ball ball;
        ball = (Ball) d;
        int dx1 = ball.getBalldx();
        int dy1 = ball.getBalldy();
        int dx2 = this.getBalldx();
        int dy2 = this.getBalldy();
        int dx = ball.getPosballx() - this.getPosballx();
        int dy = ball.getPosbally() - this.getPosbally();
        int radio1 = ball.getRadio();
        int radio2 = this.getRadio();
        int radioTotal = radio1 + radio2;

        if (Math.pow(dx, 2) + Math.pow(dy, 2) <= Math.pow(radioTotal, 2)) {
            ball.setBalldx(dx2);
            ball.setBalldy(dy2);
            this.setBalldx(dx1);
            this.setBalldy(dy1);
            return d;
        }
        return null;
    }
}
