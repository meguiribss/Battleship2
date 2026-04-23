package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    @DisplayName("buildShip deve criar Barge corretamente")
    void shouldBuildBarge() {
        Ship ship = Ship.buildShip("barca", Compass.NORTH, new Position(1, 1));

        assertNotNull(ship);
        assertTrue(ship instanceof Barge);
    }

    @Test
    @DisplayName("buildShip deve criar Caravel corretamente")
    void shouldBuildCaravel() {
        Ship ship = Ship.buildShip("caravela", Compass.NORTH, new Position(1, 1));

        assertNotNull(ship);
        assertTrue(ship instanceof Caravel);
    }

    @Test
    @DisplayName("buildShip deve criar Carrack corretamente")
    void shouldBuildCarrack() {
        Ship ship = Ship.buildShip("nau", Compass.NORTH, new Position(1, 1));

        assertNotNull(ship);
        assertTrue(ship instanceof Carrack);
    }

    @Test
    @DisplayName("buildShip deve criar Frigate corretamente")
    void shouldBuildFrigate() {
        Ship ship = Ship.buildShip("fragata", Compass.NORTH, new Position(1, 1));

        assertNotNull(ship);
        assertTrue(ship instanceof Frigate);
    }

    @Test
    @DisplayName("buildShip deve criar Galleon corretamente")
    void shouldBuildGalleon() {
        Ship ship = Ship.buildShip("galeao", Compass.NORTH, new Position(1, 1));

        assertNotNull(ship);
        assertTrue(ship instanceof Galleon);
    }

    @Test
    @DisplayName("buildShip com tipo inválido deve retornar null")
    void shouldReturnNullForInvalidType() {
        Ship ship = Ship.buildShip("invalido", Compass.NORTH, new Position(1, 1));

        assertNull(ship);
    }

    @Test
    @DisplayName("Deve obter categoria corretamente")
    void shouldReturnCorrectCategory() {
        Caravel ship = new Caravel(Compass.NORTH, new Position(2, 2));

        assertEquals("Caravela", ship.getCategory());
    }

    @Test
    @DisplayName("Deve obter bearing corretamente")
    void shouldReturnCorrectBearing() {
        Caravel ship = new Caravel(Compass.WEST, new Position(2, 2));

        assertEquals(Compass.WEST, ship.getBearing());
    }

    @Test
    @DisplayName("Deve obter posição inicial corretamente")
    void shouldReturnInitialPosition() {
        Position pos = new Position(3, 4);
        Caravel ship = new Caravel(Compass.NORTH, pos);

        assertEquals(pos, ship.getPosition());
    }

    @Test
    @DisplayName("Deve obter size corretamente")
    void shouldReturnCorrectSize() {
        Carrack ship = new Carrack(Compass.NORTH, new Position(1, 1));

        assertEquals(3, ship.getSize());
    }

    @Test
    @DisplayName("Ship deve ocupar posições corretas")
    void shouldOccupyCorrectPositions() {
        Caravel ship = new Caravel(Compass.EAST, new Position(5, 5));

        assertTrue(ship.occupies(new Position(5, 5)));
        assertTrue(ship.occupies(new Position(5, 6)));
        assertFalse(ship.occupies(new Position(5, 7)));
    }

    @Test
    @DisplayName("Ship deve continuar flutuando inicialmente")
    void shouldStillBeFloatingInitially() {
        Caravel ship = new Caravel(Compass.NORTH, new Position(1, 1));

        assertTrue(ship.stillFloating());
    }

    @Test
    @DisplayName("Ship afundado não deve continuar flutuando")
    void shouldNotFloatAfterSink() {
        Caravel ship = new Caravel(Compass.NORTH, new Position(1, 1));

        ship.sink();

        assertFalse(ship.stillFloating());
    }

    @Test
    @DisplayName("Shoot deve acertar posição correta")
    void shouldShootCorrectPosition() {
        Caravel ship = new Caravel(Compass.NORTH, new Position(1, 1));

        ship.shoot(new Position(1, 1));

        assertTrue(ship.stillFloating());
    }

    @Test
    @DisplayName("TopMostPos deve retornar linha mínima")
    void shouldReturnTopMostPosition() {
        Carrack ship = new Carrack(Compass.NORTH, new Position(4, 2));

        assertEquals(4, ship.getTopMostPos());
    }

    @Test
    @DisplayName("BottomMostPos deve retornar linha máxima")
    void shouldReturnBottomMostPosition() {
        Carrack ship = new Carrack(Compass.NORTH, new Position(4, 2));

        assertEquals(6, ship.getBottomMostPos());
    }

    @Test
    @DisplayName("LeftMostPos deve retornar coluna mínima")
    void shouldReturnLeftMostPosition() {
        Carrack ship = new Carrack(Compass.EAST, new Position(4, 2));

        assertEquals(2, ship.getLeftMostPos());
    }

    @Test
    @DisplayName("RightMostPos deve retornar coluna máxima")
    void shouldReturnRightMostPosition() {
        Carrack ship = new Carrack(Compass.EAST, new Position(4, 2));

        assertEquals(4, ship.getRightMostPos());
    }

    @Test
    @DisplayName("ToString deve retornar formato correto")
    void shouldReturnCorrectString() {
        Caravel ship = new Caravel(Compass.NORTH, new Position(1, 1));

        assertNotNull(ship.toString());
        assertTrue(ship.toString().contains("Caravela"));
    }

    @Test
    @DisplayName("AdjacentPositions não deve vir vazio")
    void shouldReturnAdjacentPositions() {
        Caravel ship = new Caravel(Compass.NORTH, new Position(3, 3));

        List<IPosition> adj = ship.getAdjacentPositions();

        assertNotNull(adj);
        assertFalse(adj.isEmpty());
    }
}