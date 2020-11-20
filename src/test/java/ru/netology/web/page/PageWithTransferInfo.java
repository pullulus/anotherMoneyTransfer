package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PageWithTransferInfo {
    private SelenideElement headingh1 = $(byText("Пополнение карты"));
    private SelenideElement sumOfTransfer = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement to = $("[data-test-id=to] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public DashboardPage makeTransferFromFirstToSecond() {
        sumOfTransfer.setValue("1000");
        from.setValue("5559 0000 0000 0002");
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage makeTransferFromSecondToFirst() {
        sumOfTransfer.setValue("1000");
        from.setValue("5559 0000 0000 0001");
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage makeTransferFromFirstToFirst() {
        sumOfTransfer.setValue("1000");
        from.setValue("5559 0000 0000 0001");
        transferButton.click();
        return new DashboardPage();
    }

}
