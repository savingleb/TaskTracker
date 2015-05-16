package controller;

import dao.DailyRecordDao;
import dao.TaskDao;
import db.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//singleton
public class CurrentTask {
    private static CurrentTask instance;
    private TaskEntity task;
    private GregorianCalendar startTime;
    final long TIME_ZONE_DIF=10800000;

    private CurrentTask(){
        TaskDao dao=TaskDao.getInstance();
        task=dao.getTaskById(dao.NOT_WORK_ID);
        startTime=new GregorianCalendar();
    }

    public static CurrentTask getInstance(){
        if (instance==null)
            instance=new CurrentTask();
        return instance;
    }

    public void saveTaskTime(){
        DailyRecordDao recordDao=DailyRecordDao.getInstance();
        Calendar currentTime=Calendar.getInstance();
        long length=Calendar.getInstance().getTimeInMillis() - startTime.getTimeInMillis();
        recordDao.applyRecord(length, task);
        System.out.print("record saved\n");
        startTime.setTimeInMillis(currentTime.getTimeInMillis());
    }

    public void changeTask(){
        this.saveTaskTime();
        TaskController controller=new TaskController();
        System.out.print("Select new current task\n");
        TaskEntity newTask=controller.selectTask();
        TaskDao taskDao=TaskDao.getInstance();
        if (task.getId() == taskDao.ROOT_ID || task.getId() == taskDao.NOT_WORK_ID) {
            System.out.println("Can't rename default task");
        }
        task=newTask;
        startTime=new GregorianCalendar();
    }

    public void printCurrentTask(){
        System.out.println("Current task: " + task.getName());
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        System.out.println("Started: " + dateFormat.format(new Date(startTime.getTimeInMillis())));
        GregorianCalendar current=new GregorianCalendar();
        Date last=new Date(current.getTimeInMillis() - startTime.getTimeInMillis() - TIME_ZONE_DIF);
        System.out.println("Last: "+dateFormat.format(last));
    }
}