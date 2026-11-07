package com.blockburst.anim;

import java.awt.Graphics2D;

public interface Drawable {

    void update(long deltaMs);

    void draw(Graphics2D g);

    boolean isFinished();
}
