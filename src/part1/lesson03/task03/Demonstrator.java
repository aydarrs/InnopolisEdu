package part1.lesson03.task03;

/**
 * Demonstrator.
 * Демонстрация работы классов MathBox и ObjectBox
 * @author Aydar_Safiullin
 */
public class Demonstrator {
    public static void main(String[] args) {
        Integer[] arr = {7, 8, 345, 66, 10};
        MathBox box = new MathBox(arr);
        System.out.println("Коллекция класса MathBox: " + box);

        System.out.println("Результат работы метода summator " + box.summator());

        box.deleteObject(11);
        box.deleteObject(99);
        System.out.println("Результат работы метода removeInt (удаление значений 11 и 99) " + box);

        box.addObject(16);
        System.out.println("Результат работы метода addObject (добавление значения 16) " + box);

        box.splitter(2);
        System.out.println("Результат работы метода splitter (деление на 2) " + box);
    }
}
