package ch.hslu.saqt;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Choose a random page on Wikipedia and select first link that is not italic and not in braces. Select a link until
 * the page title is Philosophie.
 */
public class Main {

    @Parameter(names = {"--max-trials", "-m"})
    private int maxTrials = 10;

    @Parameter(names = {"--file", "-f"}, validateWith = FilePathValidator.class, required = true)
    private String filePath = "";

    public static void main(String[] args) {
        Main main = new Main();
        new JCommander(main, args);
        main.run();
    }

    private void run() {
        Path path = Paths.get(filePath);
        try (Stream<String> stream = Files.lines(path)) {

            WikipediaTester wikipediaTester = new WikipediaTester(maxTrials);

            String resultText = stream
                    .map(this::extractTerm)
                    .map(wikipediaTester::find)
                    .map(Result::toString)
                    .collect(Collectors.joining(System.lineSeparator()));

            wikipediaTester.finish();

            Files.write(path, resultText.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractTerm(String line) {
        return line.split(";")[0];
    }

}
