package part1.lesson03.task02;

/**
 * Demonstrator.
 * Демонстрация работы класса ObjectBox.
 *
 * @author Aydar_Safiullin
 */
public class Demonstrator {
    public static void main(String[] args) {
        ObjectBox o = new ObjectBox();
        o.addObject("1");
        o.addObject("asd");
        o.addObject("555");
        o.addObject("s");
        o.deleteObject("555");
        o.deleteObject("aa");
        o.dump();
    }
}
