package Lesson01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
2. Написать метод, который преобразует массив в ArrayList;
3. Большая задача:
Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
Для хранения фруктов внутри коробки можно использовать ArrayList;
Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта
(вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах);
    Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра,
true – если она равны по весу, false – в противном случае (коробки с яблоками мы можем сравнивать с коробками с апельсинами);
    Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую (помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами).
Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
Не забываем про метод добавления фрукта в коробку.
 */
public class Main {
    public static void main(String[] args) {
        String [] strArr = {"One", "Two", "Three", "Four", "Five"};
        Integer [] integersArr = {1,2,3,4,5};

        //------1
        System.out.println (Arrays.toString (strArr));
        System.out.println (Arrays.toString (integersArr));
        relocateMethod (strArr,2,5);
        relocateMethod (integersArr,2,5);
        System.out.println (Arrays.toString (strArr));
        System.out.println (Arrays.toString (integersArr));

        //------2
        ArrayList<Object> objectList = new ArrayList( );
        ArrayList<Object> objectList2 = new ArrayList( );
        toListMethod(objectList,strArr);
        toListMethod (objectList2,integersArr);
        System.out.println (objectList.toString () );
        System.out.println (objectList2.toString () );

        //-----3
        Orange orange = new Orange (1.5f);
        Apple apple = new Apple (1.0f);
        Box <Orange> orangeBox = new Box<> ();
        Box <Apple> appleBox = new Box<> ();

        Box <Orange> orangeBox2 = new Box<> ();
        Box <Apple> appleBox2 = new Box<> ();

        appleBox.addFruit (8, apple);
        appleBox2.addFruit (5, apple);
        orangeBox.addFruit (5, orange);
        orangeBox2.addFruit (4, orange);

        System.out.println (orangeBox.compare (appleBox));

        System.out.println ( "Вес коробки яблок 1: " + appleBox.getWeight ());
        System.out.println ( "Вес коробки яблок 2: " + appleBox2.getWeight ());
        System.out.println ( "Пересыпали яблоки");
        appleBox.pour(appleBox2);
        System.out.println ( "Вес коробки яблок 1: " + appleBox.getWeight ());
        System.out.println ( "Вес коробки яблок 2: " + appleBox2.getWeight ());

        System.out.println ( );

        System.out.println ( "Вес коробки апельсинов 1: " + orangeBox.getWeight ());
        System.out.println ( "Вес коробки апельсинов 2: " + orangeBox2.getWeight ());
        System.out.println ( "Пересыпали яблоки");
        orangeBox.pour(orangeBox2);
        System.out.println ( "Вес коробки апельсинов 1: " + orangeBox.getWeight ());
        System.out.println ( "Вес коробки апельсинов 2: " + orangeBox2.getWeight ());
    }
//---1
public static void relocateMethod(Object arr[],int a, int b){
    Object o = arr[a-1];
    arr[a-1] = arr[b-1];
    arr[b-1] = o;
}
//---2
    public static void toListMethod(ArrayList<Object> list,Object arr[]){
        Collections.addAll(list, arr);
    }
}
