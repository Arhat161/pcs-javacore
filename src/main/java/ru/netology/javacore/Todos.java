package ru.netology.javacore;

import java.util.*;

public class Todos {

    private ArrayList<String> allTasks;

    public Todos() {
        this.allTasks = new ArrayList<>();
    }

    public void addTask(String task) {
        this.allTasks.add(task);
    }

    public void removeTask(String task) {
        this.allTasks.remove(task);
    }

    public void setAllTasks(ArrayList<String> allTasks) {
        this.allTasks = allTasks;
    }

    public String getAllTasks() {
        Collections.sort(this.allTasks);
        StringBuilder sb = new StringBuilder();
        for (String task : this.allTasks) {
            sb.append(task);
            sb.append(" ");
        }
        return sb.toString();
    }

    public ArrayList<String> getAllTasksAsList() {
        return this.allTasks;
    }

    @Override
    public String toString() {
        return "Todos { " +
                " allTasks = " + allTasks +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todos)) return false;
        Todos todos = (Todos) o;
        return getAllTasks().equals(todos.getAllTasks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAllTasks());
    }
}
