package view.comparators;

import view.TableRow;

import java.util.Comparator;


public class PercComparator implements Comparator<TableRow> {
    @Override
    public int compare(TableRow o1, TableRow o2) {
        return (o1.getPercentage()-o2.getPercentage());
    }
}
