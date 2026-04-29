package battleship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.*;

/**
 * Shot
 *
 * @author Your Name
 * Date: 20/02/2026
 * Time: 19:39
 */
public class Move implements IMove {

	//-------------------------------------------------------------------
	private final int number;
	private final List<IPosition> shots;
	private final List<IGame.ShotResult> shotResults;

	//-------------------------------------------------------------------
	public Move(int moveNumber, List<IPosition> moveShots, List<IGame.ShotResult> moveResults) {
		this.number = moveNumber;
		this.shots = moveShots;
		this.shotResults = moveResults;
	}

	@Override
	public String toString() {
		return "Move{" +
				"number=" + number +
				", shots=" + shots.size() +
				", results=" + shotResults.size() +
				'}';
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public List<IPosition> getShots() {
		return this.shots;
	}

	@Override
	public List<IGame.ShotResult> getShotResults() {
		return this.shotResults;
	}

	/**
	 * Processes the results of enemy fire on the game board, analyzing the outcomes of shots,
	 * such as valid shots, repeated shots, missed shots, hits on ships, and sunk ships. It can
	 * also display a detailed summary of the shot results if verbose mode is activated.
	 *
	 * @param verbose a boolean indicating whether a detailed summary should be printed to the console
	 *                for the processed enemy fire data.
	 * @return a JSON-formatted string that encapsulates the results, including counts of valid shots,
	 *         repeated shots, missed shots, shots outside the game board, and details of hits and
	 *         sunk ships.
	 */
	@Override
	public String processEnemyFire(boolean verbose) {

		ShotStats stats = new ShotStats();
		processShotResults(stats);

		int validShots = stats.getValidShots();
		int repeatedShots = stats.getRepeatedShots();
		int missedShots = stats.getMissedShots();

		Map<String, Integer> sunkBoatsCount = stats.getSunkBoatsCount();
		Map<String, Integer> hitsPerBoat = stats.getHitsPerBoat();

		int outsideShots = Game.NUMBER_SHOTS - validShots - repeatedShots;

		boolean onlyRepeatedShots = validShots == 0 && repeatedShots > 0;
		boolean hasValidShots = validShots > 0;
		boolean hasMissedShots = missedShots > 0;
		boolean hasRepeatedShots = repeatedShots > 0;
		boolean hasHitsOrSinks = !sunkBoatsCount.isEmpty() || !hitsPerBoat.isEmpty();

		if (verbose) {
			StringBuilder output = new StringBuilder();

			if (onlyRepeatedShots) {
				output.append(repeatedShots).append(" tiro").append(repeatedShots > 1 ? "s" : "").append(" repetido").append(repeatedShots > 1 ? "s" : "");
			} else {
				if (hasValidShots) {
					output.append(validShots).append(" tiro").append(validShots > 1 ? "s" : "").append(" válido").append(validShots > 1 ? "s" : "").append(": ");
				}

				if (!sunkBoatsCount.isEmpty()) {
					for (Map.Entry<String, Integer> entry : sunkBoatsCount.entrySet()) {
						String boatName = entry.getKey();
						int count = entry.getValue();
						output.append(count).append(" ").append(boatName).append(count > 1 ? "s" : "").append(" ao fundo").append(" + ");
					}
				}

				if (!hitsPerBoat.isEmpty()) {
					for (Map.Entry<String, Integer> entry : hitsPerBoat.entrySet()) {
						String boatName = entry.getKey();
						int hits = entry.getValue();
						boolean shipNotSunk = !sunkBoatsCount.containsKey(boatName);
						if (shipNotSunk) {
							output.append(hits).append(" tiro").append(hits > 1 ? "s" : "").append(" num(a) ").append(boatName).append(" + ");
						}
					}
				}

				if (hasMissedShots) {
					output.append(missedShots).append(" tiro").append(missedShots > 1 ? "s" : "").append(" na água");
				} else if (hasHitsOrSinks) {
					output.setLength(output.length() - 2);
				}

				if (hasRepeatedShots) {
					if (hasValidShots) {
						output.append(", ");
					}
					output.append(repeatedShots).append(" tiro").append(repeatedShots > 1 ? "s" : "").append(" repetido").append(repeatedShots > 1 ? "s" : "");
				}
			}

			if (outsideShots > 0) {
				if (!output.isEmpty()) {
					output.append(", ");
				}
				output.append(outsideShots).append(" tiro").append(outsideShots > 1 ? "s" : "").append(" exterior").append(outsideShots > 1 ? "es" : "");
			}

			System.out.println("Jogada nº" + this.number + " -> " + output);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("validShots", validShots);
		response.put("outsideShots", outsideShots);
		response.put("repeatedShots", repeatedShots);
		response.put("missedShots", missedShots);

		List<Map<String, Object>> sunkBoats = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : sunkBoatsCount.entrySet()) {
			Map<String, Object> boat = new HashMap<>();
			boat.put("type", entry.getKey());
			boat.put("count", entry.getValue());
			sunkBoats.add(boat);
		}
		response.put("sunkBoats", sunkBoats);

		List<Map<String, Object>> boatHits = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : hitsPerBoat.entrySet()) {
			if (!sunkBoatsCount.containsKey(entry.getKey())) {
				Map<String, Object> boat = new HashMap<>();
				boat.put("type", entry.getKey());
				boat.put("hits", entry.getValue());
				boatHits.add(boat);
			}
		}
		response.put("hitsOnBoats", boatHits);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			return objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Erro ao serializar o JSON dos resultados da jogada", e);
		}
	}

	private void processShotResults(ShotStats stats) {
		for (IGame.ShotResult result : this.shotResults) {

			if (!result.valid()) continue;

			if (result.repeated()) {
				stats.incrementRepeatedShots();
			} else {
				stats.incrementValidShots();

				if (result.ship() == null) {
					stats.incrementMissedShots();
				} else {
					String boatName = result.ship().getCategory();

					stats.incrementHits(boatName);

					if (result.sunk()) {
						stats.incrementSunk(boatName);
					}
				}
			}
		}
	}

}