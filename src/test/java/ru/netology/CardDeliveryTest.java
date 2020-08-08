package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final int random_num = (int) (Math.random() * 15);
    LocalDate date = LocalDate.now();
    LocalDate bookingDate = date.plusDays(3 + random_num);

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79210000000");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Успешно!")).waitUntil(visible, 12000);

    }
}
