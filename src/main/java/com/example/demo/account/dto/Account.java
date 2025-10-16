package com.example.demo.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class Account {
    @JsonProperty("아이디")
    @CsvBindByName(column = "아이디")
    long id;
    @JsonProperty("비밀번호")
    @CsvBindByName(column = "비밀번호")
    String password;
    @JsonProperty("이름")
    @CsvBindByName(column = "이름")
    String name;

    // 기본 생성자 필요 (Jackson에서 역직렬화할 때 사용)
    public Account() {}

    // AuthenticationService 에서 Mock 데이터를 만들 때 사용
    public Account(long id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
    // 공백 제거
    public void setPassword(String password) {
        this.password = (password == null) ? null : password.trim();
    }

    public void setName(String name) {
        this.name = (name == null) ? null : name.trim();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
