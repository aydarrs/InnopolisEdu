package part1.lesson02.task03;

import java.util.Date;

/**
 * Sort.
 * Интерфейс для выполнения сортировки массива
 * @author Aydar_Safiullin
 */
public interface UserSort {
    /**
     * Сортировка массива
     * @param persons - массив с объектами класса Person
     */
    public void userSort(Person[] persons);

    /**
     * Проверка массива на повторяющиеся данные
     * Вывод элементов массива и времени выполнения алгоритма сортировки
     * @param persons - массив с объектами класса Person
     * @throws RepeatPersonException - при повторении объектов класса Person в массиве
     */
    default void controlAndOutput(Person[] persons) throws RepeatPersonException {
        long begin = new Date().getTime();
        userSort(persons);
        long end = new Date().getTime();

        for (int i = 1; i < persons.length; i++) {
            if (persons[i].getName().equals(persons[i - 1].getName()) &&
            persons[i].getAge() == persons[i - 1].getAge())
                throw new RepeatPersonException();
        }

        for (Person p : persons)
            System.out.println(p);

        System.out.println("Время выполнения алгоритма сортировки - " + (end - begin) + " мс");
        System.out.println("______________________________________________");
    }
}
