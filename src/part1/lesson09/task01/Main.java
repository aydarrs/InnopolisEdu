package part1.lesson09.task01;

import org.joor.Reflect;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



/**
 * Main.
 * Solution of task.
 * @author Aydar_Safiullin
 */
public class Main {
    public static void main(String[] args) {
        // Наш файл .java
        String javaFile = "SomeClass.java";
        // Наш новый код
        String javaCode = addCode(javaFile);
        // Имя класса который скомпилируется наш код
        String classFile = "part1.lesson09.task01.SomeClass";
        // Работа с подключенной библиотекой
        Worker worker = Reflect.compile(classFile, javaCode).create().get();
        System.out.println("Резултат работы метода:");
        worker.doWork();
        System.out.println("_____________________________");
        System.out.println("Использованный ClassLoader:");
        System.out.println(worker.getClass().getClassLoader());
    }

    // Изначально планировал использовать собственный ClassLoader,
    // но в готовой библиотеке был свой.
    @Deprecated
    private static void useCustomClassLoader() {
        ClassLoader cL = new MyClassLoader();

        try {
            Class<?> editClass = cL.loadClass("part1.lesson09.task01.SomeClass");
            editClass.getMethod("doWork").invoke(editClass.newInstance());
//            SomeClass s = (SomeClass) editClass.getDeclaredConstructor().newInstance();
//            s.doWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit code.
     * @param file - a file containing the code to edit
     * @return - edited code
     */
    private static String addCode(String file) {
        String result = "";
        try {
            Path filePath = Paths.get(file);
            String addedCode = consoleRead();
            String originalCode = Files.readString(filePath);
            result = originalCode;
            StringBuilder sB = new StringBuilder();
            sB.append(originalCode.substring(0, originalCode.length() - 4))
                    .append(addedCode)
                    .append(" }\n}");
            result = sB.toString();
        } catch (IOException e ) {
            e.printStackTrace();
        } finally {
          return result;
        }
    }

    /**
     * Write new code.
     * @return - new code.
     * @throws IOException
     */
    private static String consoleRead() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sB = new StringBuilder();
        String codeLine = reader.readLine();
        while (!codeLine.equals("")) {
            sB.append(codeLine);
            codeLine = reader.readLine();
        }
        return sB.toString();
    }
}
