package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.getFirstCardInfo;

public class MoneyTransferTest {

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenCardsOfCustomerV1() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.assertDashboardPage();
       val firstCardBalance = dashboardPage.getCardBalance(getFirstCardInfo().getNumber());

    }
}