package ch.hslu.saqt;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Choose a random page on Wikipedia and select first link that is not italic and not in braces. Select a link until
 * the page title is Philosophie.
 */
public class Main {

    @Parameter(names = {"--url", "-u"})
    private String url = "https://de.wikipedia.org/wiki/Tee";

    @Parameter(names = {"--trials", "-t"})
    private int maxTrials = 10;

    public static void main(String[] args) {
        Main main = new Main();
        new JCommander(main, args);
        main.run();
    }

    private void run() {
        WebDriver driver = new FirefoxDriver();

        driver.navigate().to(url);

        String title = driver.getTitle();
        int trials = 0;

        System.out.println("Start bei: " + title + System.lineSeparator());

        while (trials < maxTrials && !title.equals("Philosophie – Wikipedia")) {
            WebElement firstParagraph = driver.findElement(By.cssSelector("#mw-content-text > p"));

            String paragraphText = removeWrongLinksFromParagraph(firstParagraph);

            List<WebElement> links = firstParagraph.findElements(By.tagName("a"));

            links.stream()
                    .filter(link -> paragraphText.contains(link.getText()))
                    .findFirst()
                    .ifPresent(WebElement::click);

            title = driver.getTitle();
            trials++;
            System.out.println("Versuch " + trials + " von " + maxTrials + ": " + title);
        }

        System.out.println();

        if (trials == maxTrials) {
            System.out.println("Es gibt keinen Pfad :(");
        } else {
            System.out.println("Suche erfolgreich abgeschlossen :)");
        }

        driver.quit();
    }

    private String removeWrongLinksFromParagraph(WebElement element) {
        return Arrays.stream(element.getText().split(" \\(((?!\\)).)*\\) ")).collect(Collectors.joining(" "));
    }
}
