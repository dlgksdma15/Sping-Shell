package com.example.demo.price.formatter;

import com.example.demo.price.dto.Price;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("eng")
@Component
public class EnglishOutputFormatter implements OutPutFormatter{

    public String format(Price price, int usage) {

        long id = price.getId();
        String city = price.getCity();
        String sector = price.getSector();
        int unitPrice = price.getUnitPrice();
        int total = price.getUnitPrice() * 10;

        String formatted = "city: %s, sector: %s, unit price(won): %d, bill total(won): %d"
                .formatted(city,sector,unitPrice,total);

        return formatted;
    }

}
