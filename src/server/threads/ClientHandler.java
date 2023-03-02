package server.threads;

import server.ServerApp;
import shared.Info;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private Socket clientSocket;
    private ArrayList<Info> mensajes;

    public ClientHandler(Socket socket, ArrayList<Info> mensajes) {
        this.clientSocket = socket;
        this.mensajes = mensajes;
    }

    @Override
    public void run() {
        try {
            System.out.println("========================");
            String msg = "La conexión se ha realizado con ";
            msg += this.clientSocket.getInetAddress().toString();

            System.out.println(msg);
            System.out.println("========================");

            ObjectOutputStream toClientStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
            toClientStream.writeObject(this.mensajes);

            ObjectInputStream fromClientStream = new ObjectInputStream(this.clientSocket.getInputStream());

            Info chatData = null;

            while (true) {

                try {
                    chatData = (Info) fromClientStream.readObject();
                    System.out.println(chatData.getTime() + " " + chatData.getName() + ": " + chatData.getMessage());
                    ServerApp.addMessage(chatData);

                    if (chatData.getMessage().equals("bye")) {

                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }

            System.out.println(chatData.getName() + " ha dejado la conversacion");
            fromClientStream.close();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Se ha producido un error");
        }
    }
}
