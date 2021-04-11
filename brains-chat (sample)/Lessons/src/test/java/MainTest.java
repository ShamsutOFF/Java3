package test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static Lesson06.Main.method01;
import static Lesson06.Main.method02;

class MainTest {


    @Test
    void method011() {
        int testArr[] = {1, 2, 3, 4, 5, 6, 7};
        int expArr[] = {5, 6, 7};
        Assertions.assertArrayEquals (expArr, method01 (testArr), "ОК");
    }

    @Test
    void method012() {
        int testArr[] = {1, 2, 3, 4, 5, 6, 4, 1, 2};
        int expArr[] = {1, 2};
        Assertions.assertArrayEquals (expArr, method01 (testArr), "ОК");
    }

    @Test
    void method01RuntimeException() {
        int testArr[] = {1, 2, 3, 5, 6, 1, 2};
        Assertions.assertThrows (RuntimeException.class, () -> method01 (testArr));
    }

    @Test
    void method01RuntimeException2() {
        int testArr[] = {};
        Assertions.assertThrows (RuntimeException.class, () -> method01 (testArr));
    }

    @Test
    void method021() {
        int testArr[] = {1, 2, 3, 4, 5, 6, 4, 1, 2};
        Assertions.assertTrue (method02 (testArr));
    }

    @Test
    void method022() {
        int testArr[] = {2, 2, 3, 4, 5, 6, 4, 0, 2};
        Assertions.assertTrue (method02 (testArr));
    }

    @Test
    void method023() {
        int testArr[] = {2, 2, 3, 1, 5, 6, 0, 2};
        Assertions.assertTrue (method02 (testArr));
    }

    @Test
    void method024() {
        int testArr[] = {2, 2, 3, 5, 6, 0, 2};
        Assertions.assertFalse (method02 (testArr));
    }

    @Test
    void method025() {
        int testArr[] = {};
        Assertions.assertFalse (method02 (testArr));
    }
}