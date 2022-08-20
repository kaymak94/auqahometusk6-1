package ru.netology.pageobject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class TransferPage {
    private SelenideElement amountInput = $("[data-test-id='amount'] input");
    private SelenideElement fromInput = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement transferHead = $("[data-test-id='action-transfer'] ");
    private SelenideElement transferMoneyMoreLimit = $("[data-test-id='error-notification'] ");


    public void toDoTransfer(int value, String cardInfo) {
        amountInput.setValue(Integer.toString(value));
        fromInput.setValue(cardInfo);
        transferButton.click();

    }

    public void getTransferMoneyMoreLimit() {
        transferMoneyMoreLimit.shouldHave(exactText("Не хватает денег для перевода, уменьшите сумму перевода")).shouldBe(visible);


    }


}

