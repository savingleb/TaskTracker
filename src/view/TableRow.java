package view;

import db.DailyRecordEntity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class TableRow {
    Date date;
    String task;
    Date length;
    int percentage;

    public TableRow(DailyRecordEntity record){
        task=record.getTask().getName();
        date=record.getrDate();
        length=new Date(record.getLength());
        percentage=calculatePercentage(record);
    }

    private int calculatePercentage(DailyRecordEntity record){
        List<DailyRecordEntity> records=new dao.DailyRecordDao().getRecordsList(record.getrDate());
        long dayTasksLength=0;
        for (DailyRecordEntity el:records){
            dayTasksLength+=el.getLength();
        }
        return Math.round((float)record.getLength()/dayTasksLength*100);
    }

    public void printRow(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.printf("| %-11s | %-20s | %-9s | %-3d |",date,task,dateFormat.format(length), percentage);
    }


    public Date getDate() {
        return date;
    }

    public String getTask() {
        return task;
    }

    public Date getLength() {
        return length;
    }

    public int getPercentage() {
        return percentage;
    }
}

