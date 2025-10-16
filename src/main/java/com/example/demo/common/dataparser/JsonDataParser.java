package com.example.demo.common.dataparser;

import com.example.demo.account.dto.Account;
import com.example.demo.price.dto.Price;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Component
public class JsonDataParser implements DataParser {


    public List<String> cities() {
        return null;
    }

    public List<String> sectors(String city) {
        return null;
    }

    public Price price(String city, String sector) {
        return null;
    }

    public List<Account> accounts() {
        InputStream is = TypeReference.class.getResourceAsStream("/account.json");

        if(is == null){
            System.out.println("resources/account.json 파일을 찾을 수 없습니다.");
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
