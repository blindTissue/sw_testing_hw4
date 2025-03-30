
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class GuessNumberRandomnessTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // To stop the output from being printed to the console
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testRandomNumberDistribution() {
        int[] frequency = new int[100];
        int trials = 30000;
        Random random = new Random(0);

        for (int i = 0; i < trials; i++) {
            String simulatedInput = "3\n3\n3\n3\n3\n";
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            int randomNumber = GuessNumber.guessingNumberGame(random);
            frequency[randomNumber-1]++;

        }
//        for (int i = 0; i < frequency.length; i++) {
//            System.out.println("Frequency of " + (i + 1) + ": " + frequency[i]);
//        }
        int minFrequency = Integer.MAX_VALUE;
        int maxFrequency = Integer.MIN_VALUE;
        for (int freq : frequency) {
            if (freq < minFrequency) {
                minFrequency = freq;
            }
            if (freq > maxFrequency) {
                maxFrequency = freq;
            }
        }
        int maxPossibleOut = (int) (minFrequency * 1.5);
        int minPossibleOut = (int) (maxFrequency * 0.5);
        for (int freq: frequency) {
            assertTrue(freq >= minPossibleOut && freq <= maxPossibleOut,
                    "Frequency of number " + (freq + 1) + " is out of the expected range.");
        }
    }
//    @Test
//    void testRandomNumberDistribution() {
//        int[] frequency = new int[100];
//        int trials = 30000;
//        String simulatedInput = "50\n50\n50\n50\n50\n"; // Provide enough input for each trial
//
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        Random random = new Random(0); // Fix the seed value at 0
//
//        for (int i = 0; i < trials; i++) {
//            int randomNumber = GuessNumber.guessingNumberGame(random);
//            frequency[randomNumber - 1]++;
//        }
//
//        // Calculate the minimum and maximum frequency
//        int minFrequency = Integer.MAX_VALUE;
//        int maxFrequency = Integer.MIN_VALUE;
//
//        for (int freq : frequency) {
//            if (freq < minFrequency) {
//                minFrequency = freq;
//            }
//            if (freq > maxFrequency) {
//                maxFrequency = freq;
//            }
//        }
//
//        // Verify that the frequencies are within +50%/-50% of each other
//        for (int freq : frequency) {
//            assertTrue(freq >= minFrequency * 0.5 && freq <= maxFrequency * 1.5,
//                    "Frequency of number " + (freq + 1) + " is out of the expected range.");
//        }
//    }

}
