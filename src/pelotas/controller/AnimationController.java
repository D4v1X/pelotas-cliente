/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pelotas.drawable.Ball;
import pelotas.drawable.Brick;
import pelotas.drawable.Composite;
import pelotas.drawable.Drawable;
import pelotas.score.Score;

/**
 *
 * @author David
 */
public class AnimationController extends Thread implements MouseListener {

    private boolean stopping;
    private Drawable escena;
    private List<Ball> ballDrawable;
    private final Score score;

    AnimationController(Composite escena, Score score) {
        this.escena = escena;
        this.ballDrawable = Collections.synchronizedList(new ArrayList<Ball>());
        this.score = score;
    }

    @Override
    public void run() {
        while (!stopping) {
            synchronized (ballDrawable) {// Monitor para controlar la concurrencia
                for (Ball b : ballDrawable) {
                    b.move();
                    escena.colision(b);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Random rnd = new Random();
            Ball ball;
            ball = new Ball(e.getX(), e.getY(), (1 + rnd.nextInt(1)),
                    (1 + rnd.nextInt(1)), (2 + rnd.nextInt(8)), new Color(
                    rnd.nextInt(255), rnd.nextInt(255),
                    rnd.nextInt(255)));
            escena.add(ball);
            ballDrawable.add(ball);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            Random rnd = new Random();
            Brick brick;
            brick = new Brick(e.getX(), e.getY(), (20 + rnd.nextInt(20)),
                    (10 + rnd.nextInt(30)),
                    score);
            escena.add(brick);
            /*
             * Brick brick1 = new Brick(100, 60, 40, 20); Brick brick2 = new
             * Brick(100, 120, 40, 20); Brick brick3 = new Brick(220, 60, 40,
             * 20); Brick brick4 = new Brick(220, 120, 40, 20);
             * escena.add(brick1); escena.add(brick2); escena.add(brick3);
             * escena.add(brick4);
             */
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            Random rnd = new Random();
            Brick brick;
            brick = new Brick(e.getX(), e.getY(), (20 + rnd.nextInt(20)),
                    (10 + rnd.nextInt(30)),
                    score);
            escena.add(brick);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void addBall(Ball ball) {
        this.ballDrawable.add(ball);
    }

    public boolean isStopping() {
        return stopping;
    }

    public void setStopping(boolean stopping) {
        this.stopping = stopping;
    }

    public Drawable getEscena() {
        return escena;
    }

    public void setEscena(Drawable escena) {
        this.escena = escena;
    }

    public List<Ball> getBallDrawable() {
        return ballDrawable;
    }

    public void setBallDrawable(List<Ball> ballDrawable) {
        this.ballDrawable = ballDrawable;
    }
}
