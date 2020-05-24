package part1.lesson12.task01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * HeapOut.
 *  * Пример 1: -XX:+UseSerialGC -Xmx100m
 *  * Пример 2: -XX:+UseParallelGC -Xmx100m
 *  * Пример 3: -XX:+UseConcMarkSweepGC -Xmx100m
 *  * Пример 4: -XX:+UseG1GC -Xmx100m
 * @author Aydar_Safiullin
 */
public class HeapOut {
    public static void main(String[] args) throws InterruptedException {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        List<String> list5 = new ArrayList<>();
        List<Integer> list6 = new ArrayList<>();
        List<Garbage> list7 = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1_000_000_000; i++) {
            String str1 = "" + random.nextInt();
            list1.add(str1);
            String str2 = "" + random.nextInt();
            list2.add(str2);
            String str3 = "" + random.nextInt();
            list3.add(str3);
            String str4 = "" + random.nextInt();
            list4.add(str4);
            String str5 = "" + random.nextInt();
            list5.add(str5);
            list6.add(Integer.valueOf(i));
            list7.add(new Garbage(random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    str1));

            if (i % 10 == 0) {
                Thread.sleep(1);
                list1.remove(0);
                list2.remove(0);
                list3.remove(0);
            }
        }

        System.out.println(list1.size());
    }
}
