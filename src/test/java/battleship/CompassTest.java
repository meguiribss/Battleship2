package battleship;

import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Improved Test class for Compass
 */
class CompassTest {

	private Compass compass;

	@BeforeEach
	void setUp() {
		compass = Compass.NORTH;
	}

	@AfterEach
	void tearDown() {
		compass = null;
	}

	// ---------------- CONSTRUCTOR / ENUM VALUES ----------------

	@Test
	@DisplayName("Enum instance should exist")
	void constructor() {
		assertNotNull(compass);
	}

	@Test
	@DisplayName("Compass should contain exactly 4 values")
	void valuesTest() {
		Compass[] values = Compass.values();

		assertEquals(4, values.length);
		assertArrayEquals(
				new Compass[]{
						Compass.NORTH,
						Compass.SOUTH,
						Compass.EAST,
						Compass.WEST
				},
				values
		);
	}

	// ---------------- GET DIRECTION ----------------

	@Test
	@DisplayName("Each direction returns correct char")
	void getDirection() {
		assertEquals('n', Compass.NORTH.getDirection());
		assertEquals('s', Compass.SOUTH.getDirection());
		assertEquals('e', Compass.EAST.getDirection());
		assertEquals('o', Compass.WEST.getDirection());
	}

	@Test
	@DisplayName("All directions must be unique")
	void getDirectionUnique() {
		Set<Character> chars = new HashSet<>();

		for (Compass c : Compass.values()) {
			chars.add(c.getDirection());
		}

		assertEquals(4, chars.size());
	}

	// ---------------- TO STRING ----------------

	@Test
	@DisplayName("toString returns correct values")
	void toStringTest() {
		assertEquals("n", Compass.NORTH.toString());
		assertEquals("s", Compass.SOUTH.toString());
		assertEquals("e", Compass.EAST.toString());
		assertEquals("o", Compass.WEST.toString());
	}

	@Test
	@DisplayName("toString matches getDirection")
	void toStringMatchesDirection() {
		for (Compass c : Compass.values()) {
			assertEquals(String.valueOf(c.getDirection()), c.toString());
		}
	}

	// ---------------- CHAR TO COMPASS ----------------

	@Test
	@DisplayName("charToCompass valid inputs")
	void charToCompass1() {
		assertEquals(Compass.NORTH, Compass.charToCompass('n'));
		assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
		assertEquals(Compass.EAST, Compass.charToCompass('e'));
		assertEquals(Compass.WEST, Compass.charToCompass('o'));
	}

	@Test
	@DisplayName("charToCompass invalid lowercase input")
	void charToCompass2() {
		assertNull(Compass.charToCompass('x'));
		assertNull(Compass.charToCompass('a'));
		assertNull(Compass.charToCompass('z'));
	}

	@Test
	@DisplayName("charToCompass null character")
	void charToCompass3() {
		assertNull(Compass.charToCompass('\0'));
	}

	@Test
	@DisplayName("charToCompass uppercase should return null")
	void charToCompassUpperCase() {
		assertNull(Compass.charToCompass('N'));
		assertNull(Compass.charToCompass('S'));
		assertNull(Compass.charToCompass('E'));
		assertNull(Compass.charToCompass('O'));
	}

	@Test
	@DisplayName("charToCompass symbols should return null")
	void charToCompassSymbols() {
		assertNull(Compass.charToCompass('1'));
		assertNull(Compass.charToCompass('*'));
		assertNull(Compass.charToCompass('-'));
		assertNull(Compass.charToCompass(' '));
	}

	// ---------------- RANDOM BEARING ----------------

	@Test
	@DisplayName("randomBearing never returns null")
	void randomBearingNotNull() {
		for (int i = 0; i < 100; i++) {
			assertNotNull(Compass.randomBearing());
		}
	}

	@Test
	@DisplayName("randomBearing returns valid enum values")
	void randomBearingValidValues() {
		for (int i = 0; i < 100; i++) {
			Compass c = Compass.randomBearing();

			assertTrue(
					c == Compass.NORTH ||
							c == Compass.SOUTH ||
							c == Compass.EAST ||
							c == Compass.WEST
			);
		}
	}

	@Test
	@DisplayName("randomBearing should generate more than one value over many tries")
	void randomBearingVariety() {
		Set<Compass> generated = new HashSet<>();

		for (int i = 0; i < 100; i++) {
			generated.add(Compass.randomBearing());
		}

		assertTrue(generated.size() > 1);
	}
}