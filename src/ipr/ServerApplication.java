package ipr;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerApplication {
    private final int port = 6666;


    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port); // сокет для установить соединение

            while (true) {
                Socket socket = serverSocket.accept(); // сокет для ПЕРЕДАЧИ ДАННЫХ //на этом моменте останавливается прога до тех пор, пока не подключиться клиент
                System.out.println("new connection! port: " + port);
                ServerConnectionHandler serverConnectionHandler = new ServerConnectionHandler(socket);

                new Thread(new ClientHandler(serverConnectionHandler)).start();
                Date current = new Date();
                ProgramLogger.getProgramLogger().addLogInfo("Дата: " + current + ". Сервер: готов к подключению! --- Ожидание подключения с клиентом...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}