package controller;

import view.Tree;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    static Menu instance;

    private Menu() {
    }

    public static Menu getMenu() {
        if (instance == null)
            instance = new Menu();
        return instance;
    }

    public void printMenu() {
            System.out.print("\n1. Print task tree\n");
            System.out.print("2. Add task\n");
            System.out.print("3. Delete task\n");
            System.out.print("4. Rename task\n");
            System.out.print("5. Print statistic\n");
            System.out.print("6. Print Current task\n");
            System.out.print("7. Change current task\n");
            System.out.print("8. Exit\n");

            Scanner input = new Scanner(System.in);
        int select;
        try {
            select = input.nextInt();
        }
        catch (InputMismatchException e){
            select=0;
        }

            if (select < 1 || select > 8) {
                System.out.print("Enter number between 1 and 8");
                this.printMenu();
            }
            Tree tree = new Tree();
            switch (select) {
                case 1:
                    tree.printTasks();
                    break;
                case 2:
                    new TaskController().addTask();
                    break;
                case 3:
                    new TaskController().deleteTask();
                    break;
                case 4:
                    new TaskController().renameTask();
                    break;
                case 5:
                    new RecordController().printStatistic();
                    break;
                case 6:
                    CurrentTask.getInstance().printCurrentTask();
                    break;
                case 7:
                    CurrentTask.getInstance().changeTask();
                    break;
                case 8:
                    CurrentTask.getInstance().saveTaskTime();
                    System.exit(1);
            }
            this.printMenu();
    }
}
