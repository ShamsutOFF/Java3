package com.geekbrains.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class NIOTest {
    public static void main(String[] args) {

        Path testFilePath = Paths.get ("client/src/main/java/com/geekbrains/client/history/test.txt");
        try {
//            for (int i = 1; i <= 50; i++) {
//                String txt = i + " строка\n";
//                byte[] bs = txt.getBytes ( );
//                Files.write (testFilePath, bs, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//            }

//            Files.write (testFilePath, bsN, StandardOpenOption.APPEND);

//            Path writtenFilePath = Files.write(testFilePath, bs, StandardOpenOption.APPEND);
            List<String> listLines = Files.readAllLines (testFilePath);
//            listLines.spliterator ();
            System.out.println (listLines);
            System.out.println (listLines.size () );
            System.out.println (listLines.subList (listLines.size ()-20, listLines.size ( )));
//            System.out.println (listLines.forEach ();
            listLines.subList (listLines.size ()-20, listLines.size ( )).forEach(System.out::println);
            System.out.println ("----------" );
            listLines.subList (listLines.size ()-20, listLines.size ( )).forEach(new Consumer<String> () {
                @Override
                public void accept(String s) {
                    System.out.println(s);
                }
            });
//            System.out.println(Arrays.toString(listLines.toArray()));
 //           System.out.println ("Содержимое файла " + testFilePath.getFileName () + ":\n" + new String (Files.readAllBytes(testFilePath)));
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }
}


//            testFilePath.getFileName ();


// Cоздание объекта Path через вызов статического метода get() класса Paths
//            Path testFilePath = Paths.get("/home/heorhi/testfile.txt");
//
//            //Пример строки создания объекта Path пути для запуска в Windows
//            //Path testFilePath = Paths.get("D:\\test\\testfile.txt");
//
//            //Вывод инормации о файле
//            System.out.println("Printing file information: ");
//            System.out.println("\t file name: " + testFilePath.getFileName());




