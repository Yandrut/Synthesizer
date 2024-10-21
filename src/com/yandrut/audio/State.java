package com.yandrut.audio;

public enum State {
    OFF(0),
    ATTACK(1),
    DECAY(2),
    SUSTAIN(3),
    RELEASE(4);

    final int state;

    State(int state) {
        this.state = state;
    }
}