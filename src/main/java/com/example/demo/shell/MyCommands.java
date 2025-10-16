package com.example.demo.shell;

import com.example.demo.account.dto.Account;
import com.example.demo.account.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MyCommands {

    private final AuthenticationService authService;

    @Autowired // DI
    public MyCommands(AuthenticationService authService) {
        this.authService = authService;
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

    @ShellMethod
    public String city() {
        return null;
    }

    @ShellMethod
    public String sector(String city) {
        return null;
    }

    @ShellMethod
    public String price(String city, String sector) {
        return null;
    }

    @ShellMethod
    public String billTotal(String city, String sector, int usage) {
        return null;
    }


}