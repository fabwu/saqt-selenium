package ch.hslu.saqt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A path to philosophy.
 */
public class WikipediaTester {

    private static final String BASE_URL = "https://de.wikipedia.org/wiki/";

    private final WebDriver driver;
    private final int maxTrials;

    public WikipediaTester(int maxTrials) {
        this.maxTrials = maxTrials;
        driver = new FirefoxDriver();
    }

    /**
     * Find a path to philosophy.
     *
     * @param term Start term
     * @return How many tries it takes to get to philosophy.
     */
    public Result find(String term) {
        String url = BASE_URL + term;
        driver.navigate().to(url);

        String title = driver.getTitle();
        int trials = 0;

        while (trials < maxTrials && !title.equals("Philosophie – Wikipedia")) {
            WebElement firstParagraph = driver.findElement(By.cssSelector("#mw-content-text > p"));

            String paragraphText = removeWrongLinksFromParagraph(firstParagraph);

            List<WebElement> links = firstParagraph.findElements(By.cssSelector("p > a"));

            links.stream()
                    .filter(link -> paragraphText.contains(link.getText()))
                    .findFirst()
                    .ifPresent(WebElement::click);

            title = driver.getTitle();
            trials++;
            System.out.println("Versuch " + trials + " von " + maxTrials + ": " + title);
        }

        return new Result(term, trials, trials < maxTrials);
    }

    public void finish() {
        driver.quit();
    }

    private String removeWrongLinksFromParagraph(WebElement element) {
        return Arrays.stream(element.getText().split(" \\(((?!\\)).)*\\) ")).collect(Collectors.joining(" "));
    }
}
