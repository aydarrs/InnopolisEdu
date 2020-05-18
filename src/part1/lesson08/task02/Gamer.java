package part1.lesson08.task02;

import java.io.Serializable;
import java.util.Objects;

/**
 * Gamer.
 * Item for Game.class.
 * @author Aydar_Safiullin
 */
public class Gamer implements Serializable {
    private static final long serialVersionUID = 111367111177799L;
    private String name;

    public Gamer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Игрок " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gamer gamer = (Gamer) o;
        return Objects.equals(name, gamer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
