
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GuessNumberIOTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Base correct is returned false.
    // Probably because the value is added by 1 in the program. (.nextInt(100) returns 0-99)
    // This turns out to be true fromn next testNumberMinusOne test
    @Test
    void testCorrectGuess() {
        // Simulate user input
        String simulatedInput = "50\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Mock Random to always return 50
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 50;
            }
        };

        GuessNumber.guessingNumberGame(random);

        // Verify the output contains the expected success message
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));
    }

    @Test
    void testNumberMinusOne() {
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

        // Verify the output contains the expected failure message
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));
    }

    // This returns True
    // Also checks for prompt always saying more than.
    @Test
    void testFiveAttemptsSolved() {
        // Simulate user input
        String simulatedInput = "10\n20\n30\n40\n50\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Mock Random to always return 70
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 49; //
            }
        };

        GuessNumber.guessingNumberGame(random);

        // Verify the output contains the expected failure message
        String output = outputStreamCaptor.toString().trim();
        System.out.println(output);
        assertTrue(output.contains("Congratulations! You guessed the number."));
        assertFalse(output.contains("The number is less than"));
    }


    @Test
    void testGuessExhausted() {

        String simulatedInput = "1\n2\n3\n4\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 79;
            }
        };

        GuessNumber.guessingNumberGame(random);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("You have exhausted 5 trials."));
        assertTrue(output.contains("The number was 80"));
    }

    @Test
    void testCorrectAfterThreeGuesses() {
        String simulatedInput = "1\n2\n3\n4\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 2;
            }
        };
        GuessNumber.guessingNumberGame(random);

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));

    }

    // GuessNumber Throws noSuchElementException for item out of bounds
    @Test
    void testOutOfBounds() {
        String simulatedInput = "0\n1\n2\n3\n4\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Random random = new Random();
        GuessNumber.guessingNumberGame(random);
        assertThrows(NoSuchElementException.class, () -> {
            GuessNumber.guessingNumberGame(random);
        }, "Input must be between 1 and 100");
    }

    @Test
    void testlowerBound() {
        String simulatedInput = "1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        GuessNumber.guessingNumberGame(random);
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));
    }

    @Test
    void testUpperBound() {
        String simulatedInput = "100\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 99;
            }
        };
        GuessNumber.guessingNumberGame(random);
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Congratulations! You guessed the number."));
    }

    @Test
    void testGreaterLessThan() {
        String simulatedInput = "50\n75\n70\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 69;
            }
        };
        GuessNumber.guessingNumberGame(random);
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("The number is less than"));
        assertTrue(output.contains("The number is greater than"));
    }

    @Test
    void testFailedGuess() {
        String simulatedInput = "50\n75\n68\n72\n71\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 69;
            }
        };
        GuessNumber.guessingNumberGame(random);
        String output = outputStreamCaptor.toString().trim();
        System.out.println(output);
        assertTrue(output.contains("You have exhausted 5 trials."));
        assertTrue(output.contains("The number was 70"));
    }
}