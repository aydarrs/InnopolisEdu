package part1.lesson08.task02;

import java.io.*;

/**
 * GameManager.
 * Solution of task02.
 * @author Aydar_Safiullin
 */
public class GameManager {
    /**
     * Serialization.
     * @param object - serializable object.
     * @param file - file for writing.
     */
    public static void serialize(Object object, String file) {
        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (IOException e) {
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String file = "savedGameFromLesson08.bin";
        Gamer[] gamers = {new Gamer("Erzhan"), new Gamer("Mongol"), new Gamer("Asdf")};
        Game startedGame = new Game();
        startedGame.saveGame(gamers);
        System.out.println("Сохраненая игра:\n" + startedGame);
        serialize(startedGame, file);
        System.out.println("____________________________________________");
        Game loadedGame = (Game) deSerialize(file);
        System.out.println("Загруженная игра:\n" + loadedGame);
        System.out.println("____________________________________________");
        System.out.println("Объекты равны:" + startedGame.equals(loadedGame));
    }
}
