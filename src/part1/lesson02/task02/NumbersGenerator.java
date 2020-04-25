package part1.lesson02.task02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

/**
 * NumbersGenerator.
 * Программа генерирует N случайных чисел.
 * Количество N задается пользователем путём ввода в консоль.
 * В случае если из числа возможно извлечь целый квадратный корень,
 * последний выводится в консоль.
 * В случае попытки извлечь корень из отрицательного числа,
 * в консоль выводится уведомление об этом.
 *
 * @author Aydar_Safiullin
 */
public class NumbersGenerator {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int n = parsing(reader);
            while (n > 0) {
                try {
                    int k = ThreadLocalRandom.current().nextInt(-100, 100);
                    if (k < 0)
                        throw new NegativeNumberSquareRootException();
                    int q = (int) Math.sqrt(k);
                    if (k == q * q)
                        // не совсем понял, что здесь именно выводить - сгенерированное число или корень
                        System.out.println(q);
                } catch (NegativeNumberSquareRootException e) {
                    System.out.println(e.getMessage());
                }
                n--;
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода данных");
        }
    }

    /**
     * @param reader - буфферный поток ввода символов, для чтения с клавиатуры
     * @return количество чисел, для генерации
     */
    private static int parsing(BufferedReader reader) throws IOException {
        System.out.println("Введите количество чисел, которые необходимо сгенерировать");
        int result = 0;
        boolean isParsing = false;
        while (!isParsing) {
            try {
                result = Integer.parseInt(reader.readLine());
                isParsing = true;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, попробуйте ещё раз");
            }
        }
        return result;
    }
}
