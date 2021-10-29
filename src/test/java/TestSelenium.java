import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.By.cssSelector;


public class TestSelenium {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendValidValue() {
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void shouldSendValidValueDoubleSurname() {
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петров-Сидоров Петр");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void shouldSendInvalidValueName() {
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Petr Petrov");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=name] .input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

    @Test
    void shouldSendValueWithOutTelNumber() {
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петя Иванов");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText.trim());
    }

}
