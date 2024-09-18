package com.yandrut.audio.Synth;

import com.yandrut.audio.Synth.Utils.Utils.*;
import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class WaveVisualizer extends JPanel {
    private final Oscillator[] oscillators;

    private WaveVisualizer(Oscillator[] oscillators) {
        this.oscillators = oscillators;
        setBorder(WindowDesign.LINE_BORDER);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final int PAD = 1;
        int numSamples = getWidth() - PAD * 2;
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double[] mixedSamples = new double[numSamples];
        for (Oscillator oscillator : oscillators) {
            double[] samples = oscillator.getSampleWaveform(numSamples);
            for (int i = 0; i < samples.length; i++) {
                mixedSamples[i] += samples[i] / oscillators.length;
            }
        }
        int midY = getHeight() / 2;
        Function<Double, Integer> sampleToYCoordinate = sample -> (int) (midY + sample * (midY - PAD));
        graphics2D.drawLine(0, midY, getWidth(), midY);
        graphics2D.drawLine(PAD, PAD, PAD, getHeight() - PAD);

        for (int i = 0; i < numSamples; i++) {
            int nextY = i == numSamples - 1 ? sampleToYCoordinate.apply(mixedSamples[i]) : sampleToYCoordinate.apply(mixedSamples[i + 1]);
            graphics2D.drawLine(PAD + i, sampleToYCoordinate.apply(mixedSamples[i]), PAD + i + 1, nextY);
        }
    }
}