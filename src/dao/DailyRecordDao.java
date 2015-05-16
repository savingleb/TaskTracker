package dao;

import db.DailyRecordEntity;
import util.HibernateUtil;
import db.TaskEntity;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DailyRecordDao {
    static Session session= HibernateUtil.getSessionFactory().openSession();

    public DailyRecordEntity getRecord(int id) {
        DailyRecordEntity record;
        session.beginTransaction();
        record = (DailyRecordEntity) session.get(DailyRecordEntity.class, id);
        session.getTransaction().commit();
        return record;
    }

    public DailyRecordEntity getRecord(Date date, TaskEntity task){
        DailyRecordEntity result;
        session.beginTransaction();
        result=(DailyRecordEntity)session.createCriteria(DailyRecordEntity.class)
                .add(Restrictions.eq("rDate", date))
                .add(Restrictions.eq("task", task)).uniqueResult();
        session.getTransaction().commit();
        return result;
    }

    public List<DailyRecordEntity> getRecordsList(Date date){
        List<DailyRecordEntity> result;
        session.beginTransaction();
        result=(ArrayList<DailyRecordEntity>)session
                .createCriteria(DailyRecordEntity.class)
                .add(Restrictions.eq("rDate", date)).list();
        session.getTransaction().commit();
        return result;
    }

    public List<DailyRecordEntity> getRecordsList(Date date1, Date date2){
        List<DailyRecordEntity> result;
        session.beginTransaction();
        result=(ArrayList<DailyRecordEntity>)session
                .createCriteria(DailyRecordEntity.class)
                .add(Restrictions.ge("rDate", date1))
                .add(Restrictions.le("rDate", date2)).list();
        session.getTransaction().commit();
        return result;
    }

    public List<DailyRecordEntity> getRecordsList(TaskEntity task){
        List<DailyRecordEntity> result;
        session.beginTransaction();
        result=(ArrayList<DailyRecordEntity>)session
                .createCriteria(DailyRecordEntity.class)
                .add(Restrictions.eq("task", task)).list();
        session.getTransaction().commit();
        return result;
    }

    public List<DailyRecordEntity> getRecordsList(List<TaskEntity> tasks){
        List<DailyRecordEntity> result;
        session.beginTransaction();
        result=(ArrayList<DailyRecordEntity>)session
                .createCriteria(DailyRecordEntity.class)
                .add(Restrictions.in("task", tasks.toArray())).list();
        session.getTransaction().commit();
        return result;
    }



    public void applyRecord(long length ,TaskEntity task ){
        Calendar currWithTime=new GregorianCalendar();
        GregorianCalendar currWithoutTime=new GregorianCalendar(currWithTime.get(Calendar.YEAR),
                currWithTime.get(Calendar.MONTH),
                currWithTime.get(Calendar.DAY_OF_MONTH));
        Date current=new Date(currWithoutTime.getTimeInMillis());
        DailyRecordEntity existedRecord;

        existedRecord=getRecord(current,task);
        if (existedRecord!=null){
            existedRecord.setLength(existedRecord.getLength() + length);
            session.beginTransaction();
            session.save(existedRecord);
            session.getTransaction().commit();
        }
        else{
            DailyRecordEntity newRecord=new DailyRecordEntity();
            newRecord.setLength(length);
            newRecord.setrDate(current);
            newRecord.setTask(task);
            session.beginTransaction();
            session.save(newRecord);
            session.getTransaction().commit();
        }
    }
}
