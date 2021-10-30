import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText.trim());
    }

    @Test
    void shouldSendValidValueDoubleSurname() {
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петров-Сидоров Петр");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText.trim());
    }

    @Test
    void shouldSendValidValueWithOutName() {
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", actualText.trim());
    }

    @Test
    void shouldSendInvalidValueName() {
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Petr Petrov");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText.trim());
    }

    @Test
    void shouldBeErrorWhenNoClickCheckbox() {
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actualText.trim());
    }

    @Test
    void shouldSendValueWithOutTelNumber() {
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петя Иванов");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", actualText.trim());
    }

    @Test
    void shouldSendInvalidValueTelNumber() {
        driver.findElement(By.cssSelector("[action]"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Петя Иванов");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+7999999");
        driver.findElement(cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(cssSelector("[role=button]")).click();
        String actualText = driver.findElement(cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText.trim());
    }
}
