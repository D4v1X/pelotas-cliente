/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.drawable;

import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author David
 */
public interface Drawable extends Serializable {

    public void render(Graphics g);

    public void add(Drawable d);

    public void remove(Drawable d);

    public Drawable getChild(int i);

    public Drawable colision(Drawable d);
}
