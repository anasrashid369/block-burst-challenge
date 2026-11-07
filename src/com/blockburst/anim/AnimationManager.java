package com.blockburst.anim;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private final List<Drawable> drawables = new ArrayList<>();

    public void add(Drawable d) {
        if (d != null) {
            drawables.add(d);
        }
    }
}
