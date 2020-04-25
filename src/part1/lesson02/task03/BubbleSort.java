package part1.lesson02.task03;

/**
 * BubbleSort.
 * Класс для пузырьковой сортировки
 *
 * @author Aydar_Safiullin
 */
public class BubbleSort implements UserSort {
    public void userSort(Person[] persons) {
        System.out.println("Пузырьковая сортировка");
        // Сортировка по имени
        for (int i = 0; i < persons.length; i++) {
            for (int j = 1; j < persons.length; j++) {
                if (persons[j].getName().compareTo(persons[j - 1].getName()) < 0) {
                    replace(persons, j);
                }
            }
        }

        // Сортировка по возрасту
        // (Условие задачи - "выше в списке тот, кто старше" понял как первые в массиве те, кто старше)
        for (int i = 0; i < persons.length; i++) {
            for (int j = 1; j < persons.length; j++) {
                if ((persons[j].getAge() - (persons[j - 1].getAge()) > 0)) {
                    replace(persons, j);
                }
            }
        }

        // Сортировка по полу
        for (int i = 0; i < persons.length; i++) {
            for (int j = 1; j < persons.length; j++) {
                if ((persons[j].isSex().equals(Sex.MAN) && persons[j - 1].isSex().equals(Sex.WOMAN))) {
                    replace(persons, j);
                }
            }
        }


    }

    /**
     * Перестановка двух соседних элементов в массиве
     *
     * @param persons - массив, в котором необходимо осуществить перестановку
     * @param j       - элемент, который необходимо поменять местами с левым соседним элементом
     */
    private void replace(Person[] persons, int j) {
        Person tmp = persons[j];
        persons[j] = persons[j - 1];
        persons[j - 1] = tmp;
    }
}
