package de.pkmnplatin.wnp4j.socket;

import de.pkmnplatin.wnp4j.WNP4J;
import de.pkmnplatin.wnp4j.listener.SocketListener;
import de.pkmnplatin.wnp4j.util.MessageType;
import lombok.Getter;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class WebsocketServer extends WebSocketServer {

    private List<SocketListener> listeners = new ArrayList<>();

    public WebsocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        SocketClient client = new SocketClient(conn, new ClientInfo());
        listeners.forEach(l -> l.onClientConnected(client));
        WNP4J.getInstance().getConnections().add(client);
        conn.send("VERSION:" + Integer.MAX_VALUE); // Trick the Plugin into thinking this WebServer is a RainMeter instance.
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        SocketClient client = WNP4J.getInstance().getClient(conn);
        listeners.forEach(l -> l.onClientDisconnected(client));
        WNP4J.getInstance().getConnections().remove(client);
        if (WNP4J.getInstance().getLastActiveDevice().equals(client)) {
            setLastActive(null);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        SocketClient client = WNP4J.getInstance().getClient(conn);
        if(client != null && message.contains(":")) {
            String[] split = message.split(":");
            MessageType type = Arrays.stream(MessageType.values()).filter(t -> t.name().toLowerCase().equals(split[0].toLowerCase())).findFirst().orElse(null);
            String content = String.join(":", Arrays.copyOfRange(split, 1, split.length));
            if(type != null) {
                client.parseMessage(type, content);
                listeners.forEach(l -> l.onClientMessage(client, type, content));
                setLastActive(client);
            }
        }
    }

    private void setLastActive(SocketClient client) {
        try {
            Field f = WNP4J.getInstance().getClass().getDeclaredField("lastActiveDevice");
            f.setAccessible(true);
            f.set(WNP4J.getInstance(), client);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        double heuristic = 0;
        SocketClient client = WNP4J.getInstance().getClient(conn);
        if(client == null) {
            if(conn != null) conn.close();
            return;
        }
        for (int i = 0; i < listeners.size(); i++) {
            SocketListener l = listeners.get(i);
            if (l.onError(client, ex)) {
                heuristic++;
            }
        }
        heuristic /= listeners.size();
        if(Math.round(heuristic) > 0) {
            conn.close();
        }
    }

    @Override
    public void onStart() {
        listeners.forEach(SocketListener::onSocketStartup);
        System.out.println("Started WNP4J successfully!");
        System.out.println("WebSocket listening on 'ws://127.0.0.1:" + WNP4J.getInstance().getPort() + "/'");
    }

    public boolean isRunning() {
        boolean running = false;
        try {
            Field f = getClass().getSuperclass().getDeclaredField("selectorthread");
            f.setAccessible(true);
            running = f.get(this) != null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return running;
    }

}
