package com.example.demo.price.formatter;

import com.example.demo.price.dto.Price;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("kor")
@Component
public class KoreanOutputFormatter implements OutPutFormatter{

    public String format(Price price, int usage) {

        long id = price.getId();
        String city = price.getCity();
        String sector = price.getSector();
        int unitPrice = price.getUnitPrice();
        int total = price.getUnitPrice() * 10;

        String formatted = "지자체명: %s, 업종: %s, 구간금액(원): %d, 총금액(원): %d"
                .formatted(city,sector,unitPrice,total);

        return formatted;
    }
}
