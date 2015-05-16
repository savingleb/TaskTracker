package dao;
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
    static Session session=HibernateUtil.getSessionFactory().openSession();
    public final int ROOT_ID=7;
    public final int NOT_WORK_ID=75;

    public boolean check() { //debug tree
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        tasks = (ArrayList<TaskEntity>) session.createCriteria(TaskEntity.class)
                .addOrder(Order.asc("leftKey")).list();
        session.getTransaction().commit();
        if (session != null)
            session.close();
        return tasks;
    }

    public void addTask(int parentId, String name) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "TDEV";
        String password = "1234";
        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(url, user, password);
            CallableStatement statement;
            statement = connection.prepareCall("{call ADD_TASK(?,?)}");
            statement.setInt(1,parentId);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.print(e.getLocalizedMessage());
        }
    }

    public void deleteTask(int id) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "TDEV";
        String password = "1234";
        try {
            Locale.setDefault(Locale.ENGLISH);
            Connection connection = DriverManager.getConnection(url, user, password);
            CallableStatement statement;
            statement = connection.prepareCall("{call DELETE_TASK(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.print(e.getLocalizedMessage());
        }
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
