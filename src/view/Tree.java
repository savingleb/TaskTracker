package view;

import dao.TaskDao;
import db.TaskEntity;
import java.util.List;

public class Tree {
    //return amount of tasks
    public int printTasks(){
        System.out.print("\nTask tree: \n");
        List<TaskEntity> tasks=new TaskDao().getTasks();
        int i=0;
        for (TaskEntity el:tasks)
        {
            System.out.printf("%-3s", i++ + ".");
            System.out.print("  ");
            for (int j=1; j<el.gettLevel();j++)
                System.out.print("*");
            System.out.print(el.getName()+"\n");
        }
        System.out.print("\n");
        return tasks.size();
    }
}
