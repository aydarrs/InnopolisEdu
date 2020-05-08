package part1.lesson05.task01;

import java.util.*;

/**
 * CardIndex.
 * Картотека домашних животных.
 * @author Aydar_Safiullin
 */
public class CardIndex {
    private Map<Integer, Animal> map = new TreeMap<>();

    /**
     * Добавление объекта класса Animal в картотеку.
     * Дубликат проверяется через ID, т.к. у каждого животного
     * он уникален, а другие данные животного могут изменяться.
     * @param animal - добавляемый объект класса Animal.
     */
    public void addAnimal(Animal animal) {
        try {
            if (!map.containsKey(animal.getID()))
                map.put(animal.getID(), animal);
            else throw new ExistingDataException();
        } catch (ExistingDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск животных.
     * @param name - имя для поиска.
     * @return список животных с указанным именем.
     */
    public List<Animal> searchAnimal(String name) {
        List<Animal> list = new ArrayList<>();
        for (Map.Entry<Integer, Animal> entry : map.entrySet()) {
            if (name.equals(entry.getValue().getName()))
                list.add(entry.getValue());
        }
        return list;
    }

    /**
     * Изменение данных.
     * @param ID животного, данные которого нужно изменить.
     * @param name новое имя.
     * @param person новый хоязин.
     * @param weight новый вес.
     */
    public void changeData(int ID, String name, Person person, double weight) {
        for (Map.Entry<Integer, Animal> entry : map.entrySet()) {
            if (ID == entry.getKey()) {
                if (name != null)
                    entry.getValue().setName(name);

                if (person != null)
                    entry.getValue().setPerson(person);

                if (weight != 0.0)
                    entry.getValue().setWeight(weight);
            }
        }
    }

    /**
     * Сортировка и вывод картотеки на экран.
     */
    public void printCartIndex() {
        Set<Animal> set = new TreeSet<>(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                if (!o1.getPerson().equals(o2.getPerson()))
                    return o1.getPerson().getName().compareTo(o2.getPerson().getName());

                if (!o1.getName().equals(o2.getName()))
                    return o1.getName().compareTo(o2.getName());

                return (o1.getWeight() - o2.getWeight()) < 0 ? -1 : (int) (o1.getWeight() - o2.getWeight());

            }
        });

        for (Map.Entry<Integer, Animal> entry : map.entrySet()) {
            set.add(entry.getValue());
        }

        for (Animal animal : set)
            System.out.println(animal);
    }
}
