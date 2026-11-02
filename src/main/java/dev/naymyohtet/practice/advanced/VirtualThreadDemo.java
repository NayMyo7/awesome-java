package dev.naymyohtet.practice.advanced;

import java.util.concurrent.Executors;

public class VirtualThreadDemo {
    public static void main(String[] args) throws Exception {
        // Virtual Thread
        Thread vThread = Thread.ofVirtual().start(() -> {
            System.out.println("Executing in Virtual Thread: " + Thread.currentThread());
        });
        vThread.join();

        // Traditional Thread
        Thread thread = new Thread(() -> {
            System.out.println("Executing in Platform Thread");
        });
        thread.start();

        // Multiple virtual thread
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i <= 1_000; i++) {
                int taskId = i;

                executor.submit(() -> {
                    Thread.sleep(1000);
                    System.out.println(taskId);
                    return null;
                });
            }
        }
    }
}
