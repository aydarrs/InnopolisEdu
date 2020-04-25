package part1.lesson02.task02;

/**
 * NegativeNumberSquareRootException.
 * Исключение при расчете квадратного корня из отрицательного числа
 *
 * @author Aydar_Safiullin
 */
public class NegativeNumberSquareRootException extends Exception {
    @Override
    public String getMessage() {
        return "Было сгенерированно отрицательное число";
    }
}
