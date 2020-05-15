package part1.lesson06.task02;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Word.
 * Описывает слово.
 * @author Aydar_Safiullin
 */
public class Word {
    private String represent;

    public Word() {
        wordGenerate();
    }

    public Word(int size) { wordGenerate(size);}

    /**
     * Генерация произвольного слова.
     */
    private void wordGenerate() {
        int length = ThreadLocalRandom.current().nextInt(1, 16);
        wordGenerate(length);
    }

    /**
     * Генерация слова заданного размера.
     * @param size - заданный размер.
     */
    private void wordGenerate(int size) {
        StringBuilder sB = new StringBuilder();

        for (int i = 0; i < size; i++)
            sB.append(letterGenerate());

        represent = sB.toString();
    }

    /**
     * @return произвольная буква от 'a' до 'z'.
     */
    private char letterGenerate() {
        return (char) ThreadLocalRandom.current().nextInt(97, 123);
    }

    @Override
    public String toString() {
        return represent;
    }
}
