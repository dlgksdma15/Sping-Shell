package com.example.demo.common.dataparser;

import com.example.demo.account.dto.Account;
import com.example.demo.price.dto.Price;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnProperty(name = "file.type", havingValue = "csv")
public class CsvDataParser implements DataParser{

    @Value("${file.account-path}")
    private String accountFilePath;

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
        System.out.println("csv 파서 사용: " + accountFilePath);

        InputStream is = getClass().getResourceAsStream("/" + accountFilePath); // account.csv

        if(is == null){
            System.err.println("error: resources/" + accountFilePath + " 파일을 찾을 수 없습니다.");
            return Collections.emptyList();
        }

        // InputStream을 사용해서 파싱 로직을 실행
        try(Reader reader = new InputStreamReader(is)) {

            // OpenCSV를 사용한다고 가정하고 파싱 로직을 바로 이 위치에 넣습니다.
            // CsvToBeanBuilder, HeaderColumnNameMappingStrategy 등을 사용하여 List<Account>를 생성합니다.
            HeaderColumnNameMappingStrategy<Account> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Account.class);

            CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader)
                    .withType(Account.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse(); // 파싱된 리스트 반환

        } catch(Exception e){
            System.err.println("error: CSV 파일 파싱 중 예외 발생");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


}
