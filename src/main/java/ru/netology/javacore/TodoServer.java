package ru.netology.javacore;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {

    static class Request {

        String type, task;

        public Request(String type, String task) {
            this.type = type;
            this.task = task;
        }

        @Override
        public String toString() {
            return "type = '" + type + "', task = '" + task + "'";
        }
    }

    private final int port;
    private final Todos todos;

    public TodoServer(int port, Todos todos) {

        this.port = port;
        this.todos = todos;

    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("\nStarting server at " + this.port + "... \nServer started...\n");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection accepted!");
                System.out.println("Client address: " + clientSocket.getInetAddress() +
                        " , port: " + clientSocket.getPort());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String json = in.readLine();
                System.out.println("Client message: " + json);
                Request r = new Gson().fromJson(json, Request.class);
                switch (r.type) {
                    case "ADD":
                        System.out.println("Add task '" + r.task + "' to TODO list");
                        todos.addTask(r.task);
                        break;
                    case "REMOVE":
                        System.out.println("Remove task '" + r.task + "' from TODO list");
                        todos.removeTask(r.task);
                        break;
                }
                System.out.println("Send TODO list to client... ");
                out.println(todos.getAllTasks());
                System.out.println("Complete!\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "TodoServer { " +
                " port = " + port +
                ", todos = " + todos +
                " } ";
    }
}
