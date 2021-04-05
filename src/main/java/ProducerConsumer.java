import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProducerConsumer {
    ConcurrentLinkedQueue<String> stringQueue = new ConcurrentLinkedQueue<>();
    private final int interval;
    private boolean isStopped = false;

    public ProducerConsumer(int interval) {
        this.interval = interval;
    }

    public synchronized void produce() throws InterruptedException {
        while (!isStopped) {
            stringQueue.add(generateRandomString());
            notify();
            wait(interval * 1000L);
        }
    }

    public synchronized void consume() throws InterruptedException {
        while (!isStopped) {
            if (stringQueue.isEmpty()) {
                wait();
            }
            String toParse = stringQueue.poll();
            assert toParse != null;
            long count = toParse.chars().distinct().count();
            System.out.printf("Строка: %s \nЧисло уникальных символов: %d\n\n", toParse, count);
        }
    }

    private String generateRandomString() {
        int k = new Random().nextInt(100);
        byte[] array = new byte[k];
        new Random().nextBytes(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) Math.abs(array[i] % 26);
            array[i] += 'a';
        }
        return new String(array, StandardCharsets.UTF_8);
    }

    public void stop() {
        isStopped = true;
    }

    public boolean isStopped() {
        return isStopped;
    }
}