package part1.lesson06.task01;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solution.
 * Work with files.
 * Searching and writing unique words.
 * @author Aydar_Safiullin
 */
public class Solution {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\part1\\lesson06\\task01\\ReadFile.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("src\\part1\\lesson06\\task01\\WriteFile.txt")))
        {
            // Set for storage and sorting of unique words
            Set<String> words = new TreeSet<>(((o1, o2) -> o1.compareToIgnoreCase(o2)));

            StringBuilder sB = new StringBuilder();
            while (reader.ready())
                sB.append(reader.readLine() + " ");
            String readData = sB.toString();

            // searching words and add them to Set
            Pattern pattern = Pattern.compile("\\b[-\\wА-Яа-я]+\\b");
            Matcher matcher = pattern.matcher(readData);
            while (matcher.find()) {
                words.add(readData.substring(matcher.start(), matcher.end()));
            }

            for (String s : words)
                writer.write(s + "\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
