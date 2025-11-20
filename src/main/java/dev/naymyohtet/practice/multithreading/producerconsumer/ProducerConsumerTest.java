package dev.naymyohtet.practice.multithreading.producerconsumer;

public class ProducerConsumerTest {
    public static void main(String[] args) {

        ProducerConsumer pc = new ProducerConsumer();

        // Spawn a new producer thread and start
        Thread producer = new Thread(new ProducerThread(pc));
        producer.start();

        // Spawn a new consumer thread and start
        Thread consumer = new Thread(new ConsumerThread(pc));
        consumer.start();
    }
}
