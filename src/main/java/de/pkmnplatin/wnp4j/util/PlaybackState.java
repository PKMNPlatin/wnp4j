package de.pkmnplatin.wnp4j.util;

import lombok.Getter;

@Getter
public enum PlaybackState {

    NO_PLAYBACK(0),
    PLAYING(1),
    PAUSED(2);

    private int state;

    PlaybackState(int state) {
        this.state = state;
    }

    public static PlaybackState getState(int state) {
        for(PlaybackState type : values()) {
            if(type.getState() == state) {
                return type;
            }
        }
        return NO_PLAYBACK;
    }

}
