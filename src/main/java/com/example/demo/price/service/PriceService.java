package com.example.demo.price.service;

import com.example.demo.account.dto.Account;
import com.example.demo.account.service.AuthenticationService;
import com.example.demo.common.dataparser.DataParser;
import com.example.demo.price.dto.Price;
import com.example.demo.price.formatter.EnglishOutputFormatter;
import com.example.demo.price.formatter.KoreanOutputFormatter;
import com.example.demo.price.formatter.OutPutFormatter;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class PriceService {

    private DataParser dataParser;

    private List<String> cityList;

    private OutPutFormatter outPutFormatter;

    private AuthenticationService authenticationService;

    @Autowired
    public PriceService(DataParser dataParser, OutPutFormatter outPutFormatter, AuthenticationService authenticationService) {
        this.dataParser = dataParser;
        this.outPutFormatter = outPutFormatter;
        this.authenticationService = authenticationService;

        try{
            this.cityList = dataParser.cities();

            System.out.println("PriceService 초기화 완료." + this.cityList.size() + "건 로드");
        } catch (Exception e){
            System.err.println("error: 초기 데이터 로딩 실패");
            e.printStackTrace();
            // 데이터 로드 실패 시 빈 리스트로 초기화
            this.cityList = Collections.emptyList();
        }
    }

    public List<String> cities() {
        Account currentAccount = authenticationService.getCurrentAccount();
        if(currentAccount == null){
            return Collections.emptyList();
        }

        return this.cityList;
    }

    public List<String> sectors(String city) {
        Account currentAccount = authenticationService.getCurrentAccount();
        if(currentAccount == null){
            return Collections.emptyList();
        }
        if(city == null){
            return null;
        }

        return dataParser.sectors(city);
    }

    public Price price(String city, String sector) {
        Account currentAccount = authenticationService.getCurrentAccount();
        if(currentAccount == null){
            return null;
        }
        if(city == null || sector == null){
            return null;
        }
        return dataParser.price(city,sector);
    }

    public String billTotal(String city, String sector, int usage) {
        Account currentAccount = authenticationService.getCurrentAccount();
        if(currentAccount == null){
            return null;
        }
        if(city == null || sector == null){
            return null;
        }

//        Price price = dataParser.price(city, sector);
        Price price = this.price(city, sector);
        // ================== 디버깅 코드 추가 ==================
        System.out.println("### DEBUG: price 객체 확인: " + price);

        String format = outPutFormatter.format(price, usage);

        return format;
    }

}
