package controller;

import dao.DailyRecordDao;
import dao.TaskDao;
import db.TaskEntity;
import view.Table;
import view.TableRow;
import view.comparators.DateComparator;
import view.comparators.LengthComparator;
import view.comparators.PercComparator;
import view.comparators.TaskNameComparator;

import java.text.ParseException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RecordController {

    public void printStatistic() {
        System.out.print("\nSelect criterion\n");
        while (true) {
            System.out.print("\n1. Tasks\n");
            System.out.print("2. Task and subtasks\n");
            System.out.print("3. Date\n");
            System.out.print("4. Date Range\n");
            Scanner input = new Scanner(System.in);
            int select;
            try {
                select = input.nextInt();
            }
            catch (InputMismatchException e){
                select=-1;
            }
            if (select<1 || select>4){
                System.out.print("Enter number between 1 and 4:\n");
                continue;
            }
            Table table = null;
            switch (select) {
                case 1: {
                    TaskEntity task = new TaskController().selectTask();
                    table = new Table(new DailyRecordDao().getRecordsList(task));
                    break;
                }
                case 2: {
                    TaskEntity task = new TaskController().selectTask();
                    List<TaskEntity> tasks = new TaskDao().getSubTasks(task.getId());
                    table = new Table(new DailyRecordDao().getRecordsList(tasks));
                    break;
                }
                case 3:
                    Date date = readDate();
                    table = new Table(new DailyRecordDao().getRecordsList(date));
                    break;
                case 4:
                    Date date1 = readDate();
                    Date date2 = readDate();
                    table = new Table(new DailyRecordDao().getRecordsList(date1, date2));
                    break;
            }
            selectSort(table);
            if (table != null)
                table.print();
            break;
        }
    }

    private void selectSort(Table table) {
        while (true) {
            System.out.print("\nSelect sort parameter\n");
            System.out.print("1. Date\n");
            System.out.print("2. Task name\n");
            System.out.print("3. Task length\n");
            System.out.print("4. Percentage\n");
            Scanner input = new Scanner(System.in);
            int select;
            try {
                select = input.nextInt();
            }
            catch (InputMismatchException e){
                select=-1;
            }
            if (select<1 || select>4){
                System.out.print("Enter number between 1 and 4:\n");
                continue;
            }
            Comparator<TableRow> comparator = null;
            switch (select) {
                case 1:
                    comparator = new DateComparator();
                    break;
                case 2:
                    comparator = new TaskNameComparator();
                    break;
                case 3:
                    comparator = new LengthComparator();
                    break;
                case 4:
                    comparator = new PercComparator();
                    break;
            }
            if (comparator != null)
                table.sort(comparator);
            selectSortOrder(table);
            break;
        }
    }

    private void selectSortOrder(Table table){
        while (true) {
            System.out.print("\nSelect sort order\n");
            System.out.print("1. Ascending\n");
            System.out.print("2. Descending\n");
            Scanner input = new Scanner(System.in);
            int select;
            try {
                select = input.nextInt();
            }
            catch (InputMismatchException e){
                select=-1;
            }
            if (select==1)
                break;
            else if (select==2){
                table.descSort();
                break;
            }
            System.out.print("Enter 1 or 2:\n");
        }
    }

    public Date readDate() {
        System.out.print("Enter date in format yyyy/mm/dd\n");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Scanner input = new Scanner(System.in);
        String inString = input.next();
        Date date;
        try {
            date = new Date(format.parse(inString).getTime());
        } catch (ParseException e) {
            System.out.print("incorrect date format\n");
            date = readDate();
        }
        return date;
    }
}
