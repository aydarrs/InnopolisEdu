package part1.lesson07.task01;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

/**
 * ArrayItemsFactorial.
 *
 * @author Aydar_Safiullin
 */
public class ArrayItemsFactorials {
    private Map<Integer, BigInteger> store = new ConcurrentSkipListMap<>();

    /**
     * Calculates the factorial for a given array.
     * @param numbers - given array.
     */
    private void getFactorials(int[] numbers) {
        long begin = new Date().getTime();

        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int x : numbers)
            service.execute(new FactorialCalculator(x));

        service.shutdown();

        try {
            if (service.awaitTermination(5, TimeUnit.SECONDS))
                service.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime();
        System.out.println("Время выполнения: " + (end - begin) + " мс");
    }

    /**
     * Class for multithreaded computing.
     */
    private class FactorialCalculator implements Runnable {
        private int number;

        private FactorialCalculator(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            BigInteger result = new BigInteger("1");
            // Условно считаю, что в массиве нет повторяющихся чисел, поэтому не делаю проверку на повторение
            // Если в массиве есть число меньше нашего, то используем факториал этого числа для вычислений
            if (!store.isEmpty()) {
                for (int i = number; i > 0; i--) {
                    if (!store.containsKey(i))
                        result = result.multiply(new BigInteger(String.valueOf(i)));
                    else {
                        result = result.multiply(store.get(i));
                        break;
                    }
                }
                store.put(number, result);
                return;
            }

            for (int i = 1; i <= number; i++)
                result = result.multiply(new BigInteger(String.valueOf(i)));
            store.put(number, result);
        }
    }

    public static void main(String[] args) {
        System.out.println("Вычисление факториала для массива 44000 последовательных чисел");
        ArrayItemsFactorials firstEx = new ArrayItemsFactorials();
        int[] firstArr = new int[40000];
        for (int i = 0; i < 40000; i++)
            firstArr[i] = i + 1;
        firstEx.getFactorials(firstArr);
        System.out.println("Факториал вычислен для всех чисел: " + (firstEx.store.size() == firstArr.length));
        System.out.println("\n___________________________________");

        System.out.println("Вычисление факториала для заданного массива");
        ArrayItemsFactorials secondEx = new ArrayItemsFactorials();
        int[] secondArr = {34, 1, 16, 7, 20, 14, 10, 2, 30, 15000, 123};
        secondEx.getFactorials(secondArr);
        for (Integer key : secondEx.store.keySet())
            System.out.println(String.format("Факториал чила %d равен %s", key, secondEx.store.get(key)));
        System.out.println();
    }
}
