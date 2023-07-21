package Synth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class Synthesizer {
    private boolean shouldGenerate;
    private final Oscillator[] oscillators = new Oscillator[3];
    private final WaveVisualizer waveVisual = new WaveVisualizer(oscillators);
    private static final HashMap <Character, Double> KEY_FREQUENCIES = new HashMap<>();

    private final AudioThread audioThread = new AudioThread ( () -> {
        if (!shouldGenerate) {
                return null;
            }
            short[] s = new short[AudioThread.BUFFER_SIZE];
            for (int i = 0; i < AudioThread.BUFFER_SIZE; i++) {
                double d = 0;
                for (Oscillator o : oscillators) d += o.getNextSample() / oscillators.length;
                s[i] = (short) (Short.MAX_VALUE * d);
            }
            return s;
        });

    private final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!KEY_FREQUENCIES.containsKey(e.getKeyChar())) {
                return;
            }
            if (!audioThread.isRunning()) {
                for (Oscillator o : oscillators) {
                    o.setKeyFrequency(KEY_FREQUENCIES.get(e.getKeyChar()));
                }

                shouldGenerate = true;
                audioThread.triggerPlayback();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            shouldGenerate = false;
        }
    };

    // constructor defines key and GUI actions
    static {
        final char[] KEYS = "ZSXDCVGBHNJMQ2W3ER5T6Y7UI,9O0P[=]L.;/".toCharArray();

        for (int i = 0; i < KEYS.length; i++) {
            KEY_FREQUENCIES.put(KEYS[i], KeyFrequencies.getFreq()[i]);
        }
    }

    public Synthesizer() {

        final JFrame frame = new JFrame("Synth");
        int y = 0;
        for (int i = 0; i < oscillators.length; i++) {
            oscillators[i] = new Oscillator(this);
            oscillators[i].setLocation(0,y);
            frame.add(oscillators[i]);
            y+= 105;
        }
            frame.setFont(Font.getFont("Free Mono"));
            waveVisual.setBounds(300,0,330,322);
            frame.add(waveVisual);
            frame.addKeyListener(keyAdapter);
            frame.addWindowListener(new WindowAdapter() {

                @Override
            public synchronized void windowClosing(WindowEvent e) {
                audioThread.close();
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(630, 350);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }

    public void updateWave() {
        waveVisual.repaint();
    }

    public static class AudioInfo {
        public static final int SAMPLE_RATE = 48000;
    }
}