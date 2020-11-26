package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement firstCardButton = $$("[data-test-id=action-deposit]").first();
    private SelenideElement secondCardButton = $$("[data-test-id=action-deposit]").last();


    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getCardBalance(String number) {
        val text = cards.find(text(number.substring(15, 19))).getText();
        return extractBalance(text);
    }

    public TransferInfoPage startTransferFromFirstCard() {
        firstCardButton.click();
        return new TransferInfoPage();
    }

    public TransferInfoPage startTransferFromSecondCard() {
        secondCardButton.click();
        return new TransferInfoPage();
    }

}
