package part1.lesson03.task01;

import java.util.*;

/**
 * MathBox.
 * Работа с коллекцией Number.
 * Выполнение арифметических операций с коллекцией,
 * независимо от класса элементов.
 *
 * @author Aydar_Safiullin
 */
public class MathBox {
    private List<Number> list = new ArrayList<>();

    public MathBox(Number[] arr) {
        list.addAll(Arrays.asList(arr));
    }

    /**
     * @return сумма элементов коллекции list.
     */
    public double summator() {
        double result = 0;
        for (Number item : list)
            result += item.doubleValue();
        return result;
    }

    /**
     * Деление элементов коллекции list на число, передающееся
     * в параметре метода.
     * Замена элементов коллекции list на полученные значения
     *
     * @param x - делитель
     */
    public void splitter(double x) {
        for (Number item : list) {
            double result = item.doubleValue() / x;
            list.set(list.indexOf(item), result);
        }
    }

    /**
     * Удаление из коллекции числа, соответствующего
     * передаваемому в параметре метода
     *
     * @param x - удаляемый элемент класса Integer.
     */
    public void removeInt(Integer x) {
        int intValue = x;
        double value = new Double(x);
        Iterator<Number> iterator = list.iterator();
        while (iterator.hasNext()) {
            Number o = iterator.next();
            if (o.doubleValue() / value == 1)
                iterator.remove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox box = (MathBox) o;
        return Objects.equals(list, box.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        for (Number item : list)
            sB.append(item.toString()).append(", ");
        sB.delete(sB.lastIndexOf(", "), sB.length());
        return sB.toString();
    }
}
