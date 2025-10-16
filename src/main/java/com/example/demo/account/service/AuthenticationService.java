package com.example.demo.account.service;

import com.example.demo.account.dto.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthenticationService {
    // 현재 로그인된 계정 정보를 저장
    private Account currentAccount;

    private Map<Long, Account> accounts;

    public AuthenticationService(){
        loadAccount();
    }

    public void loadAccount() {
        System.out.println("account.json 파일 로드 시도");
        InputStream is = TypeReference.class.getResourceAsStream("/account.json");

        if(is == null){
            System.out.println("error: resources/account.json 파일을 찾을 수 없습니다.");
            this.accounts = Collections.emptyMap();
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            // JSON 배열 -> List<Account>
            List<Account> accountList = mapper.readValue(is, new TypeReference<List<Account>>() {});

            // 검색 효율을 위해 List를 Map<ID, Account> 형태로 변환
            this.accounts = accountList.stream()
                    .collect(Collectors.toMap(Account::getId, account -> account));

            System.out.println("Account 데이터 " + this.accounts.size() + "건 로드 완료");
        } catch (IOException e){
            System.out.println("error: account.json 파일 로딩 또는 파싱 실패");
            e.printStackTrace();
            this.accounts = Collections.emptyMap();
        }

    }

    public Account login(Long id, String password) {
        if(accounts == null){
            return null;
        }
        Account foundAccount = accounts.get(id);

        if(foundAccount != null && foundAccount.getPassword().equals(password)){
            this.currentAccount = foundAccount;
            return foundAccount;
        }
        return null;
    }

    public void logout() {
        this.currentAccount = null;
    }

    public Account getCurrentAccount(){

        return this.currentAccount;
    }


}
