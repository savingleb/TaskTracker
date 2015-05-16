package view;

import dao.DailyRecordDao;
import dao.TaskDao;
import db.DailyRecordEntity;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class TableRow {
    private Date date;
    private String task;
    private Date length;
    private  int percentage;

    public TableRow(DailyRecordEntity record){
        task=record.getTask().getName();
        date=record.getrDate();
        length=new Date(record.getLength());
        percentage=calculatePercentage(record);
    }

    private int calculatePercentage(DailyRecordEntity record){
        List<DailyRecordEntity> records= DailyRecordDao.getInstance()
                .getRecordsList(Restrictions.eq("rDate", record.getrDate()), Order.asc("rDate"));
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

