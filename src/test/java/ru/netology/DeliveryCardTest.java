package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final int random_num = (int) (Math.random() * 15);
    LocalDate date = LocalDate.now();
    LocalDate bookingDate = date.plusDays(7 + random_num);


    @Test
    void shouldSearchCityByTwoLetters(){
        open("http://localhost:7777");
        $("[data-test-id=city] input").sendKeys("Мо");
        $$(".menu-item").find(exactText("Москва")).click();
        $(".input__icon").click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(dateFormat.format(bookingDate));
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79210000101");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(".button").click();
        $(withText("Успешно!")).waitUntil(visible, 15000);
    }
}
