package Lesson06;

import java.util.Arrays;

/*
2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки.
Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.
Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
3. Написать метод, который проверяет состав массива из чисел 1 и 4.
Если в нем нет хоть одной четверки или единицы, то метод вернет false;
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 1};
        System.out.println (Arrays.toString (a));
        a = method01 (a);
        System.out.println (Arrays.toString (a));
        System.out.println (method02 (a) );
    }

    public static int[] method01(int[] a) {
        int fourIndexInArr = 0;
        boolean fourIndex = false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 4) {
                fourIndexInArr = i;
                fourIndex = true;
            }
        }
        if (fourIndex == true) {
            int newLenght = a.length - fourIndexInArr - 1;
            int[] b = new int[newLenght];
            for (int i = 0; i < b.length; i++) {
                b[i] = a[fourIndexInArr + i + 1];
            }
            a = b;
            return b;
        } else throw new RuntimeException ( );
    }

    public static boolean method02(int[] a) {
        boolean bool = false;
        for (int i = 0; i < a.length; i++) {

            if (a[i] == 1 || a[i] == 4){
                bool = true;
            }
        }
        return bool;
    }
}