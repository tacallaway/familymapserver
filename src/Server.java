import com.sun.net.httpserver.HttpServer;
import handler.RegisterHandler;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/user/register", new RegisterHandler());
            server.setExecutor(null); // creates a default executor
            server.start();

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
