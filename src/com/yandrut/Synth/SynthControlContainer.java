package com.yandrut.audio.Synth;

import javax.swing.*;
import java.awt.*;

public class SynthControlContainer extends JPanel {
    private final Synthesizer synth;
    private Point mouseClickLocation;

    public SynthControlContainer (Synthesizer synth) {
    this.synth = synth;
}

    public Point getMouseClickLocation() {
        return mouseClickLocation;
    }

    public void setMouseClickLocation (Point mouseClickLocation) {
        this.mouseClickLocation = mouseClickLocation;
    }

    @Override
    public Component add (Component component) {
        component.addKeyListener(synth.getKeyAdapter());
        return super.add(component);
    }

    @Override
    public Component add (Component component, int index) {
        component.addKeyListener(synth.getKeyAdapter());
        return super.add(component, index);
    }

    @Override
    public Component add (String name, Component component) {
        component.addKeyListener(synth.getKeyAdapter());
        return super.add(name, component);
    }

    @Override
    public void add (Component component, Object constraints) {
        component.addKeyListener(synth.getKeyAdapter());
        super.add(component, constraints);
    }

    @Override
    public void add (Component component, Object constraints, int index) {
        component.addKeyListener(synth.getKeyAdapter());
        super.add(component, constraints, index);
    }
}