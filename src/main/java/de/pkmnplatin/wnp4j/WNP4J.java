package de.pkmnplatin.wnp4j;

import de.pkmnplatin.wnp4j.listener.SocketListener;
import de.pkmnplatin.wnp4j.socket.SocketClient;
import de.pkmnplatin.wnp4j.socket.WebsocketServer;
import lombok.AccessLevel;
import lombok.Getter;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
public class WNP4J {

    private static WNP4J instance;

    private static Logger logger = Logger.getLogger("WNP4J");

    @Getter(AccessLevel.NONE)
    private WebsocketServer serverSocket;

    private SocketClient lastActiveDevice;
    private List<SocketClient> connections = new ArrayList<>();
    private final int port = 8974;

    private WNP4J() {
        (serverSocket = new WebsocketServer(this.port)).start();
    }

    public static WNP4J getInstance() {
        if (instance == null) {
            instance = new WNP4J();
        }
        return instance;
    }

    public void addListener(SocketListener listener) {
        if (this.serverSocket != null) {
            this.serverSocket.getListeners().add(listener);
        }
    }

    public void shutdown() {
        try {
            this.serverSocket.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SocketClient getClient(WebSocket socket) {
        for (int i = 0; i < connections.size(); i++) {
            SocketClient c = connections.get(i);
            if (c != null && c.getSocket() != null && c.getSocket().equals(socket)) {
                return c;
            }
        }
        return null;
    }

}
