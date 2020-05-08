package part1.lesson05.task01;

import java.util.Objects;

/**
 * Person.
 * Класс, описывающий хозяина животного.
 * @author Aydar_Safiullin
 */
public class Person {
    private final String name;
    private int age;
    private final Sex sex;

    public Person(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "имя '" + name + '\'' +
                ", возраст " + age +
                ", пол " + sex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                sex == person.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex);
    }
}
