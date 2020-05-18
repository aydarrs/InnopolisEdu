package part1.lesson09.anotherVariantOfTask01;

import javax.tools.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


/**
 * Main.
 * Solution of task.
 *
 * @author Aydar_Safiullin
 */
public class Main {
    public static void main(String[] args) throws IOException {
        SomeClass someClass = new SomeClass();
        System.out.println("Результат работы старого кода:");

        someClass.doWork();

        // Наш файл .java
        String javaFile = "SomeClass.java";
        // Изменяем в нем код
        addCode(javaFile);

        // Компилируем
        System.out.println("____________________________");
        System.out.println("Результат работы нового кода");
        compileRuntime(javaFile);
    }

    /**
     * Compile file in runtime.
     * @param javaFile - compiling file.
     */
    private static void compileRuntime(String javaFile) {
        Path javaSource = Paths.get(javaFile);
        File[] files = {javaSource.toFile()};
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                null,
                null,
                null,
                null,
                compilationUnits
        );
        task.call();
        useCustomClassLoader();
    }


    private static void useCustomClassLoader() {
        ClassLoader cL = new MyClassLoader();

        try {
            Class<?> editClass = cL.loadClass(SomeClass.class.getName());
            editClass.getMethod("doWork").invoke(editClass.newInstance());
//            SomeClass s = (SomeClass) editClass.getDeclaredConstructor().newInstance();
//            s.doWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit code.
     *
     * @param file - a file containing the code to edit
     * @return - edited code
     */
    private static void addCode(String file) {
        String result = "";
        try {
            Path filePath = Paths.get(file);
            String addedCode = consoleRead();
            String originalCode = Files.readString(filePath);
            StringBuilder sB = new StringBuilder();
            sB.append(originalCode.substring(0, originalCode.length() - 4))
                    .append(addedCode)
                    .append(" }\n}");
            result = sB.toString();
            Files.writeString(filePath, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write new code.
     *
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
