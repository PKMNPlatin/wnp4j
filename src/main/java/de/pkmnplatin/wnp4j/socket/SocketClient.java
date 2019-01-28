package de.pkmnplatin.wnp4j.socket;

import de.pkmnplatin.wnp4j.listener.ClientListener;
import de.pkmnplatin.wnp4j.util.MathUtil;
import de.pkmnplatin.wnp4j.util.MessageType;
import de.pkmnplatin.wnp4j.util.PlaybackState;
import de.pkmnplatin.wnp4j.util.TimeConverter;
import lombok.Getter;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;

import static de.pkmnplatin.wnp4j.util.MessageType.*;

@Getter
public class SocketClient {

    private List<ClientListener> listener = new ArrayList<>();
    private WebSocket socket;
    protected ClientInfo info;

    public SocketClient(WebSocket socket, ClientInfo info) {
        this.socket = socket;
        this.info = info;
    }

    public void parseMessage(MessageType type, String content) {
        if(type == PLAYER) {
            if(!this.info.player.equals(content)) {
                listener.forEach(l -> l.onPlayerChanged(this.info.player, content));
            }
            this.info.player = content;
        }
        if(type == STATE) {
            PlaybackState state = PlaybackState.getState(Integer.parseInt(content));
            if(this.info.state != state) {
                listener.forEach(l -> l.onStateChanged(this.info.state, state));
            }
            this.info.state = state;
        }
        if(type == TITLE) {
            if (this.info.title != null && !this.info.title.equals(content)) {
                listener.forEach(l -> l.onTitleChanged(this.info.title, content));
            }
            this.info.title = content;
        }
        if(type == ARTIST) {
            if(this.info.title != null && !this.info.title.equals(content)) {
                listener.forEach(l -> l.onArtistChanged(this.info.artist, content));
            }
            this.info.artist = content;
        }
        if(type == ALBUM) {
            if(!this.info.album.equals(content)) {
                listener.forEach(l -> l.onAlbumChanged(this.info.album, content));
            }
            this.info.album = content;
        }
        if(type == COVER) {
            if(!this.info.cover.equals(content)) {
                listener.forEach(l -> l.onCoverChanged(this.info.cover, content));
            }
            this.info.cover = content;
        }
        if(type == DURATION) {
            long durationMillis = TimeConverter.toMillis(content);
            if(!this.info.durationRaw.equals(content) && this.info.duration != durationMillis) {
                listener.forEach(l -> l.onDurationChanged(this.info.duration, this.info.durationRaw, durationMillis, content));
            }
            this.info.durationRaw = content;
            this.info.duration = durationMillis;
        }
        if(type == POSITION) {
            long positionMillis = TimeConverter.toMillis(content);
            if(!this.info.positionRaw.equals(content) && this.info.position != positionMillis) {
                listener.forEach(l -> l.onPositionChanged(this.info.position, this.info.positionRaw, positionMillis, content));
            }
            this.info.positionRaw = content;
            this.info.position = positionMillis;
        }
        if(type == VOLUME) {
            double newVolume = Double.parseDouble(content);
            if(this.info.volume != newVolume) {
                listener.forEach(l -> l.onVolumeChanged(this.info.volume, newVolume));
            }
            this.info.volume = newVolume;
        }
        if(type == RATING) {
            int newRating = Integer.parseInt(content);
            if(this.info.rating != newRating) {
                listener.forEach(l -> l.onRatingChanged(this.info.rating, newRating));
            }
            this.info.rating = newRating;
        }
        if(type == REPREAT) {
            boolean repeating = content.equals("1");
            if(this.info.repeating != repeating) {
                listener.forEach(l -> l.onRepeatingChanged(this.info.repeating, repeating));
            }
            this.info.repeating = repeating;
        }
        if(type == SHUFFLE) {
            boolean shuffling = content.equals("1");
            if(this.info.shuffling != shuffling) {
                listener.forEach(l -> l.onShufflingChanged(this.info.shuffling, shuffling));
            }
            this.info.shuffling = shuffling;
        }
        if(type == TRACKID) {
            if(!this.info.trackId.equals(content)) {
                listener.forEach(l -> l.onTrackIdChanged(this.info.trackId, content));
            }
            this.info.trackId = content;
        }
        if(type == ARTISTID) {
            if(!this.info.artistId.equals(content)) {
                listener.forEach(l -> l.onArtistIdChanged(this.info.artistId, content));
            }
            this.info.artistId = content;
        }
        if(type == ALBUMID) {
            if(!this.info.albumId.equals(content)) {
                listener.forEach(l -> l.onAlbumIdChanged(this.info.albumId, content));
            }
            this.info.albumId = content;
        }
    }

    public String getPlayer() {
        return this.info.getPlayer();
    }

    public PlaybackState getState() {
        return this.info.getState();
    }

    public boolean isPlaying() {
        return this.getState() == PlaybackState.PLAYING;
    }

    public boolean isPaused() {
        return !this.isPlaying();
    }

    public String getTitle() {
        return this.info.getTitle();
    }

    public String getArtist() {
        return this.info.getArtist();
    }

    public String getAlbum() {
        return this.info.getAlbum();
    }

    public String getCover() {
        return this.info.getCover();
    }

    public long getDuration() {
        return this.info.getDuration();
    }

    public String getDurationRaw() {
        return this.info.getDurationRaw();
    }

    public long getPostion() {
        return this.info.getPosition();
    }

    public String getPostionRaw() {
        return this.info.getPositionRaw();
    }

    public double getVolume() {
        return this.info.getVolume();
    }

    public int getRating() {
        return this.info.getRating();
    }

    public boolean isRepeating() {
        return this.info.isRepeating();
    }

    public boolean isShuffling() {
        return this.info.isShuffling();
    }

    private boolean check() {
        return (socket != null && !socket.isClosed());
    }

    private void sendMessage(String message) {
        if(check()) {
            socket.send(message);
        }
    }

    public void playpause() {
        sendMessage("playpause");
    }

    public void next() {
        sendMessage("next");
    }

    public void previous() {
        sendMessage("previous");
    }

    public void setPostionSeconds(int seconds) {
        double percentage = Math.round(((seconds * 1000) * 100) / getDuration());
        sendMessage("SetPosition " + seconds + ":SetProgress " + (percentage / 100.0D) + ":");
    }

    public void setPostionPercent(double newPositionPercent) {
        newPositionPercent = MathUtil.clamp(newPositionPercent, 0D, 100D);
        int seconds = (int) ((newPositionPercent / 100) * getDuration() / 1000);
        sendMessage("SetPosition " + seconds + ":SetProgress " + newPositionPercent / 100.0D + ":");
    }

    public void setVolume(double newVolume) {
        sendMessage("setvolume " + newVolume);
    }

    public void repeat() {
        sendMessage("repeat");
    }

    public void shuffle() {
        sendMessage("shuffle");
    }

    public void togglethumbsup() {
        sendMessage("togglethumbsup");
    }

    public void togglethumbsdown() {
        sendMessage("togglethumbsdown");
    }

    public void rate() {
        sendMessage("rating");
    }

}
