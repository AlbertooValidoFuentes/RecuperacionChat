package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

import shared.Info;

public class ClientApp {
    public static void main(String[] args) {

        try {

            InetAddress myIp = InetAddress.getLocalHost();
            Socket socket = new Socket(myIp, 8080);

            Scanner scanner = new Scanner(System.in);
            ObjectOutputStream toServerStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServerStream = new ObjectInputStream(socket.getInputStream());

            try {
                ArrayList<Info> mensajes = (ArrayList<Info>) fromServerStream.readObject();

                System.out.println("Mensajes anteriores");

                for (int i = 0; i < mensajes.size(); i++) {
                    System.out.println(mensajes.get(i));
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Introduce tu nombre");
            String name = scanner.nextLine();

            while (true) {
                Info info = new Info();
                info.setName(name);

                System.out.println("Introduce un mensaje:");
                String message = scanner.nextLine();

                if (message.startsWith("message:")) {
                    info.setTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
                    info.setMessage(message.substring(9));
                    toServerStream.writeObject(info);

                } else {
                    System.out.println("Debes escribir message: para poder enviar un mensaje");
                }

                if (message.equals("bye")) {
                    info.setMessage("bye");
                    toServerStream.writeObject(info);
                    break;
                }
            }
            toServerStream.close();
            scanner.close();
            socket.close();

        } catch (IOException e) {
           System.out.println("Se ha producido un error");
        }
    }
}
