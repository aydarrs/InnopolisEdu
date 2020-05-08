package part1.lesson06.task02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Sentence.
 * Описывает предложение.
 * @author Aydar_Safiullin
 */
public class Sentence {
    // Список слов, применяемых в предложении.
    private List<String> words = new ArrayList<>();
    private String represent;

    public Sentence(String userWords[], int probability) {
        randomWordsGenerate(userWords, probability);
        buildSentence();
    }

    /**
     * Запись в список слов, для генерации предложения.
     * @param userWords - готовый массив слов.
     * Из метода getFiles класса TextGenerator.
     * @param probability - вероятность использования слова из массива userWords.
     * Из метода getFiles класса TextGenerator.
     */
    private void randomWordsGenerate(String userWords[], int probability) {
        // Определяем, сколько слов будет в предложении
        int length = ThreadLocalRandom.current().nextInt(1, 16);
        //Добавляем в список случайные слова
        for (int i = 0; i < length; i++)
            words.add(new Word().toString());

        // Проверяем, сработала ли вероятность
        int randomProbability = ThreadLocalRandom.current().nextInt(1, probability + 1);
        if (probability == randomProbability) {
            // смотрим, в какое именно место в предложении вставим слово из готового массива
            int randomInWords = ThreadLocalRandom.current().nextInt(0, length);
            // определяем, какое именно слово будем вставлять
            int randomInUserWords = ThreadLocalRandom.current().nextInt(0, userWords.length);
            words.set(randomInWords, userWords[randomInUserWords]);
        }
        // Устанавливаем у первого слова заглавную букву в верхний регистр
        words.set(0, setTitle(words.get(0)));
    }

    /**
     * Создание предложения.
     */
    private void buildSentence() {
        StringBuilder sB = new StringBuilder();
        char mark = markAtTheEndGenerate();
        for (String s : words)
            sB.append(s).append(" ");
        sB.delete(sB.length() - 1, sB.length());
        sB.append(mark).append(" ");
        represent = sB.toString();
    }

    /**
     * Установка первой буквы в верхний регистр.
     */
    private String setTitle(String word) {
        String title = word.substring(0, 1).toUpperCase();
        StringBuilder sB = new StringBuilder(title);
        if (word.length() > 1)
            sB.append(word.substring(1, word.length()));
        return sB.toString();
    }

    /**
     * Генерация случайного знака в конце предложения.
     */
    private char markAtTheEndGenerate() {
        char mark;
        int randomMark = ThreadLocalRandom.current().nextInt(1,4);
        switch (randomMark) {
            case 1:
                mark = '.';
                break;
            case 2:
                mark = '!';
                break;
            default:
                mark = '?';
        }
        return mark;
    }

    /**
     * Создание предложения определенного размера.
     * Используется для дополнения текста до заданного размера.
     * @param size - размер добавляемого предложения.
     */
    public void completeText(int size) {
        // определяем, сколько может быть в предложении слов с максимальным размером (15 + 1 пробел)
        int whole = size / 16;
        // определяем, какой длины еще необходимо слово, чтобы получить нужный размер предложения, без учета знака в конце
        int balance = size % 16 - 1;
        // собираем предложение, добавляем знак в конец.
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < whole; i++)
            sB.append(new Word(15)).append(" ");
        sB.append(new Word(balance));
        char mark = markAtTheEndGenerate();
        sB.append(mark);
        represent = setTitle(sB.toString());
    }

    /**
     * @return размер предложения.
     */
    public int getSize() {
        return represent.length();
    }

    @Override
    public String toString() {
        return represent;
    }
}
