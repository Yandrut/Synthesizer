package DAsynth;

import Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Oscillator extends SynthControlContainer {
    private static final int TONE_OFFSET_LIMIT = 200;
    private double keyFrequency;
    private double frequency;
    private int wavePos;
    private int toneOffset;
    private Waveform waveform = Waveform.Sine;
    private final Random random = new Random();

    public Oscillator(Synthesizer synth) {
        super(synth);
        JComboBox<Waveform> comboBox = new JComboBox<>(new Waveform[]{Waveform.Sine, Waveform.Square, Waveform.Saw, Waveform.Triangle, Waveform.Noise});
        comboBox.setSelectedItem(Waveform.Sine);
        comboBox.setBounds(10, 10, 85, 25);

        comboBox.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) waveform = (Waveform) l.getItem();
        });
        add(comboBox);
        JLabel toneParameter = new JLabel("x0.00");
        toneParameter.setBounds(165,65,50,25);
        toneParameter.setBorder(Utils.WindowDesign.LINE_BORDER);
        toneParameter.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB),new Point(0,0), "blank_cursor");
                setCursor(BLANK_CURSOR);
                mouseClickLocation = e.getLocationOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        toneParameter.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseClickLocation.y != e.getYOnScreen()) {
                    boolean mouseMovingUp = mouseClickLocation.y - e.getYOnScreen() > 0;
                    if (mouseMovingUp && toneOffset < TONE_OFFSET_LIMIT) {
                        toneOffset++;
                    }
                    else if (!mouseMovingUp && toneOffset > -TONE_OFFSET_LIMIT) {
                        toneOffset--;
                    }
                    applyToneOffset();
                    toneParameter.setText("x" + String.format("%.3f" ,getToneOffset()));
                }
                Utils.ParameterHandling.PARAMETER_ROBOT.mouseMove(mouseClickLocation.x, mouseClickLocation.y);
            }
        });

        add(toneParameter);
        JLabel toneText = new JLabel("Tone");
        toneText.setBounds(172,40,75,25);
        add(toneText);

        setSize(300, 115);
        setBorder(Utils.WindowDesign.LINE_BORDER);
        setLayout(null);
    }

    private enum Waveform {
    Sine,Square,Saw,Triangle,Noise
}

    public double getKeyFrequency() {
        return frequency;
}

    public void setKeyFrequency(double frequency) {
        keyFrequency = this.frequency = frequency;
        //apply tone offset
    }
    private double getToneOffset() {
        return toneOffset/1000d;
}

public double nextSample () {
        double tDivP = (wavePos++ / (double) Synthesizer.AudioInfo.SAMPLE_RATE) / (1d / frequency);

    double saw = 2d * (tDivP - Math.floor(0.5 + tDivP));

    switch (waveform) {
            case Sine:
                return Math.sin(Utils.Math.frequencyToAngularFrequency(frequency) * (wavePos -1) / Synthesizer.AudioInfo.SAMPLE_RATE);
            case Square:
                return Math.signum(Math.sin(Utils.Math.frequencyToAngularFrequency(frequency) * wavePos / Synthesizer.AudioInfo.SAMPLE_RATE));
            case Saw:
                return saw;
            case Triangle:
                return 2d * Math.abs(saw);
            case Noise:
                return random.nextDouble();
            default:
                throw new RuntimeException("UNKNOWN WAVEFORM");
        }
    }

    private void applyToneOffset() {
        frequency = keyFrequency * Math.pow(2, getToneOffset());
        System.out.println(frequency);
    }
}
