package com.example.demo.service;

import com.example.demo.account.dto.Account;
import com.example.demo.account.service.AuthenticationService;
import com.example.demo.common.dataparser.DataParser;
import com.example.demo.price.dto.Price;
import com.example.demo.price.formatter.OutPutFormatter;
import com.example.demo.price.service.PriceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private DataParser dataParser;

    @Mock
    private OutPutFormatter outPutFormatter;

    @Mock
    private AuthenticationService authenticationService;


    @Spy
    @InjectMocks
    private PriceService priceService;

    @Test
    public void billTotalwithSpyTest() throws Exception{

        when(authenticationService.getCurrentAccount()).thenReturn(new Account(1L, "asdfasdf", "asdfaasdfadsf"));
        //given
        Price fakePrice = new Price(1L,"test-city","test-sector",0);
        doReturn(fakePrice).when(priceService).price("test-city", "test-sector");
        doReturn("formatted-string").when(outPutFormatter).format(any(Price.class), anyInt());

        //when
        String result = priceService.billTotal("test-city","test-sector",50);

        //then
        Assertions.assertThat(result).isEqualTo("formatted-string");

        verify(priceService).price("test-city","test-sector");

        verify(outPutFormatter).format(fakePrice,50);
    }

}
