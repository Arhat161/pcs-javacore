package ru.netology.javacore;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {

    static class Response {
        String type;
        String task;

        @Override
        public String toString() {
            return "type = " + type + ", task = " + task;
        }
    }

    private int port = 8081;

    public TodoServer(int port, Todos todos) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection accepted");
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String json = in.readLine();
                Response r = new Gson().fromJson(json, Response.class);
                switch (r.type) {
                    case "ADD":
                        todos.addTask(r.task);
                        break;
                    case "REMOVE":
                        todos.removeTask(r.task);
                        break;
                }
                out.println(todos.getAllTasks());
                System.out.println(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Starting server at " + port + "...");
    }
}
