package part1.lesson03.task03;

import java.util.*;

/**
 * ObjectBox.
 * Работа с коллекцией Object
 *
 * @author Aydar_Safiullin
 */
public class ObjectBox<T extends Number> {
    private List<T> list = new ArrayList<>();

    public ObjectBox(T[] arr) {
        list.addAll(Arrays.asList(arr));
    }

    /**
     * Добавление элемента.
     *
     * @param obj - добавляемый элемент.
     */
    public void addObject(T obj) {
        list.add(obj);
    }

    /**
     * Удаление элемента.
     *
     * @param obj - удаляемый элемент.
     */
    public void deleteObject(T obj) {
        double value = obj.doubleValue();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T o = iterator.next();
            if (o.doubleValue() / value == 1)
                iterator.remove();
        }
    }

    /**
     * @return сумма элементов коллекции list.
     */
    public T summator() {
        Double result = 0.0;
        for (T item : list)
            result += item.doubleValue();
        return (T) result;
    }

    /**
     * Деление элементов коллекции list на число, передающееся
     * в параметре метода.
     * Замена элементов коллекции list на полученные значения
     *
     * @param x - делитель
     */
    public void splitter(T x) {
        for (T item : list) {
            Double result = item.doubleValue() / x.doubleValue();
            list.set(list.indexOf(item), (T) result);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBox<?> objectBox = (ObjectBox<?>) o;
        return Objects.equals(list, objectBox.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        for (T item : list)
            sB.append(item.toString()).append(", ");
        sB.delete(sB.lastIndexOf(", "), sB.length());
        return sB.toString();
    }
}
