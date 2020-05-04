package part1.lesson04;

/**
 * ExistingDataException.
 * Исключение при дублированнии объекта Animal.
 * @author Aydar_Safiullin
 */
public class ExistingDataException extends Exception {
    @Override
    public String getMessage() {
        return "Данное животное уже было добавлено";
    }
}
