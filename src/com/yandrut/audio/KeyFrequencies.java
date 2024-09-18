package com.yandrut.audio;

public class KeyFrequencies {
    // Every value in this array is mapped to a key to emulate piano keyboard
    private static final double[] frequency = new double[37];

    static {
        // first octave of keys
        frequency[0] = 130.8128;
        frequency[1] = 138.5913;
        frequency[2] = 146.8324;
        frequency[3] = 155.5635;
        frequency[4] = 164.8138;
        frequency[5] = 174.6141;
        frequency[6] = 184.9972;
        frequency[7] = 195.9977;
        frequency[8] = 207.6523;
        frequency[9] = 220.0000;
        frequency[10] = 233.0819;
        frequency[11] = 246.9417;
        // Second octave of keys
        frequency[12] = 261.6256;
        frequency[13] = 277.1826;
        frequency[14] = 293.6648;
        frequency[15] = 311.1270;
        frequency[16] = 329.6276;
        frequency[17] = 349.2282;
        frequency[18] = 369.9944;
        frequency[19] = 391.9954;
        frequency[20] = 415.3047;
        frequency[21] = 440.0000;
        frequency[22] = 466.1638;
        frequency[23] = 493.8833;
        frequency[24] = 523.2511;
        // Incomplete 3rd octave due to keyboard limitations
        frequency[25] = 261.6256;
        frequency[26] = 554.3653;
        frequency[27] = 587.3295;
        frequency[28] = 622.2540;
        frequency[29] = 659.2551;
        frequency[30] = 698.4565;
        frequency[31] = 739.9888;
        frequency[32] = 783.9909;
        // Missing keyboard space filled
        frequency[33] = 277.1826;
        frequency[34] = 293.6648;
        frequency[35] = 311.1270;
        frequency[36] = 329.6276;
    }

    public static double[] getFreq() {
        return frequency;
    }
}
