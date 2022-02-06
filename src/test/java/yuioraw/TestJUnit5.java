package yuioraw;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class TestJUnit5 {

    @BeforeAll
    static void BeforeAll() {
        Configuration.browserSize = "1920x1080";
    }


    @BeforeEach
    void BeforeEach() {
        open("https://www.kinopoisk.ru/");
    }

    //1
    @CsvSource(value = {
            "Миротворец, Джеймс",
            "Декстер, Джон Дал"
    })
    @ParameterizedTest(name = "Тестирование поиска фильма: {0}")
    void commonSearchTest1(String testData, String expected) {
        $("[type=\"text\"]").setValue(testData).pressEnter();
        $("[class=\"director\"]").shouldHave(Condition.text(expected));
    }

    //2
    static Stream<Arguments> commonSearchTestCsvSource() {
        return Stream.of(
                Arguments.of("Миротворец", "Джеймс"),
                Arguments.of("Декстер", "Джон Дал")
        );
    }

    @MethodSource("commonSearchTestCsvSource")
    @ParameterizedTest(name = "Тестирование поиска фильма: {0}")
    void commonSearchTest2(String testData, String expected) {
        $("[type=\"text\"]").setValue(testData).pressEnter();
        $("[class=\"director\"]").shouldHave(Condition.text(expected));
    }

    //3
    @ValueSource(strings = {"Миротворец", "Декстер"})
    @ParameterizedTest(name = "Тестирование поиска фильма: {0}")
    void commonSearchTest3(String testData) {
        $("[type=\"text\"]").setValue(testData).pressEnter();
        $("[class=\"name\"]").shouldHave(Condition.text(testData));
    }

    @AfterEach
    void AfterEach() {
        // Selenide.closeWebDriver();
        clearBrowserCookies();
    }
}
