package Synth;

import java.util.Map;
import java.util.HashMap;

public class KeyFrequencies extends Synthesizer {
    final Map<Character, Double> keyToFrequency = new HashMap<>();
    private static final char[] characters = "ZSXDCVGBHNJMQ2W3ER5T6Y7UI,".toCharArray();
    private static final double[] freq = new double[characters.length];

    static {
        // first octave of keys
        freq[0] = 130.8128; // Z
        freq[1] = 138.5913; // S
        freq[2] = 146.8324; // X
        freq[3] = 155.5635; // D
        freq[4] = 164.8138; // C
        freq[5] = 174.6141; // V
        freq[6] = 184.9972; // G
        freq[7] = 195.9977; // B
        freq[8] = 207.6523; // H
        freq[9] = 220.0000; // N
        freq[10] = 233.0819; // J
        freq[11] = 246.9417; // M
        // second octave of keys
        freq[12] = 261.6256; // Q
        freq[13] = 277.1826; // 2
        freq[14] = 293.6648; // W
        freq[15] = 311.1270; // 3
        freq[16] = 329.6276; // E
        freq[17] = 349.2282; // R
        freq[18] = 369.9944; // 5
        freq[19] = 391.9954; // T
        freq[20] = 415.3047; // 6
        freq[21] = 440.0000; // Y
        freq[22] = 466.1638; // 7
        freq[23] = 493.8833; // U
        freq[24] = 523.2511; // I
        freq[25] = 261.6256; // ,
    }
}
 /*   static {
        for (int i = 0; i < characters.length; i++) {
            keyToFrequency.put(characters[i],freq[i]);
        }
    }

  */
