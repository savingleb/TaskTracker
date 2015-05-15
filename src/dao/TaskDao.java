package dao;
import org.hibernate.Query;
import util.HibernateUtil;
import db.TaskEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class TaskDao {
    private static Session session=HibernateUtil.getSessionFactory().openSession();
    public final int ROOT_ID=7;
    public final int NOT_WORK_ID=75;

    //singleton
    private static TaskDao instance=null;

    private TaskDao(){}

    public static TaskDao getInstance(){
        if (instance==null)
            instance=new TaskDao();
        return instance;
    }


    public boolean check() { //just for debug tree
        int[] checking = new int[5];
        session.beginTransaction();
        for (int i = 0; i < 5; i++) {
            checking[i] = (Integer) session.createSQLQuery(
                    "select check" + (i + 1) + "() ch from dual").addScalar("ch",
                    IntegerType.INSTANCE).uniqueResult();
        }
        session.getTransaction().commit();
        for (int i = 0; i < 5; i++) {
            if (checking[i] != 1) {
                System.out.print("incorrect nested set tree. check query - " + i+1);
                return false;
            }
            System.out.print("correct check query " + i + "\n");
        }
        return true;
    }

    public List<TaskEntity> getTasks() {
        List<TaskEntity> tasks;
        session.beginTransaction();
        tasks = (ArrayList<TaskEntity>) session.createCriteria(TaskEntity.class)
                .addOrder(Order.asc("leftKey")).list();
        session.getTransaction().commit();
        return tasks;
    }

    public void addTask(int parentId, String name) {
        session.beginTransaction();
        Query  query= session.getNamedQuery("AddTask")
                .setParameter("parentId", parentId)
                .setParameter("name", name);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void deleteTask(int id) {
        session.beginTransaction();
        Query  query= session.getNamedQuery("DeleteTask").setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public TaskEntity getTaskById(int id){
        TaskEntity task=null;
        session.beginTransaction();
        task=(TaskEntity)session.get(TaskEntity.class, id);
        session.getTransaction().commit();
        return task;
    }

    public void renameTask(int id, String newName){
        TaskEntity task=getTaskById(id);
        task.setName(newName);
        session.beginTransaction();
        session.update(task);
        session.getTransaction().commit();
    }

    public List<TaskEntity> getSubTasks(int id) {
        TaskEntity task=getTaskById(id);
        List<TaskEntity> subTasks;
        session.beginTransaction();
        subTasks = (ArrayList<TaskEntity>) session.createCriteria(TaskEntity.class)
                .add(Restrictions.ge("leftKey", task.getLeftKey()))
                .add(Restrictions.le("rightKey",task.getRightKey()))
                .addOrder(Order.asc("leftKey")).list();
        session.getTransaction().commit();
        return subTasks;
    }
}
