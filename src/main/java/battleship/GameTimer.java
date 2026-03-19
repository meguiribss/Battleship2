package battleship;

public class GameTimer {

    private long start;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        long end = System.currentTimeMillis();
        System.out.println("Tempo da jogada: " + (end - start) + " ms");
    }
}
