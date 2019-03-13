import com.sun.net.httpserver.HttpServer;
import handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Family map server.
 */
public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/user/register", new RegisterHandler());
            server.createContext("/user/login", new LoginHandler());
            server.createContext("/clear", new ClearHandler());
            server.createContext("/fill/", new FillHandler());
            server.createContext("/person", new PersonHandler());
            server.createContext("/event", new EventHandler());
            server.createContext("/load", new LoadHandler());
            server.createContext("/", new FileHandler());

            server.setExecutor(null); // creates a default executor
            server.start();

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
