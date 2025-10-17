package com.example.demo.shell;

import com.example.demo.account.dto.Account;
import com.example.demo.account.service.AuthenticationService;
import com.example.demo.price.dto.Price;
import com.example.demo.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Objects;

@ShellComponent
public class MyCommands {

    private final AuthenticationService authService;
    private final PriceService priceService;

    @Autowired // DI
    public MyCommands(AuthenticationService authService,PriceService priceService) {
        this.authService = authService;
        this.priceService = priceService;
    }


    @ShellMethod("아이디와 비밀번호로 로그인합니다.")
    public String login(long id, String password) {
        Account account = authService.login(id,password);

        if(account != null){
            return account.toString();
        }
        return "id or password not correct";
    }

    @ShellMethod
    public String logout() {
        authService.logout();
        return "good bye";
    }

    @ShellMethod(key = "current-user", value = "현재 로그인된 사용자의 정보를 확인")
    public String currentUser(){
        Account currentAccount = authService.getCurrentAccount();
        if(currentAccount == null){
            return " ";
        }
        return currentAccount.toString();
    }

    @ShellMethod(value = "지차제명 목록 출력")
    public String city(){
        List<String> cities = priceService.cities();

        if(cities.isEmpty()){
//            throw new Exception();
            return "로그인을 하셔야 이용하실 수 있습니다.";
//            return "로드된 지자체 정보가 없습니다.";
        }
        return cities.toString();
    }

    @ShellMethod
    public String sector(String city){
        List<String> sectors = priceService.sectors(city);
        if(sectors.isEmpty()){
//            throw new Exception();
            return "로그인을 하셔야 이용하실 수 있습니다.";
//            return "로드된 업종 정보가 없습니다.";
        }

        return sectors.toString();
    }

    @ShellMethod
    public String price(String city, String sector) {
        Price price = priceService.price(city,sector);
        if(Objects.isNull(price)){
//            throw new Exception();
            return "로그인을 하셔야 이용하실 수 있습니다.";
//            return "로드된 구간 금액 정보가 없습니다.";
        }
        return price.toString();
    }

    @ShellMethod(key = "bill-total", value = "사용자에 따른 금액을 알 수 있는 기능(한/영 지원)")
    public String billTotal(String city, String sector, int usage) {
        String s = priceService.billTotal(city, sector, usage);
        if(Objects.isNull(s)){
            return "로그인을 하셔야 이용하실 수 있습니다.";
        }

        return s;
    }


}