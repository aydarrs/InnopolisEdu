package part1.lesson03.task02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ObjectBox.
 * Работа с коллекцией Object
 *
 * @author Aydar_Safiullin
 */
public class ObjectBox {
    List<Object> list = new ArrayList<>();

    /**
     * Добавление элемента.
     *
     * @param obj - добавляемый элемент.
     */
    public void addObject(Object obj) {
        list.add(obj);
    }

    /**
     * Удаление элемента.
     *
     * @param obj - удаляемый элемент.
     */
    public void deleteObject(Object obj) {
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o.equals(obj))
                iterator.remove();
        }
    }

    /**
     * Вывод в консоль строки, с перечислением элементов коллекции.
     */
    public void dump() {
        StringBuilder sB = new StringBuilder();
        for (Object o : list)
            sB.append(o.toString()).append(", ");
        sB.delete(sB.lastIndexOf(", "), sB.length());
        System.out.println(sB);
    }
}
