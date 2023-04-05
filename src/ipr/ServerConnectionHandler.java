package ipr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionHandler {
    private final Socket socket;
    private final ObjectOutputStream outputStream; //outputStream для записи данных в пункт назначения  //Для сериализации объектов в поток используется класс ObjectInputStream. Он записывает данные в поток
    private final ObjectInputStream inputStream;  // inputStream для чтения данных из источника

    public ServerConnectionHandler(Socket socket) {
        try {
            this.socket = socket; //Это сокет для соединения с клиентом ( для передачи данных)
            this.outputStream = new ObjectOutputStream(socket.getOutputStream()); //с помощью ObjectOutputStream мы можем использоваьт норм методы
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("can't create connection", e);
        }
    }

    public String read() {
        try {
            return (String) inputStream.readObject(); //преобразовываем с тип стринг //методы чтобы считывать с клиента readObject
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(ArrayList message) { // Для записи данных ObjectOutputStream использует метод write(), который записывает в поток один младший байт из message //это то что отправляем
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
