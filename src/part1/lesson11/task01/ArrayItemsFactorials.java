package part1.lesson11.task01;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ArrayItemsFactorial.
 *
 * @author Aydar_Safiullin
 */
public class ArrayItemsFactorials {
    /**
     * Calculates the factorial for a given array.
     * @param numbers - given array.
     */
    private void getFactorials(Integer[] numbers) {
        long begin = new Date().getTime();
        List<Integer> nums = Arrays.asList(numbers);

        nums.parallelStream()
                .distinct()
                .map(x -> {
                            BigInteger result = new BigInteger("1");
                            for (int i = 1; i <= x; i++)
                                result = result.multiply(new BigInteger(String.valueOf(i)));
                            return String.format("Факториал %d = %s", x, result);
                        })
                .collect(Collectors.toList())
                .stream()
                .forEach(System.out::println);

        long end = new Date().getTime();
        System.out.println("Время выполнения: " + (end - begin) + " мс");
    }


    public static void main(String[] args) {
        System.out.println("Вычисление факториала для заданного массива");
        ArrayItemsFactorials secondEx = new ArrayItemsFactorials();
        Integer[] secondArr = {34, 1, 30000, 16, 7, 20, 14, 10, 2, 30, 15000, 123, 15000, 16};
        secondEx.getFactorials(secondArr);
    }
}
