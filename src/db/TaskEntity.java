package db;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TASK", schema = "TDEV", catalog = "")
public class TaskEntity {
    private int id;
    private int tLevel;
    private int leftKey;
    private int rightKey;
    private String name;
    private Set<DailyRecordEntity> records = new HashSet<DailyRecordEntity>();

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "T_LEVEL")
    public int gettLevel() {
        return tLevel;
    }

    public void settLevel(int tLevel) {
        this.tLevel = tLevel;
    }

    @Basic
    @Column(name = "LEFT_KEY")
    public int getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    @Basic
    @Column(name = "RIGHT_KEY")
    public int getRightKey() {
        return rightKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (id != that.id) return false;
        if (tLevel != that.tLevel) return false;
        if (leftKey != that.leftKey) return false;
        if (rightKey != that.rightKey) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + tLevel;
        result = 31 * result + leftKey;
        result = 31 * result + rightKey;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY)
    public Set<DailyRecordEntity> getRecords() {
        return this.records;
    }

    public void setRecords(Set<DailyRecordEntity> records) {
        this.records = records;
    }
}
