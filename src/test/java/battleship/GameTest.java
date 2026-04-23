package battleship;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clean and corrected GameTest
 */
class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game(new Fleet());
	}

	@AfterEach
	void tearDown() {
		game = null;
	}

	// ---------------- CONSTRUCTOR ----------------

	@Test
	@DisplayName("Game constructor initializes correctly")
	void constructor() {
		assertNotNull(game);
		assertNotNull(game.getMyFleet());
		assertNotNull(game.getAlienMoves());
		assertNotNull(game.getMyMoves());

		assertEquals(0, game.getInvalidShots());
		assertEquals(0, game.getRepeatedShots());
		assertEquals(0, game.getHits());
		assertEquals(0, game.getSunkShips());

		assertTrue(game.getAlienMoves().isEmpty());
		assertTrue(game.getMyMoves().isEmpty());
	}

	// ---------------- FIRE SINGLE SHOT ----------------

	@Test
	@DisplayName("Invalid shot increments invalid counter")
	void invalidShot() {
		game.fireSingleShot(new Position(-1, 5), false);

		assertEquals(1, game.getInvalidShots());
	}

	@Test
	@DisplayName("Repeated shot by flag increments repeated counter")
	void repeatedShotFlag() {
		Position p = new Position(2, 3);

		game.fireSingleShot(p, false);
		game.fireSingleShot(p, true);

		assertEquals(1, game.getRepeatedShots());
	}

	@Test
	@DisplayName("Water shot does not increment hits")
	void waterShot() {
		game.fireSingleShot(new Position(9, 9), false);

		assertEquals(0, game.getHits());
	}

	@Test
	@DisplayName("Hit ship increments hits")
	void hitShip() {
		Fleet fleet = new Fleet();
		Ship ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);

		Game g = new Game(fleet);

		g.fireSingleShot(new Position(1, 1), false);

		assertEquals(1, g.getHits());
	}

	@Test
	@DisplayName("Sink ship increments sunk ships")
	void sinkShip() {
		Fleet fleet = new Fleet();
		Ship ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);

		Game g = new Game(fleet);

		for (IPosition p : ship.getPositions()) {
			g.fireSingleShot(p, false);
		}

		assertEquals(1, g.getSunkShips());
	}

	// ---------------- FIRE SHOTS ----------------

	@Test
	@DisplayName("fireShots valid move adds move")
	void fireShotsValid() {
		List<IPosition> shots = List.of(
				new Position(0, 0),
				new Position(0, 1),
				new Position(0, 2)
		);

		game.fireShots(shots);

		assertEquals(1, game.getAlienMoves().size());
	}

	@Test
	@DisplayName("fireShots less than 3 throws exception")
	void fireShotsTooSmall() {
		List<IPosition> shots = List.of(
				new Position(0, 0),
				new Position(0, 1)
		);

		assertThrows(IllegalArgumentException.class,
				() -> game.fireShots(shots));
	}

	@Test
	@DisplayName("fireShots more than 3 throws exception")
	void fireShotsTooLarge() {
		List<IPosition> shots = List.of(
				new Position(0, 0),
				new Position(0, 1),
				new Position(0, 2),
				new Position(0, 3)
		);

		assertThrows(IllegalArgumentException.class,
				() -> game.fireShots(shots));
	}

	// ---------------- REPEATED SHOT ----------------

	@Test
	@DisplayName("repeatedShot true after move")
	void repeatedShotTrue() {
		List<IPosition> shots = List.of(
				new Position(2, 3),
				new Position(2, 4),
				new Position(2, 5)
		);

		game.fireShots(shots);

		assertTrue(game.repeatedShot(new Position(2, 3)));
	}

	@Test
	@DisplayName("repeatedShot false before move")
	void repeatedShotFalse() {
		assertFalse(game.repeatedShot(new Position(2, 3)));
	}

	// ---------------- GETTERS ----------------

	@Test
	@DisplayName("Remaining ships count")
	void remainingShips() {
		Fleet fleet = new Fleet();
		Game g = new Game(fleet);

		Ship ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		Ship ship2 = new Frigate(Compass.EAST, new Position(5, 5));

		fleet.addShip(ship1);
		fleet.addShip(ship2);

		assertEquals(2, g.getRemainingShips());

		ship1.sink();

		assertEquals(1, g.getRemainingShips());
	}

	// ---------------- READ ENEMY FIRE ----------------

	@Test
	@DisplayName("readEnemyFire valid input")
	void readEnemyFireValid() {
		Scanner sc = new Scanner("A1 B2 C3");

		String json = game.readEnemyFire(sc);

		assertNotNull(json);
		assertEquals(1, game.getAlienMoves().size());
	}

	@Test
	@DisplayName("readEnemyFire incomplete input throws")
	void readEnemyFireInvalid() {
		Scanner sc = new Scanner("A1 B2");

		assertThrows(IllegalArgumentException.class,
				() -> game.readEnemyFire(sc));
	}

	@Test
	@DisplayName("readEnemyFire missing number throws")
	void readEnemyFireMissingNumber() {
		Scanner sc = new Scanner("A B2 C3");

		assertThrows(IllegalArgumentException.class,
				() -> game.readEnemyFire(sc));
	}

	// ---------------- JSON ----------------

	@Test
	@DisplayName("jsonShots returns json text")
	void jsonShots() {
		List<IPosition> shots = List.of(
				new Position(0, 0),
				new Position(1, 1),
				new Position(2, 2)
		);

		String json = Game.jsonShots(shots);

		assertNotNull(json);
		assertTrue(json.contains("row"));
		assertTrue(json.contains("column"));
	}

	// ---------------- RANDOM ----------------

	@Test
	@DisplayName("randomEnemyFire returns json")
	void randomEnemyFire() {
		String json = game.randomEnemyFire();

		assertNotNull(json);
		assertEquals(1, game.getAlienMoves().size());
	}

	// ---------------- PRINT METHODS ----------------

	@Test
	@DisplayName("print methods do not throw")
	void printMethods() {
		assertDoesNotThrow(() -> game.printMyBoard(true, true));
		assertDoesNotThrow(() -> game.printAlienBoard(true, true));
	}

	@Test
	@DisplayName("over method does not throw")
	void overMethod() {
		assertDoesNotThrow(() -> game.over());
	}
}