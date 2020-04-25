package part1.lesson02.task03;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Person.
 * Класс, описывающий человека
 *
 * @author Aydar_Safiullin
 */
public class Person {
    private int age;
    private Sex sex;
    private String name;

    public Person(int age, Sex sex, String name) {
        this.age = age;
        this.sex = sex;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public Sex isSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    /**
     * Генератор случайного объекта класса Person
     *
     * @return random Person
     */
    public static Person randomGeneration() {
        int randomAge = ThreadLocalRandom.current().nextInt(0, 101);

        int randomIndex = ThreadLocalRandom.current().nextInt(0, Names.values().length);
        String randomName = Names.values()[randomIndex].getName();
        Sex randomSex = Names.values()[randomIndex].getSex();

        return new Person(randomAge, randomSex, randomName);
    }

    @Override
    public String toString() {
        return String.format("%s\t\t%d\t\t%s", sex, age, name);
    }
}
