package DAsynth;

import javax.swing.*;
import java.awt.*;

public class SynthControlContainer extends JPanel {
    private final Synthesizer synth;
    protected Point mouseClickLocation;
    protected boolean on;

    public SynthControlContainer (Synthesizer synth) {
    this.synth = synth;
}

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
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