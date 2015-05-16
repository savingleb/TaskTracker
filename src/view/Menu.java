package view;

import controller.CurrentTask;
import controller.RecordController;
import controller.TaskController;

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
        while (true) {
            System.out.println("\n1. Print task tree");
            System.out.println("2. Add task");
            System.out.println("3. Delete task");
            System.out.println("4. Rename task");
            System.out.println("5. Print statistic");
            System.out.println("6. Print Current task");
            System.out.println("7. Change current task");
            System.out.println("8. Exit");
            Scanner input = new Scanner(System.in);
            int select;
            if (input.hasNextInt())
                select = input.nextInt();
            else {
                System.out.println("Enter number between 1 and 8");
                continue;
            }
            if (select < 1 || select > 8) {
                System.out.println("Enter number between 1 and 8");
                continue;
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
        }
    }
}
