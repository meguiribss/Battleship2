package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

	@Test
	@DisplayName("Deve criar Fleet vazia")
	void shouldCreateEmptyFleet() {
		Fleet fleet = new Fleet();

		assertNotNull(fleet);
		assertTrue(fleet.getShips().isEmpty());
	}

	@Test
	@DisplayName("Deve adicionar ship válida")
	void shouldAddValidShip() {
		Fleet fleet = new Fleet();
		IShip ship = new Caravel(Compass.NORTH, new Position(1, 1));

		boolean result = fleet.addShip(ship);

		assertTrue(result);
		assertEquals(1, fleet.getShips().size());
	}

	@Test
	@DisplayName("Não deve adicionar ship fora do tabuleiro")
	void shouldNotAddShipOutsideBoard() {
		Fleet fleet = new Fleet();
		IShip ship = new Carrack(Compass.NORTH, new Position(20, 20));

		boolean result = fleet.addShip(ship);

		assertFalse(result);
		assertTrue(fleet.getShips().isEmpty());
	}

	@Test
	@DisplayName("Não deve adicionar ship com colisão")
	void shouldNotAddShipWithCollision() {
		Fleet fleet = new Fleet();

		IShip ship1 = new Caravel(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Caravel(Compass.NORTH, new Position(1, 1));

		fleet.addShip(ship1);
		boolean result = fleet.addShip(ship2);

		assertFalse(result);
		assertEquals(1, fleet.getShips().size());
	}

	@Test
	@DisplayName("Deve obter ships por categoria")
	void shouldReturnShipsByCategory() {
		Fleet fleet = new Fleet();

		fleet.addShip(new Caravel(Compass.NORTH, new Position(1, 1)));
		fleet.addShip(new Caravel(Compass.NORTH, new Position(4, 4)));
		fleet.addShip(new Carrack(Compass.NORTH, new Position(7, 7)));

		List<IShip> result = fleet.getShipsLike("Caravela");

		assertEquals(2, result.size());
	}

	@Test
	@DisplayName("Deve devolver lista vazia se categoria não existir")
	void shouldReturnEmptyListForUnknownCategory() {
		Fleet fleet = new Fleet();

		fleet.addShip(new Caravel(Compass.NORTH, new Position(1, 1)));

		List<IShip> result = fleet.getShipsLike("Inexistente");

		assertTrue(result.isEmpty());
	}

	@Test
	@DisplayName("Deve obter ships a flutuar")
	void shouldReturnFloatingShips() {
		Fleet fleet = new Fleet();

		IShip ship1 = new Caravel(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Caravel(Compass.NORTH, new Position(5, 5));

		ship2.sink();

		fleet.addShip(ship1);
		fleet.addShip(ship2);

		List<IShip> floating = fleet.getFloatingShips();

		assertEquals(1, floating.size());
	}

	@Test
	@DisplayName("Deve obter ships afundados")
	void shouldReturnSunkShips() {
		Fleet fleet = new Fleet();

		IShip ship1 = new Caravel(Compass.NORTH, new Position(1, 1));
		IShip ship2 = new Caravel(Compass.NORTH, new Position(5, 5));

		ship2.sink();

		fleet.addShip(ship1);
		fleet.addShip(ship2);

		List<IShip> sunk = fleet.getSunkShips();

		assertEquals(1, sunk.size());
	}

	@Test
	@DisplayName("shipAt deve devolver ship correta")
	void shouldReturnShipAtPosition() {
		Fleet fleet = new Fleet();

		IShip ship = new Caravel(Compass.EAST, new Position(3, 3));
		fleet.addShip(ship);

		IShip result = fleet.shipAt(new Position(3, 4));

		assertEquals(ship, result);
	}

	@Test
	@DisplayName("shipAt deve devolver null se não existir")
	void shouldReturnNullIfNoShipAtPosition() {
		Fleet fleet = new Fleet();

		fleet.addShip(new Caravel(Compass.NORTH, new Position(1, 1)));

		IShip result = fleet.shipAt(new Position(9, 9));

		assertNull(result);
	}

	@Test
	@DisplayName("createRandom deve criar fleet completa")
	void shouldCreateRandomFleet() {
		IFleet fleet = Fleet.createRandom();

		assertNotNull(fleet);
		assertEquals(11, fleet.getShips().size());
	}

	@Test
	@DisplayName("print methods não devem lançar exceção")
	void shouldPrintMethodsWithoutErrors() {
		Fleet fleet = new Fleet();

		fleet.addShip(new Caravel(Compass.NORTH, new Position(1, 1)));

		assertDoesNotThrow(() -> {
			fleet.printAllShips();
			fleet.printFloatingShips();
			fleet.printShipsByCategory("Caravela");
			fleet.printStatus();
		});
	}
}