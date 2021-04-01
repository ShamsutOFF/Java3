package Lesson05;
/*
Организуем гонки:
Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого их них уходит разное время.
В тоннель не может заехать одновременно больше половины участников (условность).
Попробуйте все это синхронизировать.
Только после того, как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int CARS_COUNT = 4;

    static Semaphore smpForTunnel = new Semaphore (CARS_COUNT / 2);
    static CountDownLatch start = new CountDownLatch (CARS_COUNT);
    static CountDownLatch startFlag = new CountDownLatch (1);
    static CountDownLatch finishFlag = new CountDownLatch (CARS_COUNT);
    static volatile AtomicInteger atomicInteger = new AtomicInteger (0);

    public static void main(String[] args) throws InterruptedException {

        System.out.println ("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race (new Road (60), new Tunnel ( ), new Road (40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car (race, 20 + (int) (Math.random ( ) * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread (cars[i]).start ( );
        }
        start.await ( );
        System.out.println ("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        startFlag.countDown ( );

        finishFlag.await ( );
        System.out.println ("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
/*
Что примерно должно получиться
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!
Участник #2 готовится
Участник #1 готовится
Участник #4 готовится
Участник #3 готовится
Участник #2 готов
Участник #4 готов
Участник #1 готов
Участник #3 готов
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!
Участник #2 начал этап: Дорога 60 метров
Участник #4 начал этап: Дорога 60 метров
Участник #3 начал этап: Дорога 60 метров
Участник #1 начал этап: Дорога 60 метров
Участник #1 закончил этап: Дорога 60 метров
Участник #3 закончил этап: Дорога 60 метров
Участник #3 готовится к этапу (ждет): Тоннель 80 метров
Участник #1 готовится к этапу (ждет): Тоннель 80 метров
Участник #1 начал этап: Тоннель 80 метров
Участник #3 начал этап: Тоннель 80 метров
Участник #4 закончил этап: Дорога 60 метров
Участник #4 готовится к этапу (ждет): Тоннель 80 метров
Участник #2 закончил этап: Дорога 60 метров
Участник #2 готовится к этапу (ждет): Тоннель 80 метров
Участник #3 закончил этап: Тоннель 80 метров
Участник #1 закончил этап: Тоннель 80 метров
Участник #2 начал этап: Тоннель 80 метров
Участник #4 начал этап: Тоннель 80 метров
Участник #3 начал этап: Дорога 40 метров
Участник #1 начал этап: Дорога 40 метров
Участник #3 закончил этап: Дорога 40 метров
Участник #3 - WIN
Участник #1 закончил этап: Дорога 40 метров
Участник #4 закончил этап: Тоннель 80 метров
Участник #4 начал этап: Дорога 40 метров
Участник #2 закончил этап: Тоннель 80 метров
Участник #2 начал этап: Дорога 40 метров
Участник #2 закончил этап: Дорога 40 метров
Участник #4 закончил этап: Дорога 40 метров
ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!
 */