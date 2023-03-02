package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.threads.ClientHandler;
import shared.Info;


public class ServerApp {
    private static ArrayList<Info> mensajes = new ArrayList<>();
    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(8080);

            while (true) {
                System.out.println("========================");
                System.out.println("Esperando a que se realice una conexión.");
                Socket clientSocket = serverSocket.accept();
                ClientHandler newClient = new ClientHandler(clientSocket, mensajes);
                newClient.start();
            }

        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(Info message) {
        mensajes.add(message);
    }
}
