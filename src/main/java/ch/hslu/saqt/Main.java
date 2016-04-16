package ch.hslu.saqt;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Choose a random page on Wikipedia and select first link that is not italic and not in braces. Select a link until
 * the page title is Philosophie.
 */
public class Main {

    @Parameter(names = {"--term", "-t"})
    private String term = "https://de.wikipedia.org/wiki/Tee";

    @Parameter(names = {"--max-trials", "-m"})
    private int maxTrials = 10;

    public static void main(String[] args) {
        Main main = new Main();
        new JCommander(main, args);
        main.run();
    }

    private void run() {
        System.out.println("Start bei: " + term + System.lineSeparator());

        Path path = new Path(maxTrials);
        int trials = path.find(term);

        System.out.println();

        if (trials == maxTrials) {
            System.out.println("Es gibt keinen Pfad :(");
        } else {
            System.out.println("Suche erfolgreich abgeschlossen :)");
        }

    }

}
