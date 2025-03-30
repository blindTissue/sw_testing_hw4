import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuessNumberIOTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testCorrectGuess() {
        // Simulate user input
        String simulatedInput = "50\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Mock Random to always return 50
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 49; // 50 - 1 because nextInt(100) returns 0-99
            }
        };

        GuessNumber.guessingNumberGame(random);

        // Verify the output contains the expected success message
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));
    }

    @Test
    void testIncorrectGuess() {
        // Simulate user input
        String simulatedInput = "30\n40\n50\n60\n70\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Mock Random to always return 80
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 79; // 80 - 1 because nextInt(100) returns 0-99
            }
        };

        GuessNumber.guessingNumberGame(random);

        // Verify the output contains the expected failure message
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("You have exhausted 5 trials."));
        assertTrue(output.contains("The number was 80"));
    }

    @Test
    void testBoundaryValues() {
        // Simulate user input
        String simulatedInput = "1\n100\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Mock Random to always return 1
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0; // 1 - 1 because nextInt(100) returns 0-99
            }
        };

        GuessNumber.guessingNumberGame(random);

        // Verify the output contains the expected success message for the lower boundary
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));

        // Reset the output stream
        outputStreamCaptor.reset();

        // Mock Random to always return 100
        random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 99; // 100 - 1 because nextInt(100) returns 0-99
            }
        };

        GuessNumber.guessingNumberGame(random);

        // Verify the output contains the expected success message for the upper boundary
        output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));
    }
}