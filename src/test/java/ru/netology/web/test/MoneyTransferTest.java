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
    void shouldTransferMoneyBetweenCardsOfCustomerFromFirstToSecond() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val pageWithTransferInfo = dashboardPage.startTransferFromFirstCard();
        val newDashboardPage = pageWithTransferInfo.makeTransferFromFirstToSecond();

        int actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        int expected = firstCardBalance + getTransferAmount().getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTransferMoneyBetweenCardsOfCustomerFromSecondToFirst() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val secondCardBalance = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        val pageWithTransferInfo = dashboardPage.startTransferFromSecondCard();
        val newDashboardPage = pageWithTransferInfo.makeTransferFromSecondToFirst();

        int actual = dashboardPage.getCardBalance(getSecondCardInfo().getNumber());
        int expected = secondCardBalance + getTransferAmount().getAmount();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotTransferMoneyBetweenCardsOfCustomerFromFirstFirst() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        val pageWithTransferInfo = dashboardPage.startTransferFromFirstCard();
        val newDashboardPage = pageWithTransferInfo.makeTransferFromFirstToFirst();

        int actual = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());
        assertEquals(firstCardBalance, actual);
    }
}