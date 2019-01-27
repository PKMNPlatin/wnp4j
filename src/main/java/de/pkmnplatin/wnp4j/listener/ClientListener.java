package de.pkmnplatin.wnp4j.listener;

import de.pkmnplatin.wnp4j.socket.SocketClient;
import de.pkmnplatin.wnp4j.util.PlaybackState;
import lombok.Getter;

@Getter
public class ClientListener {

    private final SocketClient parent;

    public ClientListener(SocketClient parent) {
        this.parent = parent;
    }

    public void onPlayerChanged(String oldPlayer, String newPlayer) {
    }

    public void onStateChanged(PlaybackState oldState, PlaybackState newState) {
    }

    public void onTitleChanged(String oldTitle, String newTitle) {
    }

    public void onArtistChanged(String oldArtist, String newArtist) {

    }

    public void onAlbumChanged(String oldAlbum, String newAlbum) {
    }

    public void onCoverChanged(String oldCover, String newCover) {
    }

    public void onDurationChanged(long oldDuration, String oldDurationRaw, long newDuration, String newDurationRaw) {
    }

    public void onPositionChanged(long oldPosition, String oldPositionRaw, long newPosition, String newPositionRaw) {
    }

    public void onVolumeChanged(double oldVolume, double newVolume) {

    }

    public void onRatingChanged(int oldRating, int newRating) {

    }

    public void onRepeatingChanged(boolean oldState, boolean newState) {

    }

    public void onShufflingChanged(boolean oldState, boolean newState) {
    }

    public void onTrackIdChanged(String oldTrackId, String newTrackId) {

    }

    public void onArtistIdChanged(String oldArtistId, String newArtistId) {

    }

    public void onAlbumIdChanged(String oldAlbumId, String newAlbumId) {

    }

}
