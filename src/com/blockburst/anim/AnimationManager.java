package com.blockburst.anim;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimationManager {

    private final List<Drawable> drawables = new ArrayList<>();

    public void add(Drawable d) {
        if (d != null) {
            drawables.add(d);
        }
    }

    public void updateAll(long deltaMs) {
        Iterator<Drawable> iterator = drawables.iterator();

        while (iterator.hasNext()) {
            Drawable d = iterator.next();
            d.update(deltaMs);

            if (d.isFinished()) {
                iterator.remove();
            }
        }
    }

    public void drawAll(Graphics2D g) {
        for (Drawable d : drawables) {
            d.draw(g);
        }
    }

    public void clear() {
        drawables.clear();
    }
}
