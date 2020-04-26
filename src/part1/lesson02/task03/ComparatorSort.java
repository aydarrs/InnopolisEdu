package part1.lesson02.task03;

import java.util.*;

/**
 * ComparatorSort.
 * Сортировка с помощью класса Comparator
 *
 * @author Aydar_Safiullin
 */
public class ComparatorSort implements UserSort {
    public void userSort(Person[] persons) {
        System.out.println("Сортировка с помощью класса Comparator");
        List<Person> list = Arrays.asList(persons);
        // Сортировка по полу
        Collections.sort(list, ((o1, o2) -> o1.isSex().compareTo(o2.isSex())));
        // Сортировка по возрасту
        // (Условие задачи - "выше в списке тот, кто старше" понял как первые в массиве те, кто старше)
        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.isSex().equals(o2.isSex()))
                    return o2.getAge() - o1.getAge();
                return 0;
            }
        });
        // Сортировка по имени
        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getAge() == o2.getAge())
                    return o1.getName().compareTo(o2.getName());
                return 0;
            }
        });

        list.toArray(persons);
    }
}
