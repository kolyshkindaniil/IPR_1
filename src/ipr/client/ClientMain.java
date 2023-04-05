package ipr.client;

import ipr.ProgramLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        ClientConnection connection = null;
        int port = 6666;
        String host = "localhost";

        connection = new ClientConnection(new Socket(host, port));
        System.out.println("new connection. port: " + port + ", " +
                "host: " + host);

        Date current = new Date();
        ProgramLogger.getProgramLogger().addLogInfo("Дата: " + current + ". Клиент: подключение с сервером установлено.");

        while (true) {
            System.out.println("1 - Просмотреть, 2 - Выход");

            Scanner in = new Scanner(System.in);
            System.out.print("Input: ");
            String choice = in.nextLine();

            if(choice.equals("1")){
                ProgramLogger.getProgramLogger().addLogInfo("Клиент: Отправка запроса на получение данных.");
                connection.write(choice);

                ArrayList <String> result = connection.read();
                ProgramLogger.getProgramLogger().addLogInfo("Клиент: Данные от сервера получены!");

                System.out.println("\n<-- Result work of Factory Pattern --> ");
                for(Object el : result){
                    System.out.println(el);
                }
            }
            else{
                if(choice.equals("2")){
                    connection.write("2"); //отправляем серверу слово дисконект (в место где switch case "connect" "disconnect")
                    ProgramLogger.getProgramLogger().addLogInfo("Клиент: Отключение от сервера.");
                    connection.close(); //закрываем соединение со стороны клиента
                    return;
                }
                else{
                    System.out.println("Неверный ввод");
                    ProgramLogger.getProgramLogger().addLogInfo("Клиент: неверный выбор меню на клиенте.");
                }
            }



        }
    }
}
