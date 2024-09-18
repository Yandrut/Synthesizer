package com.yandrut.audio;


@SuppressWarnings("all")
public class Envelopes {
    Wavetable wavetable;
    private State envelopeState = State.OFF;
    private final double attackDuration = 0.011;
    private final double decayDuration = 0.331;
    private final double sustainLevel = 0.590;
    private final double releaseDuration = 0.300;
    private int wavetableIndex;
    private int wavetableStepSize;

    public void startPlayback() {
        envelopeState = State.ATTACK;
    }
    public void stopPlayback() {
        envelopeState = State.RELEASE;
    }

    public double getNextSample() {
         double sample = wavetable.getSamples()[wavetableIndex];
         wavetableIndex = (wavetableIndex + wavetableStepSize) % Wavetable.SIZE;
        double envelopeAmplitude = 1d;

        switch (envelopeState) {
            case ATTACK:
                envelopeAmplitude = Math.min(envelopeAmplitude,getEnvelopeAmplitude());
                if (envelopeAmplitude >= 1d) {
                    envelopeState = State.DECAY;
                }
                break;
            case DECAY:
                envelopeAmplitude = Math.max(envelopeAmplitude,getEnvelopeAmplitude());
                if (envelopeAmplitude <= sustainLevel) {
                    envelopeState = State.SUSTAIN;
                }
                break;
            case SUSTAIN:
                envelopeAmplitude = sustainLevel;
                break;
            case RELEASE:
                envelopeAmplitude = Math.max(envelopeAmplitude,getEnvelopeAmplitude()); {
                    if (envelopeAmplitude <= 0d) {
                        envelopeState = State.OFF;
                    }
                    break;
            }
        }
        return sample * envelopeAmplitude;
    }
    private double getEnvelopeAmplitude() {
            double envelopeAmplitude = 0d;
            double currentSample = wavetable.getSamples()[wavetableIndex];

            switch (envelopeState) {
                case ATTACK:
                    envelopeAmplitude = currentSample / attackDuration;
                    break;

                case DECAY:
                    envelopeAmplitude = 1.0 - (currentSample / decayDuration) * (1.0 - sustainLevel);
                    break;

                case SUSTAIN:
                    envelopeAmplitude = sustainLevel;
                    break;

                case RELEASE:
                    envelopeAmplitude = sustainLevel - (currentSample / releaseDuration) * sustainLevel;
                    break;
            }
            return envelopeAmplitude;
        }
    }