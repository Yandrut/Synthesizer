package com.g223.synth;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Synthesizer {
    private final JFrame frame = new JFrame ("Synthesizer");
    private boolean shouldGenerate;
    private int wavePos;vac
    private final AudioThread audioThread = new AudioThread ( () -> {

            if (!shouldGenerate) {
                return null;
            }
            short[] a = new short[AudioThread.BUFFER_SIZE];
            for (int i = 0; i < AudioThread.BUFFER_SIZE; i++) {
                a[i] = (short) (Short.MAX_VALUE * Math.sin((2 * Math.PI * 440) / AudioInfo.SAMPLE_RATE * wavePos++));
            }
            return a;
        });

    Synthesizer () {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               if (!audioThread.isRunning()) {
                   shouldGenerate = true;
                   audioThread.triggerPlayback();
               }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                shouldGenerate = false;
            }
        });
            frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                audioThread.close();
            }
        });
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(630, 350);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static class AudioInfo {
        public static final int SAMPLE_RATE = 44100;
    }
}
