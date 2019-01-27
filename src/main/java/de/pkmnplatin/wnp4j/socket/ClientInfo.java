package de.pkmnplatin.wnp4j.socket;

import de.pkmnplatin.wnp4j.util.PlaybackState;
import lombok.Getter;

@Getter
public class ClientInfo {

    protected String player = "";
    protected PlaybackState state = PlaybackState.NO_PLAYBACK;
    protected String title = "";
    protected String artist = "";
    protected String album = "";
    protected String cover = "";
    protected long duration = 0L;
    protected String durationRaw = "00:00";
    protected long position = 0L;
    protected String positionRaw = "00:00";
    protected double volume = -1D;
    protected int rating = 0;
    protected boolean repeating;
    protected boolean shuffling;

    /**
     * This is just optional so it's not always set.
     */
    protected String trackId;

    /**
     * This is just optional so it's not always set.
     */
    protected String artistId;

    /**
     * This is just optional so it's not always set.
     */
    protected String albumId;

}
