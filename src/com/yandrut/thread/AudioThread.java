package com.yandrut.thread;

import com.yandrut.exception.OpenAlException;
import com.yandrut.utils.Utils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import java.util.function.Supplier;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static com.yandrut.audio.Synthesizer.AudioInfo.SAMPLE_RATE;
public class AudioThread extends Thread {

    private final Supplier <short[]> bufferSupplier;

    static final int BUFFER_COUNT = 8;
    private final int[] buffers = new int[BUFFER_COUNT];
    private final long device = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
    private final long context = alcCreateContext(device, new int[1]);
    private static final int BUFFER_SIZE = 512;
    private final int source;
    private int bufferIndex;
    private boolean closed;
    private boolean running;

    public AudioThread(Supplier <short[]> bufferSupplier) {
        this.bufferSupplier = bufferSupplier;
        alcMakeContextCurrent(context);
        AL.createCapabilities(ALC.createCapabilities(device));
        source = alGenSources();
        for (int i = 0; i < BUFFER_COUNT; i++) {
            bufferSamples(new short[0]);
        }
        alSourcePlay(source);
        catchInternalException();
        start();
    }

    public boolean isRunning () {
        return running;
    }

    public static int getBufferSize() {
        return BUFFER_SIZE;
    }

    @Override
    public synchronized void run () {
        while (!closed) {
            while (!running) {
                Utils.invokeProcedure(this::wait,false);
            }
            int processedBuffers = alGetSourcei(source, AL_BUFFERS_PROCESSED);

            for (int i = 0; i < processedBuffers; i++) {
                short [] samples = bufferSupplier.get();
                if (samples == null) {
                    running = false;
                    break;
                }
                alDeleteBuffers(alSourceUnqueueBuffers(source));
                buffers[bufferIndex] = alGenBuffers();
                bufferSamples(samples);
            }
            if (alGetSourcei(source, AL_SOURCE_STATE) != AL_PLAYING) {
                alSourcePlay(source);
            }
            catchInternalException();
        }
        alDeleteSources(source);
        alDeleteBuffers(buffers);
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    public synchronized void triggerPlayback () {
        running = true;
        notify();
    }

    public void close () {
        closed = true;
    }

    private void bufferSamples (short[] samples) {
        int buf = buffers[bufferIndex++];
        alBufferData(buf, AL_FORMAT_MONO16, samples, SAMPLE_RATE);
        alSourceQueueBuffers(source, buf);
        bufferIndex %= BUFFER_COUNT;
    }

    private void catchInternalException() {
        int err = alcGetError(device);
        if (err != ALC_NO_ERROR) {
            throw new OpenAlException(err);
        }
    }
}