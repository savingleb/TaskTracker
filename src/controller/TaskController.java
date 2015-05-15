package controller;

import dao.TaskDao;
import db.TaskEntity;

import java.util.List;
import java.util.Scanner;

public class TaskController {
    private static TaskDao taskDao =TaskDao.getInstance();

    TaskEntity selectTask() {
        while (true) {
            int amount = new view.Tree().printTasks();
            System.out.println("Select task: ");
            int select;
                Scanner input = new Scanner(System.in);
            if (input.hasNextInt())
                select = input.nextInt();
            else {
                System.out.println("Enter number between 0 and " + (amount - 1));
                continue;
            }
            if (select < 0 || select > amount - 1) {
                System.out.println("Enter number between 0 and " + (amount - 1));
                continue;
            }
            List<TaskEntity> tasks = taskDao.getTasks();
            return tasks.get(select);
        }
    }

    public void renameTask() {
        while (true) {
            TaskEntity task = selectTask();
            if (task.getId() == taskDao.ROOT_ID || task.getId() == taskDao.NOT_WORK_ID) {
                System.out.println("Can't rename default task");
                continue;
            }
            System.out.println("Enter new task name: ");
            Scanner input = new Scanner(System.in);
            String newName = input.next();
            taskDao.renameTask(task.getId(), newName);
            break;
        }
    }

    public void addTask() {
        System.out.println("Enter number of parent task. new task will be leaf of this task: ");
        Scanner input = new Scanner(System.in);
        TaskEntity task = selectTask();
        System.out.println("Enter new task name: ");
        String name = input.next();
        taskDao.addTask(task.getId(), name);
    }

    public void deleteTask() {
        while (true) {
            TaskEntity task = selectTask();
            if (task.getId() == taskDao.ROOT_ID || task.getId() == taskDao.NOT_WORK_ID) {
                System.out.println("Can't delete default task");
                continue;
            }
            taskDao.deleteTask(task.getId());
            break;
        }
    }
}
