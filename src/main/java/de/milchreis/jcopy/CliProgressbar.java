package de.milchreis.jcopy;

public class CliProgressbar {

    public static final char EMPTY = '░';
    public static final char FILLED = '█';

    private final int width;
    private long current;
    private final long start;
    private final long max;

    public CliProgressbar(int width, long current, long start, long max) {
        this.width = width;
        this.current = current;
        this.start = start;
        this.max = max;
    }

    public String print() {
        StringBuilder bar = new StringBuilder();

        int pos = width;
        for (int p = 0; p < pos; p++) {
            bar.append(EMPTY);
        }

        int amount = (int) (100 * (current - start) / (max - start));
        int move = (pos * amount) / 100;
        return "[" + bar.substring(0, move).replace(EMPTY, FILLED) + amount + "%" + bar.substring(move) + "]";
    }

    public void step() {
        current++;
    }
}