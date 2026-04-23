package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PDFexporterTest {

    private final String fileName = "game_report.pdf";

    @AfterEach
    void cleanUp() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @DisplayName("Deve criar ficheiro PDF com lista de jogadas")
    void shouldCreatePdfFileSuccessfully() {

        List<String> jogadas = new ArrayList<>();
        jogadas.add("A1");
        jogadas.add("B3");
        jogadas.add("C5");

        assertDoesNotThrow(() -> PDFexporter.export(jogadas, 2));

        File file = new File(fileName);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    @DisplayName("Deve criar PDF mesmo com lista de jogadas vazia")
    void shouldCreatePdfWithEmptyMovesList() {

        List<String> jogadas = new ArrayList<>();

        assertDoesNotThrow(() -> PDFexporter.export(jogadas, 5));

        File file = new File(fileName);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    @DisplayName("Deve criar PDF com zero navios restantes")
    void shouldCreatePdfWithZeroShipsRemaining() {

        List<String> jogadas = new ArrayList<>();
        jogadas.add("D2");

        assertDoesNotThrow(() -> PDFexporter.export(jogadas, 0));

        File file = new File(fileName);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    @DisplayName("Não deve lançar erro com várias jogadas")
    void shouldHandleMultipleMoves() {

        List<String> jogadas = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            jogadas.add("Jogada " + i);
        }

        assertDoesNotThrow(() -> PDFexporter.export(jogadas, 3));
    }
}