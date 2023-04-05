package ipr;

//import server.util.Factorial;

import java.io.IOException;
import java.util.ArrayList;

public class ClientHandler implements Runnable { //Runnable - 'это интерфейс. Именно наследуем класс от интерфейса
    private final ServerConnectionHandler connectionHandler;
    ArrayList<String> array = new ArrayList<String>();

    public ClientHandler(ServerConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override // показывает что мы переопределяем метод
    public void run() {
        try {
            while (true) {
                String message = connectionHandler.read();
                ProgramLogger.getProgramLogger().addLogInfo("Сервер: чтение запроса от клиента.");
                switch (message) {
                    case "1": {
                        System.out.println("Выбран 1 - Просмотр");

                        //System.out.println("\n<-- Result work of Factory Pattern --> ");
                        Factory factory = new Factory();
                        ProgramLogger.getProgramLogger().addLogInfo("Объект factory создан!");
                        kindOfPerson StatusPR = factory.create(1);   // John - 20
                        kindOfPerson StatusManager = factory.create(2);   // Sara - 39
                        kindOfPerson StatusPR2 = factory.create(1); // Stev - 23
                        kindOfPerson StatusDirector = factory.create(3);  // Bond - 45

                        StatusPR.show();
                        ProgramLogger.getProgramLogger().addLogInfo("Показано StatusPR");
                        StatusManager.show();
                        ProgramLogger.getProgramLogger().addLogInfo("Показано StatusManager");
                        StatusPR2.show();
                        ProgramLogger.getProgramLogger().addLogInfo("Показано StatusSalesman");
                        StatusDirector.show();
                        ProgramLogger.getProgramLogger().addLogInfo("Показано StatusDirector");


                        ProgramLogger.getProgramLogger().addLogInfo("Сервер: Отправка запрашиваемых данных клиенту.");
                        connectionHandler.write(array);
                    }
                    break;
                    case "2": {
                        System.out.println("Выбран 2 - Выход");
                        connectionHandler.close();
                        System.out.println("disconnect");
                        ProgramLogger.getProgramLogger().addLogInfo("Сервер: Отключение...");
                        return;
                    }
                }
            }
        }  catch ( IOException e) {
        e.printStackTrace();
    }

    }

    interface kindOfPerson {
        void show();
    }

    class StatusPR1 implements kindOfPerson {

        @Override
        public void show() {
            //System.out.println("Status : PR");
            array.add("Status : PR");
        }
    }

    class StatusManager1 implements kindOfPerson {

        @Override
        public void show() {
            //System.out.println("Status : Manager");
            array.add("Status : Manager");
        }
    }

    class StatusDirector1 implements kindOfPerson {

        @Override
        public void show() {
            //System.out.println("Status : Director");
            array.add("Status : Director");

        }
    }

    class Factory {
        public kindOfPerson create(int index) {
            switch (index) {
                case (1)  : return new StatusPR1();
                case (2) : return new StatusManager1();
                case(3) : return new StatusDirector1();
                default : return null;
            }
        }
    }
}

