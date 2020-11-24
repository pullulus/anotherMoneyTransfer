package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenCardsOfCustomerFromSecondToFirst() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val secondCartBalance = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        val pageWithTransferInfo = dashboardPage.startTransferFromFirstCard();
        val newDashboardPage = pageWithTransferInfo.makeTransfer("1000", "5559 0000 0000 0002" );

        int actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        int expected = firstCardBalance + 1000;
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        int expected2 = secondCartBalance - 1000;
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneyBetweenCardsOfCustomerFromFirstToSecond() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val secondCardBalance = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        val pageWithTransferInfo = dashboardPage.startTransferFromSecondCard();
        val newDashboardPage = pageWithTransferInfo.makeTransfer("2000", "5559 0000 0000 0001" );

        int actual = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        int expected = secondCardBalance + 2000;
        assertEquals(expected, actual);
        int actual2 = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        int expected2 = firstCardBalance - 2000;
        assertEquals(expected2,actual2);
    }

    @Test
    void shouldNotTransferMoneyBetweenCardsOfCustomerFromFirstToFirst() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val pageWithTransferInfo = dashboardPage.startTransferFromFirstCard();
        val newDashboardPage = pageWithTransferInfo.makeTransfer("3000", "5559 0000 0000 0001" );

        int actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        assertEquals(firstCardBalance, actual);
    }

    @Test
    void shouldNotTransferAmountOverBalanceBetweenCardsOfCustomer() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val secondCartBalance = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        val bigAmount = secondCartBalance + 1000;
        val pageWithTransferInfo = dashboardPage.startTransferFromFirstCard();
        val newDashboardPage = pageWithTransferInfo.makeTransfer("bigAmount", "5559 0000 0000 0002" );

        int actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        int expected = firstCardBalance + bigAmount;
        assertEquals(expected, actual);
    }
}