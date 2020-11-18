package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCardInfo = $(withText("**** **** **** 0001"));
    private SelenideElement secondCardInfo = $(withText("**** **** **** 0002"));
    private SelenideElement firstCardButton = $$("[data-test-id=action-deposit]").first();

    public DashboardPage() {
        heading.shouldBe(visible);
        firstCardInfo.shouldBe(visible);
        secondCardInfo.shouldBe(visible);
    }

    public PageWithTransferInfo startTransfer() {
    firstCardButton.click();
    return new PageWithTransferInfo();
   }

   public DashboardPage showResultOfTheTransfer() {
        firstCardInfo.shouldHave(text(", баланс"));
        return new DashboardPage();
   }
}
