package part1.lesson05.task01;

import java.util.Objects;

/**
 * Animal.
 * Класс, описывающий животное.
 * @author Aydar_Safiullin
 */
public class Animal {
    private final int ID;
    private String name;
    private Person person;
    private double weight;

    public Animal(int id, String name, Person person, double weight) {
        this.ID = id;
        this.name = name;
        this.person = person;
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Домашнее животное{" +
                "ID=" + ID +
                ", кличка '" + name + '\'' +
                ", хозяин " + person +
                ", вес " + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return ID == animal.ID &&
                Double.compare(animal.weight, weight) == 0 &&
                Objects.equals(name, animal.name) &&
                Objects.equals(person, animal.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
