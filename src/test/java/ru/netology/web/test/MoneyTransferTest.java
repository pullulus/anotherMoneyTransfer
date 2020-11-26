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
        val transferInfoPage = dashboardPage.startTransferFromFirstCard();
        val amount = 1000;
        val newDashboardPage = transferInfoPage.makeTransfer(String.valueOf(amount), getSecondCardInfo().getNumber());

        val actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val expected = firstCardBalance + 1000;
        assertEquals(expected, actual);
        val actual2 = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        val expected2 = secondCartBalance - 1000;
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
        val transferInfoPage = dashboardPage.startTransferFromSecondCard();
        val amount = 2000;
        val newDashboardPage = transferInfoPage.makeTransfer(String.valueOf(amount), getFirstCardInfo().getNumber());

        val actual = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        val expected = secondCardBalance + 2000;
        assertEquals(expected, actual);
        val actual2 = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val expected2 = firstCardBalance - 2000;
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldNotTransferMoneyBetweenCardsOfCustomerFromFirstToFirst() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val transferInfoPage = dashboardPage.startTransferFromFirstCard();
        val amount = 3000;
        val newDashboardPage = transferInfoPage.makeTransfer(String.valueOf(amount), getFirstCardInfo().getNumber());

        val actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
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
        val transferInfoPage = dashboardPage.startTransferFromFirstCard();
        val bigAmount = Math.abs(secondCartBalance) + 1000;
        val newDashboardPage = transferInfoPage.makeTransfer(String.valueOf(bigAmount), getSecondCardInfo().getNumber());

        transferInfoPage.getErrorMessage();
        val actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        assertEquals(firstCardBalance, actual);
        val actual2 = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        assertEquals(secondCartBalance, actual2);
    }

}