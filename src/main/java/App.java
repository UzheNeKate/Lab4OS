import java.util.Arrays;

public class App {
    public static void main(String[] args) throws InterruptedException {
        int length = 5;
        Thread[] producerThreads = new Thread[length];
        Thread[] consumerThreads = new Thread[length];

        for (int i = 0; i < length; i++) {
            var c = new ProducerConsumer(12);
            producerThreads[i] = new Thread(() -> {
                try {
                    c.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            consumerThreads[i] = new Thread(() -> {
                try {
                    c.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Arrays.stream(producerThreads).forEach(Thread::start);
        Arrays.stream(consumerThreads).forEach(Thread::start);

        for (Thread producerThread : producerThreads) {
            producerThread.join();
        }

        for (Thread consumerThread : consumerThreads) {
            consumerThread.join();
        }
    }
}