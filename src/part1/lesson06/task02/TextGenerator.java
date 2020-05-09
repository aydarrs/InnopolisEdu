package part1.lesson06.task02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TextGenerator.
 * Генератор случайного текста.
 * @author Aydar_Safiullin
 */
public class TextGenerator {
    // список предложений
    private List<String> sentences = new ArrayList<>();
    // список с указанием номеров предложений, после которых начинается новый абзац
    List<Integer> paragraphs = new ArrayList<>();

    /**
     * Генерирует и записывает текст в файлы.
     * @param path - путь к файлу для записи.
     * @param n - кол-во копий файла.
     * @param size - размер файла.
     * @param words - готовый массив слов.
     * @param probability - вероятность попадания слова из готового массива в предложение
     */
    private void getFiles(String path, int n, int size, String[] words, int probability) {
        System.out.println(String.format("Создание %d файлов(-а) по %d байт.", n, size));
        int currentSize = 0;
        StringBuilder sB = new StringBuilder();
        // Генерируем случайные предложения.
        // Чтобы не выйти за заданный размер файла, подстраховываемся, уменьшив size на
        // максимально возможный размер предложения с учётом пробелов.
        while (currentSize < size - 240) {
            Sentence sentence = new Sentence(words, probability);
            sentences.add(sentence.toString());
            currentSize += sentence.getSize();
        }

        // Добавляем предложение, чтобы дополнить размер нашего текста до size
        if (currentSize != size) {
            Sentence sentence = new Sentence(size - currentSize);
            sentences.add(sentence.toString());
        }

        // Определяем, где будем разбивать текст на абзацы
        paragraphSeparate();

        // Делим текст на абзацы
        for (int i = 0; i < sentences.size(); i++) {
            sB.append(sentences.get(i));
            if (!paragraphs.isEmpty() && i == paragraphs.get(0) + 1) {
                sB.replace(sB.length() - 1, sB.length(), "\n");
                paragraphs.remove(0);
            }
        }

        // Доделываем наш текст
        // Если он заканчивается на " ", то заменяем концовку(знак в конце и этот пробел)
        String text = sB.toString();
        if (text.endsWith(" ")) {
            sB.replace(sB.length() - 2, sB.length(), "z.");
            text = sB.toString(); // Здесь вопрос - при изменении StringBuilder, из которого собирается text в строке 46,
                                  // изменится ли сам text?
        }

        // Создаём файлы
        File file = new File(path);
        try (FileInputStream fin = new FileInputStream(file)) // fis - для вывода инфы о размере созданного файла
        {
            Files.writeString(file.toPath(), text);
            for (int i = 1; i < n; i++)
                Files.copy(file.toPath(), Paths.get(path.replace(".txt", i + ".txt")), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл(ы) созданы.");
            System.out.println("Размер созданных файлов " + fin.available() + " байт.");
            // Размер можно было вывести и без FileInputStream, указав просто длину строки.
            // Но я решил, что так будет правильнее.

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Определяет, после каких предложений будет новый абзац.
     */
    private void paragraphSeparate() {
        // Текущий номер предложения
        int current = 0;
        for (int x : paragraphs)
            current += x;
        // Сколько предложений будет в абзаце в абзаце
        int result = ThreadLocalRandom.current().nextInt(1,20);
        // Если в абзаце должно быть меньше предложений, чем осталось не распределенных по абзацам,
        // добавляем место разбиения в список и рекурсивно продолжаем работу метода,
        // пока не определим все места разбиения на абзацы
        if (result < sentences.size() - current) {
            paragraphs.add(result + current);
            paragraphSeparate();
        }
    }


    public static void main(String[] args) {
        TextGenerator generator = new TextGenerator();

        // генерируем массив готовых слов, которые должны попадать в предложение с заданной вероятностью
        String[] probableWords = new String[1000];
        for (int i = 0; i < probableWords.length; i++)
            probableWords[i] = new Word().toString();

        generator.getFiles("src\\part1\\lesson06\\task02\\WriteFile.txt", 3, 5906, probableWords, 4);
    }

}
