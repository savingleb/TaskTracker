package dao;

import db.DailyRecordEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
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
    private static Session session = HibernateUtil.getSessionFactory().openSession();

    //singletonggggggggggg
    private static DailyRecordDao instance=null;

    private DailyRecordDao(){}

    public static DailyRecordDao getInstance(){
        if (instance==null)
            instance=new DailyRecordDao();
        return instance;
    }

    public DailyRecordEntity getRecord(Date date, TaskEntity task) {
        DailyRecordEntity result;
        session.beginTransaction();
        result = (DailyRecordEntity) session.createCriteria(DailyRecordEntity.class)
                .add(Restrictions.eq("rDate", date))
                .add(Restrictions.eq("task", task)).uniqueResult();
        session.getTransaction().commit();
        return result;
    }

    public void applyRecord(long length, TaskEntity task) {
        Calendar currWithTime = new GregorianCalendar();
        GregorianCalendar currWithoutTime = new GregorianCalendar(currWithTime.get(Calendar.YEAR),
                currWithTime.get(Calendar.MONTH),
                currWithTime.get(Calendar.DAY_OF_MONTH));
        Date current = new Date(currWithoutTime.getTimeInMillis());
        DailyRecordEntity existedRecord;
        existedRecord = getRecord(current, task);
        if (existedRecord != null) {
            existedRecord.setLength(existedRecord.getLength() + length);
            session.beginTransaction();
            session.save(existedRecord);
            session.getTransaction().commit();
        } else {
            DailyRecordEntity newRecord = new DailyRecordEntity();
            newRecord.setLength(length);
            newRecord.setrDate(current);
            newRecord.setTask(task);
            session.beginTransaction();
            session.save(newRecord);
            session.getTransaction().commit();
        }
    }

    public List<DailyRecordEntity> getRecordsList(Criterion contentCriterion, Order order) {
        List<DailyRecordEntity> result;
        session.beginTransaction();
        result = (ArrayList<DailyRecordEntity>) session
                .createCriteria(DailyRecordEntity.class)
                .add(contentCriterion)
                .addOrder(order).list();
        session.getTransaction().commit();
        return result;
    }
}