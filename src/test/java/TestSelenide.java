import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestSelenide {
    @Test
    void shouldSendValidValue() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendValidValueDoubleSurname() {
        open("http://localhost:9999/");
        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Иванов-Сидоров Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendValidValueWithOutName() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendInvalidName() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Petrov Petr");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendValidName2() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("ПЕТРОВ ПЕТЯ ПЕТРОВИЧ");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendInvalidNameColorOfText() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Petrov Petr");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $(".checkbox__text").shouldHave(Condition.cssValue("color", "rgba(11, 31, 53, 0.95)"));
    }

    @Test
    void shouldSendInvalidValueTelNumber() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("ПЕТРОВ ПЕТЯ ПЕТРОВИЧ");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[data-test-id=phone] input").setValue("+71234567");
        form.$("[role=button]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendValueWithOutTelNumber() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("ПЕТРОВ ПЕТЯ ПЕТРОВИЧ");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}