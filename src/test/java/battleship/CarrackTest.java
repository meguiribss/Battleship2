package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    @Test
    @DisplayName("Deve criar Carrack virada a NORTH corretamente")
    void shouldCreateCarrackNorth() {
        Carrack carrack = new Carrack(Compass.NORTH, new Position(2, 3));

        assertEquals("Nau", carrack.getCategory());
        assertEquals(3, carrack.getSize());

        List<IPosition> positions = carrack.getPositions();

        assertEquals(3, positions.size());

        assertEquals(2, positions.get(0).getRow());
        assertEquals(3, positions.get(0).getColumn());

        assertEquals(3, positions.get(1).getRow());
        assertEquals(3, positions.get(1).getColumn());

        assertEquals(4, positions.get(2).getRow());
        assertEquals(3, positions.get(2).getColumn());
    }

    @Test
    @DisplayName("Deve criar Carrack virada a SOUTH corretamente")
    void shouldCreateCarrackSouth() {
        Carrack carrack = new Carrack(Compass.SOUTH, new Position(1, 5));

        List<IPosition> positions = carrack.getPositions();

        assertEquals(3, positions.size());

        assertEquals(1, positions.get(0).getRow());
        assertEquals(5, positions.get(0).getColumn());

        assertEquals(2, positions.get(1).getRow());
        assertEquals(5, positions.get(1).getColumn());

        assertEquals(3, positions.get(2).getRow());
        assertEquals(5, positions.get(2).getColumn());
    }

    @Test
    @DisplayName("Deve criar Carrack virada a EAST corretamente")
    void shouldCreateCarrackEast() {
        Carrack carrack = new Carrack(Compass.EAST, new Position(4, 2));

        List<IPosition> positions = carrack.getPositions();

        assertEquals(3, positions.size());

        assertEquals(4, positions.get(0).getRow());
        assertEquals(2, positions.get(0).getColumn());

        assertEquals(4, positions.get(1).getRow());
        assertEquals(3, positions.get(1).getColumn());

        assertEquals(4, positions.get(2).getRow());
        assertEquals(4, positions.get(2).getColumn());
    }

    @Test
    @DisplayName("Deve criar Carrack virada a WEST corretamente")
    void shouldCreateCarrackWest() {
        Carrack carrack = new Carrack(Compass.WEST, new Position(6, 1));

        List<IPosition> positions = carrack.getPositions();

        assertEquals(3, positions.size());

        assertEquals(6, positions.get(0).getRow());
        assertEquals(1, positions.get(0).getColumn());

        assertEquals(6, positions.get(1).getRow());
        assertEquals(2, positions.get(1).getColumn());

        assertEquals(6, positions.get(2).getRow());
        assertEquals(3, positions.get(2).getColumn());
    }

    @Test
    @DisplayName("Carrack deve começar flutuando")
    void carrackShouldStartFloating() {
        Carrack carrack = new Carrack(Compass.NORTH, new Position(0, 0));

        assertTrue(carrack.stillFloating());
    }

    @Test
    @DisplayName("Carrack deve ocupar posições corretas")
    void shouldOccupyCorrectPositions() {
        Carrack carrack = new Carrack(Compass.EAST, new Position(3, 3));

        assertTrue(carrack.occupies(new Position(3, 3)));
        assertTrue(carrack.occupies(new Position(3, 4)));
        assertTrue(carrack.occupies(new Position(3, 5)));

        assertFalse(carrack.occupies(new Position(3, 6)));
        assertFalse(carrack.occupies(new Position(2, 3)));
    }

    @Test
    @DisplayName("Não deve retornar null ao criar Carrack")
    void shouldCreateCarrackSuccessfully() {
        Carrack carrack = new Carrack(Compass.SOUTH, new Position(1, 1));

        assertNotNull(carrack);
    }
}