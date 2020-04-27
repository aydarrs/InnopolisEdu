package part1.lesson03.task01;

/**
 * Demonstrator.
 * Демонстрация работы класса MathBox.
 *
 * @author Aydar_Safiullin
 */
public class Demonstrator {
    public static void main(String[] args) {
        Number[] arr = {new Integer(4), new Double(99.5), new Byte("48"), new Short("24"),
                new Long(6666), new Float(11)};
        MathBox box = new MathBox(arr);
        System.out.println("Коллекция класса MathBox: " + box);

        System.out.println("Результат работы метода summator " + box.summator());

        box.removeInt(11);
        box.removeInt(99);
        System.out.println("Результат работы метода removeInt " + box);

        box.splitter(2);
        System.out.println("Результат работы метода splitter " + box);
    }
}
