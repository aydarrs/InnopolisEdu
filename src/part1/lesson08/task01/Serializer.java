package part1.lesson08.task01;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Serializer.
 *
 * @author Aydar_Safiullin
 */
public class Serializer {
    // Создаем кучу примитивных классов и String для проверки объекта на "плоскость"
    private static Class[] typesArray = {byte.class, short.class, int.class, long.class,
            double.class, float.class, double.class, char.class, boolean.class, String.class};
    private static Set<Class> types = new HashSet<>();
    static {
        types.addAll(Arrays.asList(typesArray));
    }

    /**
     * Checking is the object "flat".
     * @param object - checked object.
     * @throws NoFlatObjectException
     */
    private void toFlatObjectCheeking(Object object) throws NoFlatObjectException {
        Field[] field = object.getClass().getDeclaredFields();
        for (Field f: field)
            if (!types.contains(f.getType()))
                throw new NoFlatObjectException();
    }

    /**
     * Serialization.
     * @param object - serializable object.
     * @param file - file for writing.
     */
    public void serialize(Object object, String file) {
        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // перед записью проверяем, содержит ли наш объект непримитивные поля
            // (за исключением String полей)
            toFlatObjectCheeking(object);
            oos.writeObject(object);
        } catch (NoFlatObjectException e) {
            System.out.println("По условию задачи работаем только с \"плоскими\" объектами!");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            e.printStackTrace();
        }
    }

    /**
     * Deserialization.
     * @param file - read file.
     * @return  read object.
     */
    public Object deSerialize(String file) {
        Object result = null;
        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = ois.readObject();
            // после проверяем, содержит ли наш объект непримитивные поля
            // (за исключением String полей)
            toFlatObjectCheeking(result);
        } catch (NoFlatObjectException e) {
            System.out.println("По условию задачи работаем только с \"плоскими\" объектами!");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка приведения класса");
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String file = "FlatObjForLesson08.bin";
        Serializer serializer = new Serializer();
        ExperimentalObject original = new ExperimentalObject(11, "Эксперимент");
        System.out.println("Сериализуемый объект:\n" + original);
        serializer.serialize(original, file);
        System.out.println("____________________________________________");
        ExperimentalObject readObject = (ExperimentalObject) serializer.deSerialize(file);
        System.out.println("Десериализованный объект:\n" + readObject);
        System.out.println("____________________________________________");
        System.out.println("Объекты равны:" + original.equals(readObject));
    }
}
