package com.yandrut.audio;

public enum State {
    OFF(0),
    ATTACK(1),
    DECAY(2),
    SUSTAIN(3),
    RELEASE(4);

    State(double state) {

    }
}