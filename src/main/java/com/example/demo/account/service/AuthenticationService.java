package com.example.demo.account.service;

import com.example.demo.account.dto.Account;
import com.example.demo.common.dataparser.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthenticationService {
    // 현재 로그인된 계정 정보를 저장
    private Account currentAccount;
    private Map<Long, Account> accounts;

    @Autowired
    public AuthenticationService(DataParser jsonDataParser){
        try{
            this.accounts = jsonDataParser.accounts().stream()
                    .collect(Collectors.toMap(Account::getId, Function.identity()));

            System.out.println("AuthenticationService 초기화, " + this.accounts.size() + "로드 완료");
        }  catch (Exception e){
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
