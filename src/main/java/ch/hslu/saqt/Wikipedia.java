package ch.hslu.saqt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Choose a random page on Wikipedia and select first link that is not italic and not in braces. Select a link until
 * the page title is Philosophie.
 */
public class Wikipedia {
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        driver.navigate().to("https://de.wikipedia.org/wiki/Tee");

        String title = driver.getTitle();
        int trials = 0;
        int maxTrials = 10;

        System.out.println("Start bei: " + title + System.lineSeparator());

        while (trials < maxTrials && !title.equals("Philosophie – Wikipedia")) {
            WebElement firstParagraph = driver.findElement(By.cssSelector("#mw-content-text p"));

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

        if (trials ==  maxTrials) {
            System.out.println("Es gibt keinen Pfad :(");
        } else {
            System.out.println("Suche erfolgreich abgeschlossen :)");
        }

        driver.quit();
    }

    private static String removeWrongLinksFromParagraph(WebElement element) {
        String text = element.getAttribute("innerHTML");
        System.out.println(text);
        String textWithoutBraces = Arrays.stream(text.split(" \\(((?!\\)).)*\\) ")).collect(Collectors.joining(" "));
        return textWithoutBraces;
    }
}
