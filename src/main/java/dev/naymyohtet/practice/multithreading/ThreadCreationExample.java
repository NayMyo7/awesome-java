package dev.naymyohtet.practice.multithreading;

// Creating a thread by extending Thread class
class MyThread extends Thread {

    @Override
    public void run() {
        // Thread logic here
        System.out.println("MyThread is running...");
    }

}

// Creating a thread by implementing Runnable interface
class MyRunnable implements Runnable {

    @Override
    public void run() {
        // Thread logic here
        System.out.println("MyRunnable is running...");
    }

}

public class ThreadCreationExample {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        Thread thread2 = new Thread(new MyRunnable());

        thread1.start(); // Start the thread 1
        thread2.start(); // Start the thread 2
    }
}
