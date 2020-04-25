package part1.lesson02.task03;

/**
 * RepeatPersonException.
 * Исключение при повторении объектов класса Person
 *
 * @author Aydar_Safiullin
 */
public class RepeatPersonException extends Exception {
    @Override
    public String getMessage() {
        return "Ошибка! Повторяющиеся данные в масиве";
    }
}
