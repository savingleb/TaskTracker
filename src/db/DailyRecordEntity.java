package db;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "DAILY_RECORD", schema = "TDEV", catalog = "")
public class DailyRecordEntity {
    private int id;
    private Date rDate;
    private long length;
    private TaskEntity task;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "R_DATE")
    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDate) {
        this.rDate = rDate;
    }

    @Basic
    @Column(name = "LENGTH")
    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyRecordEntity that = (DailyRecordEntity) o;
        if (id != that.id) return false;
        if (length != that.length) return false;
        if (rDate != null ? !rDate.equals(that.rDate) : that.rDate != null) return false;
        if (task!=null ? !task.equals(that.task): that.task!=null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (rDate != null ? rDate.hashCode() : 0);
        result = 31 * result + (int) (length ^ (length >>> 32));
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", nullable = false)
    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }
}