

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class GuessNumberRandomnessTest {

    @Test
    void testRandomNumberDistribution() {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
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


}
