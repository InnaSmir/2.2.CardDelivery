package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final int random_num = (int) (Math.random() * 15);
    LocalDate date = LocalDate.now();
    LocalDate bookingDate = date.plusDays(3 + random_num);

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:7777/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79210000000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Успешно!")).waitUntil(visible, 11000);
    }

    @Test
    void shouldNotSubmitIfCityIsWrong(){
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Кондопога");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79210010101");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitIfBookingIsBeforeThreeDays(){
        open("http://localhost:7777/");
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys("10.08.2020");
        $("[data-test-id='name'] input").setValue("Иван Петров");
        $("[data-test-id='phone'] input").setValue("+79211111111");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(" [data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotSubmitIfEnglishName(){
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79210000101");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(" [data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия " +
                "указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitIfPhoneIsWrong(){
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Саратов");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Иван Сидоров");
        $("[data-test-id='phone'] input").setValue("+792100000000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(" [data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. " +
                "Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitIfCheckboxUnchecked() {
        open("http://localhost:7777");
        $("[data-test-id='city'] input").setValue("Новгород");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Петр Иванов");
        $("[data-test-id='phone'] input").setValue("+79210020202");
        $(".button").click();
        $(".input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки " +
                "и использования моих персональных данных"));
    }
}
