package part1.lesson12.task02;

/**
 * MetaspaceOut.
 *  * Пример 1: -XX:+UseSerialGC -Xmx100m
 *  * Пример 2: -XX:+UseParallelGC -Xmx100m
 *  * Пример 3: -XX:+UseConcMarkSweepGC -Xmx100m
 *  * Пример 4: -XX:+UseG1GC -Xmx100m
 * @author Aydar_Safiullin
 */
public class MetaspaceOut {
    public static void main(String[] args) throws InterruptedException {
        StringBuilder sB = new StringBuilder();
        Thread.sleep(1_000);
        while (true) {
            sB.append("word ");
        }
    }
}
