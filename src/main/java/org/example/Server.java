package org.example;

import org.example.DAO.CircuitDaoInterface;
import org.example.DAO.JsonConverter;
import org.example.DTO.Circuit;
import org.example.Exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

// Taken from oop-client-server-multithreaded-2024 sample

public class Server {

    final int SERVER_PORT_NUMBER = 8888;  // could be any port from 1024 to 49151 (that doesn't clash with other Apps)

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)

            while (true) {
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);

                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                // create a new ClientHandler for the requesting client, passing in the socket and client number,
                // pass the handler into a new thread, and start the handler running in the thread.
                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(clientSocket!=null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
}

// Taken from oop-client-server-multithreaded-2024 sample

class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;
    private CircuitDaoInterface circuitDaoInterface;
    private JsonConverter jsonConverter;

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        try {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Taken from oop-client-server-multithreaded-2024 sample

    @Override
    public void run() {
        String request;
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);

                // Implement our PROTOCOL
                // The protocol is the logic that determines the responses given based on requests received.
                //
                if (request.startsWith("1"))  // so, client wants the time !
                {
                    Circuit c = circuitDaoInterface.getCircuitById(2);
                    String jsonMessage = jsonConverter.circuitToJson(c);
                    socketWriter.println(jsonMessage); // send the received message back to the client
                    System.out.println("Server message: time sent to client.");

                } else if (request.startsWith("2")) {
                    List<Circuit> message = circuitDaoInterface.getAllCircuits();
                    String jsonMessage = jsonConverter.circuitListToJson(message);
                    socketWriter.println(jsonMessage);   // send the received message back to the client
                    System.out.println("Server message: json message sent to client.");

                } else if (request.startsWith("3"))
                {
                    socketWriter.println("Sorry to see you leaving. Goodbye.");
                    System.out.println("Server message: Client has notified us that it is quitting.");
                }
                else{
                    socketWriter.println("error I'm sorry I don't understand your request");
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } finally {
            this.socketWriter.close();
            try {
                this.socketReader.close();
                this.clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }
}


