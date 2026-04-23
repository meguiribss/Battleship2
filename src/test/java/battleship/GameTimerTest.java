package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    @Test
    @DisplayName("Deve criar objeto GameTimer com sucesso")
    void shouldCreateGameTimerSuccessfully() {
        GameTimer timer = new GameTimer();

        assertNotNull(timer);
    }

    @Test
    @DisplayName("Start deve guardar valor de tempo maior que zero")
    void startShouldStoreCurrentTime() throws Exception {
        GameTimer timer = new GameTimer();

        timer.start();

        Field field = GameTimer.class.getDeclaredField("start");
        field.setAccessible(true);

        long value = field.getLong(timer);

        assertTrue(value > 0);
    }

    @Test
    @DisplayName("Start chamado duas vezes deve atualizar tempo")
    void startCalledTwiceShouldUpdateTime() throws Exception {
        GameTimer timer = new GameTimer();

        timer.start();

        Field field = GameTimer.class.getDeclaredField("start");
        field.setAccessible(true);

        long firstValue = field.getLong(timer);

        Thread.sleep(5);

        timer.start();

        long secondValue = field.getLong(timer);

        assertTrue(secondValue >= firstValue);
    }

    @Test
    @DisplayName("Stop após start não deve lançar exceção")
    void stopAfterStartShouldNotThrowException() {
        GameTimer timer = new GameTimer();

        timer.start();

        assertDoesNotThrow(timer::stop);
    }

    @Test
    @DisplayName("Stop sem start também não deve lançar exceção")
    void stopWithoutStartShouldNotThrowException() {
        GameTimer timer = new GameTimer();

        assertDoesNotThrow(timer::stop);
    }

    @Test
    @DisplayName("Start seguido de stop deve funcionar corretamente")
    void startThenStopShouldExecuteCorrectly() {
        GameTimer timer = new GameTimer();

        assertDoesNotThrow(() -> {
            timer.start();
            Thread.sleep(10);
            timer.stop();
        });
    }
}
