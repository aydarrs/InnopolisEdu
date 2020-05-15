package part1.lesson08.task01;

import java.io.Serializable;
import java.util.Objects;

/**
 * ExperimentalObject.
 * Serializable class.
 * @author Aydar_Safiullin
 */
public class ExperimentalObject implements Serializable {
    private static final long serialVersionUID = 1113671416999L;
    private int id;
    private String name;

    public ExperimentalObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("id: %d\nИмя: %s", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentalObject object = (ExperimentalObject) o;
        return id == object.id &&
                Objects.equals(name, object.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
