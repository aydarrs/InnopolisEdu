package part1.lesson05.task01;

/**
 * Demonstration.
 * Демонстрация работы.
 * @author Aydar_Safiullin
 */
public class Demonstration {
    public static void main(String[] args) {
        CardIndex cardIndex = new CardIndex();
        Person hanna = new Person("Hanna", 55, Sex.FEMALE);
        Person oleg = new Person("Oleg", 33, Sex.MALE);
        Person lana = new Person("Lana", 18, Sex.FEMALE);
        Person jack = new Person("Jack", 40, Sex.MALE);
        Person sara = new Person("Sara", 25, Sex.FEMALE);

        System.out.println("Демонстрация работы метода addAnimal");
        cardIndex.addAnimal(new Animal(1, "Banksy", jack, 10.3));
        cardIndex.addAnimal(new Animal(2, "Banksy", jack, 7.7));
        cardIndex.addAnimal(new Animal(3, "Lora", jack, 4.2));
        cardIndex.addAnimal(new Animal(4, "Banksy", oleg, 5.7));
        cardIndex.addAnimal(new Animal(5, "Zhora", lana, 3.3));
        cardIndex.addAnimal(new Animal(6, "Honney", hanna, 34.0));
        cardIndex.addAnimal(new Animal(7, "Sam", sara, 11.0));
        cardIndex.addAnimal(new Animal(7, "Sam", sara, 11.0));
        cardIndex.printCartIndex();
        System.out.println("_______________________________________");

        System.out.println("Демонстрация работы метода searchAnimal");
        for (Animal animals : cardIndex.searchAnimal("Lora"))
            System.out.println(animals);
        System.out.println("_______________________________________");

        System.out.println("Демонстрация работы метода changeData");
        cardIndex.changeData(6, "Kira", null, 4.0);
        cardIndex.printCartIndex();
    }
}
