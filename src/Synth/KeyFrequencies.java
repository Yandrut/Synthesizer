package Synth;

public class KeyFrequencies {
    private static final double[] freq = new double[26];

    static {
        // first octave of keys
        freq[0] = 130.8128;
        freq[1] = 138.5913;
        freq[2] = 146.8324;
        freq[3] = 155.5635;
        freq[4] = 164.8138;
        freq[5] = 174.6141;
        freq[6] = 184.9972;
        freq[7] = 195.9977;
        freq[8] = 207.6523;
        freq[9] = 220.0000;
        freq[10] = 233.0819;
        freq[11] = 246.9417;
        // second octave of keys
        freq[12] = 261.6256;
        freq[13] = 277.1826;
        freq[14] = 293.6648;
        freq[15] = 311.1270;
        freq[16] = 329.6276;
        freq[17] = 349.2282;
        freq[18] = 369.9944;
        freq[19] = 391.9954;
        freq[20] = 415.3047;
        freq[21] = 440.0000;
        freq[22] = 466.1638;
        freq[23] = 493.8833;
        freq[24] = 523.2511;
        freq[25] = 261.6256;
    }

    public static double[] getFreq() {
        return freq;
    }
}
