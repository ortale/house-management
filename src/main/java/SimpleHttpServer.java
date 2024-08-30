import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        // Create an HttpServer instance
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);

        // Define handlers for different endpoints
        server.createContext("/api/houses/getAll", new GetAllHousesHandler());
        server.createContext("/api/houses/add", new AddHouseHandler());
//        server.createContext("/api/houses/update", new UpdateHouseHandler());
//        server.createContext("/api/houses/delete", new DeleteHouseHandler());
//        server.createContext("/api/houses/{houseId}/certificates/add", new AddCertificateHandler());
//        server.createContext("/api/houses/{houseId}/certificates/update", new UpdateCertificateHandler());
//        server.createContext("/api/houses/{houseId}/certificates/delete", new DeleteCertificateHandler());
//        server.createContext("/api/houses/{houseId}/payments/add", new AddPaymentHandler());
//        server.createContext("/api/houses/{houseId}/payments/update", new UpdatePaymentHandler());
//        server.createContext("/api/houses/{houseId}/payments/delete", new DeletePaymentHandler());

        // Start the server
        server.start();
        System.out.println("Server started on port 8000");
    }
}
