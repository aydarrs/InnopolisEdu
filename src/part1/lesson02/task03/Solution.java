package part1.lesson02.task03;

/**
 * Solution.
 * Программа генерирует данные заданного числа людей.
 * Люди сортируются в следующем порядке:
 * - по полу (сперва мужчины);
 * - по возрасту (сперва старшие);
 * - по имени в алфавитном порядке.
 * Сортировка проводится дважды, разными алгоритмами.
 * Каждым алгоритмом в консоль выводится отсортированный список людей
 * и время выполнения.
 * В случае повтора данных, бросается исключение и программа завершается.
 * Список возможных имен приведен в enum-классе Names.
 *
 * @author Aydar_Safiullin
 */
public class Solution {
    public static void main(String[] args) {
        //
        Person[] persons = new Person[10000];
        for (int i = 0; i < persons.length; i++)
            persons[i] = Person.randomGeneration();
        UserSort comparatorSort = new ComparatorSort();
        UserSort bubbleSort = new BubbleSort();

        try {
            comparatorSort.controlAndOutput(persons);
            bubbleSort.controlAndOutput(persons);
        } catch (RepeatPersonException e) {
            e.printStackTrace();
        }

    }
}
