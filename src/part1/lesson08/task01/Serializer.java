package part1.lesson08.task01;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Serializer.
 * Solution of task01.
 * @author Aydar_Safiullin
 */
public class Serializer {
    // Создаем кучу примитивных классов и String для проверки объекта на "плоскость"
    private static final Class<?>[] typesArray = {byte.class, short.class, int.class, long.class,
            double.class, float.class, double.class, char.class, boolean.class, String.class};
    private static final Set<Class<?>> types = new HashSet<>();
    static {
        types.addAll(Arrays.asList(typesArray));
    }

    /**
     * Serialization.
     * @param object - serializable object.
     * @param file - file for writing.
     */
    public static void serialize(Object object, String file) {
        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // перед записью проверяем, содержит ли наш объект непримитивные поля
            // (за исключением String полей)
            toFlatObjectCheeking(object);
            oos.writeObject(object);
        } catch (NoFlatObjectException |IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialization.
     * @param file - read file.
     * @return  read object.
     */
    public static Object deSerialize(String file) {
        Object result = null;
        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = ois.readObject();
            // после проверяем, содержит ли наш объект непримитивные поля
            // (за исключением String полей)
            toFlatObjectCheeking(result);
        } catch (IOException | ClassNotFoundException | NoFlatObjectException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checking is the object "flat".
     * @param object - checked object.
     * @throws NoFlatObjectException
     */
    private static void toFlatObjectCheeking(Object object) throws NoFlatObjectException {
        Field[] field = object.getClass().getDeclaredFields();
        for (Field f: field)
            if (!types.contains(f.getType()))
                throw new NoFlatObjectException();
    }

    public static void main(String[] args) {
        String file = "FlatObjForLesson08.bin";
        ExperimentalObject original = new ExperimentalObject(11, "Эксперимент");
        System.out.println("Сериализуемый объект:\n" + original);
        serialize(original, file);
        System.out.println("____________________________________________");
        ExperimentalObject readObject = (ExperimentalObject) deSerialize(file);
        System.out.println("Десериализованный объект:\n" + readObject);
        System.out.println("____________________________________________");
        System.out.println("Объекты равны:" + original.equals(readObject));
    }
}
