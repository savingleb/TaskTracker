package controller;

import dao.TaskDao;
import db.TaskEntity;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskController {

    TaskEntity selectTask(){
        while (true) {
            int amount=new view.Tree().printTasks();
            System.out.print("Select task: ");
            Scanner input = new Scanner(System.in);
            int select;
            try {
                select = input.nextInt();
            }
            catch (InputMismatchException e){
                select=-1;
            }
            if (select <0 || select >amount-1) {
                System.out.print("Enter number between 0 and "+(amount-1)+"\n");
                continue;
            }
            List<TaskEntity> tasks = new TaskDao().getTasks();
            return tasks.get(select);
        }
    }


    public void renameTask(){
        TaskDao taskDao=new TaskDao();
        while (true) {
            TaskEntity task=selectTask();
            if (task.getId()==taskDao.ROOT_ID || task.getId()==taskDao.NOT_WORK_ID) {
                System.out.print("Can't rename default task\n");
                continue;
            }
            System.out.print("Enter new task name: ");
            Scanner input = new Scanner(System.in);
            String newName=input.next();
            new TaskDao().renameTask(task.getId(),newName);
            break;
        }
    }
    public void addTask(){
        TaskDao taskDao=new TaskDao();
            System.out.print("Enter number of parent task. new task will be leaf of this task: ");
            Scanner input = new Scanner(System.in);
            TaskEntity task=selectTask();
            System.out.print("Enter new task name: ");
            String name=input.next();
            new TaskDao().addTask(task.getId(), name);
    }

    public void deleteTask(){
        TaskDao taskDao=new TaskDao();
        while (true) {
            TaskEntity task=selectTask();
            if (task.getId()==taskDao.ROOT_ID || task.getId()==taskDao.NOT_WORK_ID) {
                System.out.print("Can't delete default task\n");
                continue;
            }
            new TaskDao().deleteTask(task.getId());
            break;
        }
    }
}
