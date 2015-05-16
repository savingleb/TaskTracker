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
    TaskEntity task;
    GregorianCalendar startTime;
    final long TIME_ZONE_DIF=10800000;

    private CurrentTask(){
        TaskDao dao=new TaskDao();
        task=dao.getTaskById(dao.NOT_WORK_ID);
        startTime=new GregorianCalendar();
    }

    public static CurrentTask getInstance(){
        if (instance==null)
            instance=new CurrentTask();
        return instance;
    }

    void saveTaskTime(){
        DailyRecordDao recordDao=new DailyRecordDao();
        Calendar currentTime=Calendar.getInstance();
        long length=Calendar.getInstance().getTimeInMillis() - startTime.getTimeInMillis();
        recordDao.applyRecord(length, task);
        System.out.print("record saved\n");
        startTime.setTimeInMillis(currentTime.getTimeInMillis());
    }

    void changeTask(){
        this.saveTaskTime();
        TaskController controller=new TaskController();
        System.out.print("Select new current task\n");
        task=controller.selectTask();
        startTime=new GregorianCalendar();
    }

    void printCurrentTask(){
        System.out.print("\nCurrent task: " + task.getName());
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        System.out.print("\nStarted: " + dateFormat.format(new Date(startTime.getTimeInMillis())));
        GregorianCalendar current=new GregorianCalendar();
        Date last=new Date(current.getTimeInMillis() - startTime.getTimeInMillis() - TIME_ZONE_DIF);
        System.out.print("\nLast: "+dateFormat.format(last));
    }
}