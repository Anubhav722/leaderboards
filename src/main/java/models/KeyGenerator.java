package models;

public class KeyGenerator {
    private int count = 0;

    public synchronized int getKey() {
        ++count;
        return count;
    }
}
