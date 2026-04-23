package battleship;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Scoreboard
 */
public class ScoreboardTest {

    private static final String FILE_NAME = "scoreboard.txt";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Path.of(FILE_NAME));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILE_NAME));
    }

    @Test
    @DisplayName("Deve criar ficheiro e guardar resultado")
    void saveShouldCreateFileAndWriteResult() throws IOException {
        Scoreboard.save("Player1 venceu");

        File file = new File(FILE_NAME);

        assertTrue(file.exists(), "O ficheiro scoreboard.txt devia existir");

        String content = Files.readString(Path.of(FILE_NAME));
        assertEquals("Player1 venceu\n", content);
    }

    @Test
    @DisplayName("Deve adicionar vários resultados no ficheiro")
    void saveShouldAppendMultipleResults() throws IOException {
        Scoreboard.save("Jogador A");
        Scoreboard.save("Jogador B");

        String content = Files.readString(Path.of(FILE_NAME));

        assertEquals("Jogador A\nJogador B\n", content);
    }

    @Test
    @DisplayName("Deve guardar string vazia")
    void saveShouldHandleEmptyString() throws IOException {
        Scoreboard.save("");

        String content = Files.readString(Path.of(FILE_NAME));

        assertEquals("\n", content);
    }

    @Test
    @DisplayName("Deve guardar texto com caracteres especiais")
    void saveShouldHandleSpecialCharacters() throws IOException {
        Scoreboard.save("Vitória João!");

        String content = Files.readString(Path.of(FILE_NAME));

        assertEquals("Vitória João!\n", content);
    }

    @Test
    @DisplayName("Deve criar ficheiro quando não existe")
    void saveShouldCreateFileIfNotExists() {
        File file = new File(FILE_NAME);

        assertFalse(file.exists());

        Scoreboard.save("Teste");

        assertTrue(file.exists());
    }
}