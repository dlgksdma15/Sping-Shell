package com.example.demo;

import com.example.demo.account.dto.Account;
import com.example.demo.account.service.AuthenticationService;
import com.example.demo.common.dataparser.CsvDataParser;
import com.example.demo.common.dataparser.DataParser;
import com.example.demo.common.dataparser.JsonDataParser;
import com.example.demo.price.dto.Price;
import com.example.demo.price.formatter.EnglishOutputFormatter;
import com.example.demo.price.formatter.KoreanOutputFormatter;
import com.example.demo.price.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=json",
        "file.price-path=price.json",
        "file.account-path=account.json"
})
@ExtendWith(MockitoExtension.class)
public class JsonPropertiesTest {

    @Autowired
    DataParser dataParser;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PriceService priceService;

    @BeforeEach
    void setUp(){
        authenticationService.login(1L,"1");
    }

    @Test
    void beanTest() {
        assertInstanceOf(JsonDataParser.class, dataParser);
    }

    @Test
    void loginTest() throws Exception {
        authenticationService.login(1L, "1");
        Account account = authenticationService.getCurrentAccount();
        assertEquals("선도형", account.getName());
    }

    @Test
    void logoutTest() {
        authenticationService.login(1L, "1");
        authenticationService.logout();
        Account account = authenticationService.getCurrentAccount();
        assertNull(account);
    }

    @Test
    void cityTest() {
        List<String> cities = priceService.cities();
        assertEquals(cities.size(), 21);
        assertTrue(cities.contains("동두천시"));
    }

    @Test
    void sectorsTest() {
        List<String> sectors = priceService.sectors("동두천시");
        assertEquals(sectors.size(), 5);
        assertTrue(sectors.contains("가정용"));
    }

    @Test
    void priceTest() {
        Price price = priceService.price("동두천시", "가정용");
        assertEquals("동두천시", price.getCity());
        assertEquals("가정용", price.getSector());
        assertEquals(690, price.getUnitPrice());
    }

    @Test
    void billTotalTest() {
        String billTotal = priceService.billTotal("동두천시", "가정용", 10);
        assertTrue(billTotal.contains("690"));
    }

    @Test
    void OutPutFormatterTest() {
        Price price = priceService.price("동두천시", "가정용");

        KoreanOutputFormatter koreanOutputFormatter = new KoreanOutputFormatter();
        EnglishOutputFormatter englishOutputFormatter = new EnglishOutputFormatter();

        String korean = koreanOutputFormatter.format(price, 10);
        String english = englishOutputFormatter.format(price, 10);

        assertTrue(korean.contains("690"));
        assertTrue(english.contains("690"));
        assertNotEquals(korean, english);
    }
}
