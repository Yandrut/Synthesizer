package com.yandrut.audio;
import com.yandrut.utils.ReferenceWrapper;
import com.yandrut.utils.Utils;
import static com.yandrut.audio.Synthesizer.AudioInfo.SAMPLE_RATE;
import static com.yandrut.utils.Utils.Math.offsetTone;
import static com.yandrut.utils.Utils.WindowDesign.LINE_BORDER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;


public class Oscillator extends SynthControlContainer {
    private static final int TONE_OFFSET_LIMIT = 1000;
    private Wavetable wavetable = Wavetable.Sine; // Default waveform set to size
    private final ReferenceWrapper<Integer> toneOffset = new ReferenceWrapper<>(0);
    private final ReferenceWrapper <Integer> volume = new ReferenceWrapper<>(100);
    private double keyFrequency;
    private int wavetableStepSize;
    private int wavetableIndex;


    public Oscillator(Synthesizer synth) {
        super(synth);
        JComboBox<Wavetable> comboBox = new JComboBox<>(Wavetable.values());
        comboBox.setSelectedItem(Wavetable.Sine);
        comboBox.setBounds(10, 10, 85, 25);

        comboBox.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) wavetable = (Wavetable) l.getItem();
        });
        add(comboBox);
        JLabel toneParameter = new JLabel("x0.00");
        toneParameter.setFont(Font.getFont("Free Mono"));
        toneParameter.setBounds(165,85,50,25);
        toneParameter.setBorder(LINE_BORDER);

        Utils.ParameterHandling.addMouseListeners(toneParameter,this,
                                                 -TONE_OFFSET_LIMIT,TONE_OFFSET_LIMIT, 1,toneOffset,
                () -> {
                    applyToneOffset();
                    toneParameter.setText(" x" + String.format("%.3f",getToneOffset()));
                });
        add(toneParameter);
        JLabel toneText = new JLabel("Tone");
        toneText.setFont(Font.getFont("Free Mono"));
        toneText.setBounds(165,65,75,25);
        add(toneText);
        JLabel volumeParameter = new JLabel(" 100%");
        volumeParameter.setFont(Font.getFont("Free Mono"));
        volumeParameter.setBounds(222,85,50,25);
        volumeParameter.setBorder(LINE_BORDER);
        add(volumeParameter);

        Utils.ParameterHandling.addMouseListeners(volumeParameter,this,0,100,1,volume,
                () -> volumeParameter.setText(" " + volume.value + "%"));
        JLabel volumeText = new JLabel("Volume");
        volumeText.setFont(Font.getFont("Free Mono"));
        volumeText.setBounds(220,65,75,25);
        add(volumeText);
        setSize(300, 115);
        setBorder(LINE_BORDER);
        setLayout(null);
    }

    public double[] getSampleWaveform(int numSamples) {
        double [] samples = new double[numSamples];
        double frequency = 1.0 / (numSamples / (double) SAMPLE_RATE) * 3d;
        int index = 0;
        int stepSize = (int) (Wavetable.SIZE * offsetTone(frequency,getToneOffset()) / SAMPLE_RATE);
        for (int i = 0; i < numSamples; i++) {
            samples[i] = wavetable.getSamples()[index] * getVolumeMultiplier();
            index = (index + stepSize) % Wavetable.SIZE;
        }
        return samples;
    }
    public void setKeyFrequency(double frequency) {
        keyFrequency = frequency;
        applyToneOffset();
    }

    private double getToneOffset() {
        return toneOffset.value /1000d;
    }

    private double getVolumeMultiplier() {
        return volume.value / 100d;
    }

    public double getNextSample() {
        double sample = wavetable.getSamples()[wavetableIndex] * getVolumeMultiplier();
        wavetableIndex = (wavetableIndex + wavetableStepSize) % Wavetable.SIZE;
        return sample;
    }
    private void applyToneOffset() {
        wavetableStepSize = (int) (Wavetable.SIZE * offsetTone(keyFrequency,getToneOffset()) / SAMPLE_RATE);
    }
}
