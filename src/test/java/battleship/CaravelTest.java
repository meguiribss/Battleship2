package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

	@Test
	@DisplayName("Deve criar Caravel virada a NORTH corretamente")
	void shouldCreateCaravelNorth() {
		Caravel caravel = new Caravel(Compass.NORTH, new Position(2, 3));

		assertEquals("Caravela", caravel.getCategory());
		assertEquals(2, caravel.getSize());

		List<IPosition> positions = caravel.getPositions();

		assertEquals(2, positions.size());
		assertEquals(2, positions.get(0).getRow());
		assertEquals(3, positions.get(0).getColumn());

		assertEquals(3, positions.get(1).getRow());
		assertEquals(3, positions.get(1).getColumn());
	}

	@Test
	@DisplayName("Deve criar Caravel virada a SOUTH corretamente")
	void shouldCreateCaravelSouth() {
		Caravel caravel = new Caravel(Compass.SOUTH, new Position(4, 1));

		List<IPosition> positions = caravel.getPositions();

		assertEquals(2, positions.size());
		assertEquals(4, positions.get(0).getRow());
		assertEquals(1, positions.get(0).getColumn());

		assertEquals(5, positions.get(1).getRow());
		assertEquals(1, positions.get(1).getColumn());
	}

	@Test
	@DisplayName("Deve criar Caravel virada a EAST corretamente")
	void shouldCreateCaravelEast() {
		Caravel caravel = new Caravel(Compass.EAST, new Position(5, 2));

		List<IPosition> positions = caravel.getPositions();

		assertEquals(2, positions.size());
		assertEquals(5, positions.get(0).getRow());
		assertEquals(2, positions.get(0).getColumn());

		assertEquals(5, positions.get(1).getRow());
		assertEquals(3, positions.get(1).getColumn());
	}

	@Test
	@DisplayName("Deve criar Caravel virada a WEST corretamente")
	void shouldCreateCaravelWest() {
		Caravel caravel = new Caravel(Compass.WEST, new Position(1, 6));

		List<IPosition> positions = caravel.getPositions();

		assertEquals(2, positions.size());
		assertEquals(1, positions.get(0).getRow());
		assertEquals(6, positions.get(0).getColumn());

		assertEquals(1, positions.get(1).getRow());
		assertEquals(7, positions.get(1).getColumn());
	}

	@Test
	@DisplayName("Caravel deve começar intacta")
	void caravelShouldStartFloating() {
		Caravel caravel = new Caravel(Compass.NORTH, new Position(0, 0));

		assertTrue(caravel.stillFloating());
	}

	@Test
	@DisplayName("Caravel deve ocupar posições corretas")
	void shouldOccupyCorrectPositions() {
		Caravel caravel = new Caravel(Compass.EAST, new Position(3, 3));

		assertTrue(caravel.occupies(new Position(3, 3)));
		assertTrue(caravel.occupies(new Position(3, 4)));
		assertFalse(caravel.occupies(new Position(3, 5)));
	}

	@Test
	@DisplayName("Não deve retornar null ao criar Caravel")
	void shouldCreateCaravelSuccessfully() {
		Caravel caravel = new Caravel(Compass.SOUTH, new Position(1, 1));

		assertNotNull(caravel);
	}
}