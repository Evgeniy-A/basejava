package ru.javawebinar.basejava;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    private static int counter;
    public static final int THREADS_NUMBER = 10000;
    private static final Object LOCK_0 = new Object();
    private static final Object LOCK_1 = new Object();
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final AtomicInteger atomicCounter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread(() -> System.out.println(
                Thread.currentThread().getName() + ", " + Thread.currentThread().getState()));
        thread0.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() +
                                            ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    String ts = LocalDateTime.now().format(DATE_TIME_FORMATTER);
                    System.out.println(ts);
                }
                latch.countDown();
                return 5;
            });
            Thread.sleep(500);
            System.out.println(counter);
        }

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
        atomicCounter.incrementAndGet();
    }
}