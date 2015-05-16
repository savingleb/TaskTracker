package controller;

import dao.DailyRecordDao;
import dao.TaskDao;
import db.TaskEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import view.Table;

import java.text.ParseException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecordController {

    private Order orderByAttribute(String attribute, OrderType orderType) {
        if (orderType == OrderType.DESC) {
            return Order.desc(attribute);
        }
        return Order.asc(attribute);
    }

    private Criterion restrictContent(Date date) {
        return Restrictions.eq("rDate", date);
    }

    private Criterion restrictContent(Date date1, Date date2) {
        return Restrictions.between("rDate", date1, date2);
    }

    private Criterion restrictContent(TaskEntity task) {
        return Restrictions.eq("task", task);
    }

    private Criterion restrictContent(List<TaskEntity> tasks) {
        return Restrictions.in("task", tasks.toArray());
    }


    public void printStatistic() {
        System.out.println("Select criterion");
        while (true) {
            System.out.println("1. Tasks");
            System.out.println("2. Task and subtasks");
            System.out.println("3. Date");
            System.out.println("4. Date Range");
            int select;
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt())
                select = input.nextInt();
            else {
                System.out.println("Enter number between 1 and 4");
                continue;
            }
            if (select < 1 || select > 4) {
                System.out.println("Enter number between 1 and 4:");
                continue;
            }
            Criterion criterion = null;
            DailyRecordDao dailyRecordDao =DailyRecordDao.getInstance();
            switch (select) {
                case 1: {
                    TaskEntity task = new TaskController().selectTask();
                    criterion = restrictContent(task);
                    break;
                }
                case 2: {
                    TaskEntity task = new TaskController().selectTask();
                    List<TaskEntity> tasks =TaskDao.getInstance().getSubTasks(task.getId());
                    criterion = restrictContent(tasks);
                    break;
                }
                case 3:
                    Date date = readDate();
                    criterion = restrictContent(date);
                    break;
                case 4:
                    Date date1 = readDate();
                    Date date2 = readDate();
                    criterion = restrictContent(date1, date2);
                    break;
            }
            Order order = selectSort();
            Table table = new Table(dailyRecordDao.getRecordsList(criterion, order));
            table.print();
            break;
        }
    }

    private Order selectSort() {
        while (true) {
            System.out.println("Select sort parameter");
            System.out.println("1. Date");
            System.out.println("2. Task name");
            System.out.println("3. Task length");
            int select;
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt())
                select = input.nextInt();
            else {
                System.out.println("Enter number between 1 and 3");
                continue;
            }
            if (select < 1 || select > 3) {
                System.out.println("Enter number between 1 and 3");
                continue;
            }
            OrderType orderType = selectSortOrder();

            switch (select) {
                case 1:
                    return orderByAttribute("rDate", orderType);
                case 2:
                    return orderByAttribute("task", orderType);
                case 3:
                    return orderByAttribute("length", orderType);
            }
        }
    }

    private OrderType selectSortOrder() {
        while (true) {
            System.out.println("Select sort order");
            System.out.println("1. Ascending");
            System.out.println("2. Descending");
            int select;
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt())
                select = input.nextInt();
            else {
                System.out.println("Enter 1 or 2:");
                continue;
            }
            if (select == 1)
                return OrderType.ASC;
            else if (select == 2) {
                return OrderType.DESC;
            }
            System.out.println("Enter 1 or 2:");
        }
    }

    public Date readDate() {
        while (true) {
            System.out.println("Enter date in format yyyy/mm/dd");
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Scanner input = new Scanner(System.in);
            String inString = input.next();
            Date date;
            try {
                date = new Date(format.parse(inString).getTime());
            } catch (ParseException e) {
                System.out.println("incorrect date format");
                continue;
            }
            return date;
        }
    }
}
