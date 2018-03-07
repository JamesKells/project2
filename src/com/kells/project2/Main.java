package com.kells.project2;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


class Task {

    private String name;
    private String description;
    private int priority;

    Task(String name, String description, int priority)
    {
        setName(name);
        setDescription(description);
        setPriority(priority);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        if (priority < 0) {
            this.priority = 0;
        }
        else if (priority > 5) {
            this.priority = 5;
        }
        else
            this.priority = priority;
    }
}

class TaskCollection {

    private List<Task> tasks;

    TaskCollection()
    {
        this.tasks = new LinkedList();
    }

    private Task createTask(String name, String description, int priority) {
        return new Task(name, description, priority);
    }

    public void addTask(String name, String description, int priority) {
        this.tasks.add(createTask(name, description, priority));
    }

    public void removeTask(int index) {
        if (this.tasks.size() > index)
            this.tasks.remove(index);
    }

    public void updateTask(int index, String name, String description, int priority)
    {
        this.tasks.set(index, createTask(name, description, priority));
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public List<Task> getTasks(int priorty) {
        List matchedTasks = new LinkedList();
        for (Task task : this.tasks) {
            if (task.getPriority() == priorty) {
                matchedTasks.add(task);
            }
        }
        return matchedTasks;
    }
}

class TaskView {

    private TaskCollection taskCollection;
    private Scanner scanner = new Scanner(System.in);

    TaskView(TaskCollection collection)
    {
        this.taskCollection = collection;
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        }
        catch (NumberFormatException|NullPointerException e) {
            return false;
        }
        return true;
    }

    private String prompt(String message) {
        System.out.println(message);
        return this.scanner.nextLine();
    }

    private int promptInt(String message) {
        String response = prompt(message);

        boolean isInt = isInteger(response);
        while (!isInt) {
            response = prompt(message);
            isInt = isInteger(response);
        }
        return Integer.parseInt(response);
    }

    private void add() {
        String name = prompt("Enter new task's name. ");
        String description = prompt("Enter new task's description. ");
        int priority = promptInt("Enter new task's priority. ");
        this.taskCollection.addTask(name, description, priority);
    }

    private void remove() {
        int index = promptInt("Enter the index of the task to delete. ");
        this.taskCollection.removeTask(index);
    }

    private void update() {
        int index = promptInt("Enter the index of task to update. ");
        String newName = prompt("Enter new task name. ");
        String newDescription = prompt("Enter new task description. ");
        int newPriority = promptInt("Enter new task priority. ");
        this.taskCollection.updateTask(index, newName, newDescription, newPriority);
    }

    private void displayTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = (Task)tasks.get(i);
            System.out.println("Task index: " + i + ", Name: " + task
                    .getName() + ", Description: " + task
                    .getDescription() + ", Priority: " + task
                    .getPriority());
        }
    }

    private void list() { displayTasks(this.taskCollection.getTasks()); }

    private void listByPriority()
    {
        int priority = promptInt("Enter priority. ");
        displayTasks(this.taskCollection.getTasks(priority));
    }

    private void menu() {
        boolean endLoop = false;
        while (!endLoop) {
            System.out.println("Menu");
            System.out.println("1) Add task.");
            System.out.println("2) Remove task.");
            System.out.println("3) Update task.");
            System.out.println("4) List tasks.");
            System.out.println("5) List tasks of a priority.");
            System.out.println("0) Exit");

            int userInput = promptInt("Choose a number. ");

            switch (userInput) {
                case 0:
                    endLoop = true;
                    break;
                case 1:
                    add();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    list();
                    break;
                case 5:
                    listByPriority();
            }
        }
    }

    public void run()
    {
        menu();
    }
}


public class Main {

    public static void main(String[] args) {
        TaskCollection collection = new TaskCollection();
        TaskView view = new TaskView(collection);
        view.run();
    }
}