/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.drawable;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author David
 */
@SuppressWarnings("serial")
public class Composite implements Drawable {

    private List<Drawable> childDrawable = Collections.synchronizedList(new ArrayList<Drawable>());

    @Override
    public void render(Graphics g) {
        synchronized (childDrawable) {
            for (Drawable drawable : childDrawable) {
                drawable.render(g);
            }
        }
    }

    @Override
    public void add(Drawable drawable) {
        childDrawable.add(drawable);
    }

    @Override
    public void remove(Drawable drawable) {
        childDrawable.remove(drawable);
    }

    @Override
    public Drawable getChild(int i) {
        return childDrawable.get(i);
    }

    @Override
    public Drawable colision(Drawable d) {
        synchronized (childDrawable) {
            for (Drawable draw : childDrawable) {
                draw.colision(d);
            }
        }
        return null;
    }

    public List<Drawable> getChildDrawable() {
        return childDrawable;
    }

    public void setChildDrawable(List<Drawable> childDrawable) {
        this.childDrawable = childDrawable;
    }
}
