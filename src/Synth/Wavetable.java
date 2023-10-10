package Synth;

import Utils.Utils;
import static Synth.Synthesizer.AudioInfo.SAMPLE_RATE;

enum Wavetable {
    Sine,Square,Saw,Triangle;

    static final int SIZE = 8192;
    private final float[] samples = new float[SIZE];

    static {
        final double FUNDAMENTAL_FREQUENCY = 1d / (SIZE / (double) SAMPLE_RATE);
        for (int i = 0; i < SIZE; ++i) {
            double t = i / (double) SAMPLE_RATE;
            double tDivp = t / (1d / FUNDAMENTAL_FREQUENCY);
            Sine.samples[i] = (float) Math.sin(Utils.Math.frequencyToAngularFrequency(FUNDAMENTAL_FREQUENCY) * t);
            Square.samples[i] = Math.signum(Sine.samples[i]);
            Saw.samples[i] = (float) (2d * (tDivp - Math.floor(0.5 + tDivp)));
            Triangle.samples[i] = (float) (2d * Math.abs(Saw.samples[i]) - 1d);
        }
    }

    public float[] getSamples() {
        return samples;
    }
}
