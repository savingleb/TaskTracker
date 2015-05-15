package view;

import db.DailyRecordEntity;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<TableRow> rows = new ArrayList<TableRow>();

    public Table(List<DailyRecordEntity> records) {
        for (DailyRecordEntity el : records) {
            rows.add(new TableRow(el));
        }
    }

    public void print() {
        System.out.printf("%3s| %-11s | %-20s | %-9s | %-3s |\n", "#", "Date", "Task", "Length", "%");
        for (int i = 0; i <= 58; i++)
            System.out.print("_");
        for (int i = 0; i < rows.size(); i++) {
            System.out.printf("\n%3d", i + 1);
            rows.get(i).printRow();
        }
    }
}
