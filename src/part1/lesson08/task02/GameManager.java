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
    public void serialize(Object object, String file) {
        try(FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
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
        String file = "savedGameFromLesson08.bin";
        GameManager gameManager = new GameManager();
        Gamer[] gamers = {new Gamer("Erzhan"), new Gamer("Mongol"), new Gamer("Asdf")};
        Game startedGame = new Game();
        startedGame.saveGame(gamers);
        System.out.println("Сохраненая игра:\n" + startedGame);
        gameManager.serialize(startedGame, file);
        System.out.println("____________________________________________");
        Game loadedGame = (Game) gameManager.deSerialize(file);
        System.out.println("Загруженная игра:\n" + loadedGame);
        System.out.println("____________________________________________");
        System.out.println("Объекты равны:" + startedGame.equals(loadedGame));
    }
}
