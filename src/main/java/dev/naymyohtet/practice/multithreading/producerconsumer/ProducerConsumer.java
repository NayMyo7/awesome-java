package dev.naymyohtet.practice.multithreading.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;

// only one thread can access either producer or consumer methods as both are synchronized
public class ProducerConsumer {
    private int count = 0;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

    public synchronized void produce() {
        while (true) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (queue.isEmpty()) {
                count++;
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.add(count);
                System.out.println(Thread.currentThread().getName() + " produced: " + count);
            }

        } // end while
    }

    public synchronized void consume() {
        while (true) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!queue.isEmpty()) {
                Integer consumed = queue.remove(); // consumed
                System.out.println(Thread.currentThread().getName() + " consumed: " + consumed);
                notifyAll();
            }
        } // end while
    }
}
