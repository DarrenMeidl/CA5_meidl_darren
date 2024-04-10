package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class DAOServer {
    final int PORT_NUMBER = 11111;

    public static void main(String[] args) {
        DAOServer server = new DAOServer();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
             Socket clientSocket = serverSocket.accept();       // start server to listen on port number 8888
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("The DAOServer server has started and is waiting for a client to connect.");

            String message = in.readLine(); // read the incoming message - terminated by a newline ("\n")
            // program stops and waits here until input (followed by '\n') arrives.

            System.out.println("The GreetingServer has received this message from a client: " + message);
            System.out.println("The server is replying to the client.");

            if ("hello server".equals(message)) {
                out.println("hello client");    // send response back to client
            } else {
                out.println("unrecognised greeting");
            }

            System.out.println("The DAOServer is finished, and is exiting. Goodbye!");

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
