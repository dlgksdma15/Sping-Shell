package com.example.demo.common.dataparser;

import com.example.demo.account.dto.Account;
import com.example.demo.price.dto.Price;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "file.type", havingValue = "json")
public class JsonDataParser implements DataParser {

    @Value("${file.account-path-json}")
    private String accountFilePath;

    @Value("${file.price-path-json}")
    private String priceFilePath;

    public List<String> cities() {
        System.out.println("Price json 파서 사용: " + priceFilePath);
        InputStream is = getClass().getResourceAsStream("/" + priceFilePath);

        if(is == null){
            System.err.println("error: resources/" + priceFilePath + " 파일을 찾을 수 없습니다.");
            return Collections.emptyList();
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            // JSON 배열을 List<Price>로 변환
            List<Price> priceList = mapper.readValue(is, new TypeReference<List<Price>>() {});
            return priceList.stream()
                    .map(Price::getCity) // Price DTO에 getCity()가 있다고 가정
                    .distinct()
                    .collect(Collectors.toList());
        } catch (IOException e){
            System.out.println("price.json 파일 로딩 또는 파싱 실패");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> sectors(String city) {
        InputStream is = getClass().getResourceAsStream("/" + priceFilePath);

        if(is == null){
            System.err.println("error: resources/" + priceFilePath + " 파일을 찾을 수 없습니다.");
            return Collections.emptyList();
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            List<Price> priceList = mapper.readValue(is, new TypeReference<List<Price>>() {});
            return priceList.stream()
                    // 1. 필터링: 입력된 city와 Price 객체의 지자체명(getCity)이 일치하는 요소만 통과
                    .filter(price -> price.getCity().equals(city))
                    // 2. 매핑: 남은 요소에서 업종 정보(getSector)만 추출 (예: "일반용", "일반용(미)")
                    .map(Price::getSector)
                    // 3. 중복 제거: 중복된 업종 제거
                    .distinct()
                    // 4. 수집: 최종 결과를 List<String>으로 반환
                    .collect(Collectors.toList());
        } catch (IOException e){
            System.err.println("error: price.json 파일 로딩 또는 파싱 실패");
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public Price price(String city, String sector) {
        InputStream is = getClass().getResourceAsStream("/" + priceFilePath);

        if(is == null){
            System.err.println("error: resources/" + priceFilePath + " 파일을 찾을 수 없습니다.");
            return null;
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            List<Price> priceList = mapper.readValue(is, new TypeReference<List<Price>>() {});
            return priceList.stream().
                     filter(price -> price.getCity().equals(city) && price.getSector().equals(sector))
                    .findFirst()  // 첫 번째 매칭되는 항목 반환
                    .orElse(null);  // 없으면 null 반환
        } catch (IOException e){
            System.err.println("error: price.json 파일 로딩 또는 파싱 실패");
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> accounts() {
        System.out.println("Account json 파서 사용: " + accountFilePath);
        InputStream is = TypeReference.class.getResourceAsStream("/account.json");

        if(is == null){
            System.out.println("json 파일을 찾을 수 없습니다.");
            return Collections.emptyList();
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            // JSON 배열을 List<Account>로 변환
            return mapper.readValue(is, new TypeReference<List<Account>>() {});
        } catch (IOException e){
            System.out.println("account.json 파일 로딩 또는 파싱 실패");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


}
