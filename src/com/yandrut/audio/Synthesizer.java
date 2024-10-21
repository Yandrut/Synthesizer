package com.yandrut.audio;

import com.yandrut.thread.AudioThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import static com.yandrut.thread.AudioThread.*;

public class Synthesizer {
    private boolean shouldGenerate;
    private final Oscillator[] oscillators = new Oscillator[3];
    private static final HashMap <Character, Double> KEY_FREQUENCIES = new HashMap<>();

    private final AudioThread audioThread = new AudioThread ( () -> {
            if (!shouldGenerate) {
                return null;
            }
            short[] samples = new short[getBufferSize()];

            for (int i = 0; i < getBufferSize(); i++) {
                double d = 0;
                for (Oscillator o : oscillators) d += o.getNextSample() / oscillators.length;
                samples[i] = (short) (Short.MAX_VALUE * d);
            }
            return samples;
        });

    private final KeyAdapter keyAdapter = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!KEY_FREQUENCIES.containsKey(e.getKeyChar()) && e.getKeyCode() != KeyEvent.VK_ESCAPE) {
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.out.println("Terminating program");
                System.exit(0);
            }
            else if (!audioThread.isRunning()) {
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
    public KeyAdapter getKeyQAdapter() {
        return keyAdapter;
    }

    public static class AudioInfo {
        public static final int SAMPLE_RATE = 48000;
    }
}