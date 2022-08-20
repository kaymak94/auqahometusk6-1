package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pageobject.DashboardPage;
import ru.netology.pageobject.LoginPage;
import ru.netology.pageobject.TransferPage;

import static com.codeborne.selenide.Selenide.open;

public class TransferMoneyTests {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {

        int value = 10;
        String cardNumber = DataHelper.getFirstCardNumber().getCardNumber();
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferToSecondButton();
        val transferPage = new TransferPage();
        transferPage.toDoTransfer(value, cardNumber);
        var firstCardBalanceNew = dashboardPage.getFirstCardBalance();
        var secondCardBalanceNew = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(secondCardBalance - value, secondCardBalanceNew);
        Assertions.assertEquals(firstCardBalance + value, firstCardBalanceNew);
    }



    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {

        int value = 600;
        String cardNumber = DataHelper.getSecondCardNumber().getCardNumber();
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferToFirstButton();
        val transferPage = new TransferPage();
        transferPage.toDoTransfer(value, cardNumber);
        var firstCardBalanceNew = dashboardPage.getFirstCardBalance();
        var secondCardBalanceNew = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalance - value, firstCardBalanceNew);
        Assertions.assertEquals(secondCardBalance + value, secondCardBalanceNew);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCardMoreLimit() {

        int value = 11_000;
        String cardNumber = DataHelper.getSecondCardNumber().getCardNumber();
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferToFirstButton();
        val transferPage = new TransferPage();
        transferPage.toDoTransfer(secondCardBalance + value, cardNumber);
        transferPage.getTransferMoneyMoreLimit();
        Assertions.assertEquals(firstCardBalance, dashboardPage.getFirstCardBalance());
        Assertions.assertEquals(secondCardBalance, dashboardPage.getSecondCardBalance());

    }
}
