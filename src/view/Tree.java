package view;

import dao.TaskDao;
import db.TaskEntity;

import java.util.List;

public class Tree{


    //return amount of tasks
    public int printTasks(){
        System.out.println("Task tree: ");
        List<TaskEntity> tasks=TaskDao.getInstance().getTasks();
        int i=0;
        for (TaskEntity el:tasks)
        {
            System.out.printf("%-5s", i++ + ".");
            for (int j=1; j<el.gettLevel();j++)
                System.out.print("*");
            System.out.println(el.getName()+"");
        }
        System.out.println();
        return tasks.size();
    }
}
