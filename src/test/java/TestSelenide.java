import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestSelenide {

    @BeforeEach
    void setupClass() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendValidValue() {

        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendValidValueDoubleSurname() {

        SelenideElement form = $("[action]");
        form.$("[data-test-id=name] input").setValue("Иванов-Сидоров Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendValidValueWithOutName() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendInvalidName() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Petrov Petr");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendValidNameCapsLK() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("ПЕТРОВ ПЕТЯ ПЕТРОВИЧ");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldBeErrorWhenNoClickCheckbox() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Петров Петр");
        form.$("[data-test-id=phone] input").setValue("+71234567891");
        form.$("[role=button]").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldSendInvalidValueTelNumber() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("ПЕТРОВ ПЕТЯ ПЕТРОВИЧ");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[data-test-id=phone] input").setValue("+71234567");
        form.$("[role=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendValueWithOutTelNumber() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("ПЕТРОВ ПЕТЯ ПЕТРОВИЧ");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}