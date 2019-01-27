package de.pkmnplatin.wnp4j.listener;

import de.pkmnplatin.wnp4j.socket.SocketClient;
import de.pkmnplatin.wnp4j.util.MessageType;

public class SocketListener {

    /**
     *
     * @param client the client which has connected to the websocket.
     */
    public void onClientConnected(SocketClient client) {}

    /**
     *
     * @param client the client which dropped the connection.
     */
    public void onClientDisconnected(SocketClient client) {}

    /**
     * @param client the client which has sent the data.
     * @param type the message type of the message.
     * @param conent the message content.
     */
    public void onClientMessage(SocketClient client, MessageType type, String conent) {}

    /**
     * gets executed when the Socket has started.
     */
    public void onSocketStartup() {}

    /**
     * @param client The client which has produced the error.
     * @param ex The exception which was thrown.
     * @return If true, this listener says to drop the connection to the client.
     */
    public boolean onError(SocketClient client, Exception ex) {
        return true;
    }

}
