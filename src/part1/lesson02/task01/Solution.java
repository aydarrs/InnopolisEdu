package part1.lesson02.task01;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Solution.
 * Программа выводит в консоль "Hello, World!".
 * В ходе выполнения бросается одно из трех исключений.
 *
 * @author Aydar_Safiullin
 */
public class Solution {
    public static void main(String[] args) {
        String s = null;
        int[] array = {0, 2, 6};
        int var = ThreadLocalRandom.current().nextInt(1, 4);

        System.out.println("Hello, World!");

        switch (var) {
            case 1:
                System.out.println(s.equals("Another string"));
                break;
            case 2:
                System.out.println(array[4]);
                break;
            case 3:
                throw new ArithmeticException();
        }
    }
}
