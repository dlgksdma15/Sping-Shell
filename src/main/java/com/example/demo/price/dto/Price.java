package com.example.demo.price.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    @JsonProperty("순번")
    @CsvBindByName(column = "순번")
    long id;
    @JsonProperty("지자체명")
    @CsvBindByName(column = "지자체명")
    String city;
    @JsonProperty("업종")
    @CsvBindByName(column = "업종")
    String sector;
    @JsonProperty("구간금액(원)")
    @CsvBindByName(column = "구간금액(원)")
    int unitPrice;

    // Jackson 역질렬화를 위한 기본 생성자
    public Price() {
    }

    public Price(long id, String city, String sector, int unitPrice) {
        this.id = id;
        this.city = city;
        this.sector = sector;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", sector='" + sector + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
