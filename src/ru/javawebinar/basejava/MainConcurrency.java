package ru.javawebinar.basejava;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK_0 = new Object();
    private static final Object LOCK_1 = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread(() -> System.out.println(
                Thread.currentThread().getName() + ", " + Thread.currentThread().getState()));
        thread0.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() +
                                            ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    if (j % 2 == 0) {
                        inc();
                    } else calculateSin();
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(counter);
    }

    private static void inc() {
        synchronized (LOCK_0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (LOCK_1) {
                counter++;
            }
        }
    }

    private static void calculateSin() {
        synchronized (LOCK_1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (LOCK_0) {
                double a = Math.sin(13.);
            }
        }
    }
}