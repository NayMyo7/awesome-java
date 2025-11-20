package dev.naymyohtet.practice.multithreading.producerconsumer;

public class ConsumerThread implements Runnable {

    private ProducerConsumer pc;

    public ConsumerThread(ProducerConsumer pc) {
        this.pc = pc;
    }

    @Override
    public void run() {
        pc.consume();
    }

}
