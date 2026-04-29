package battleship;

import java.util.HashMap;
import java.util.Map;

public class ShotStats {

    private int validShots;
    private int repeatedShots;
    private int missedShots;

    private Map<String, Integer> sunkBoatsCount = new HashMap<>();
    private Map<String, Integer> hitsPerBoat = new HashMap<>();

    // getters
    public int getValidShots() { return validShots; }
    public int getRepeatedShots() { return repeatedShots; }
    public int getMissedShots() { return missedShots; }

    public Map<String, Integer> getSunkBoatsCount() { return sunkBoatsCount; }
    public Map<String, Integer> getHitsPerBoat() { return hitsPerBoat; }

    // increment methods
    public void incrementValidShots() { validShots++; }
    public void incrementRepeatedShots() { repeatedShots++; }
    public void incrementMissedShots() { missedShots++; }

    public void incrementHits(String boat) {
        hitsPerBoat.put(boat, hitsPerBoat.getOrDefault(boat, 0) + 1);
    }

    public void incrementSunk(String boat) {
        sunkBoatsCount.put(boat, sunkBoatsCount.getOrDefault(boat, 0) + 1);
    }
}