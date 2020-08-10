package ru.open;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyStepDef extends Steps {
    private String selectorExchangeRates = "//*[@class='main-page-exchange main-page-info__card']";
    private String selectorTableHeaders = ".//tbody/tr[contains(@class,'header')]/td";
    private String selectorTableRows = ".//tbody/tr[contains(@class,'row')]";

    private WebElement exchangeRates;

    private List<Map<String,String>> collectExchangeRates = new ArrayList<>();

    @Given("перейти на сайт '(.*)'")
    public void перейти_на_сайт(String url) {
        открытьХром();
        chromeDriver.get(url);
    }

    @Then("тайтл равен '(.*)'")
    public void тайтл_равен(String titleName) {
        Assert.assertTrue(chromeDriver.getTitle().contains(titleName));

    }

    @Then("проверить курс '(.*)'")
    public void проверить_курс(String moneyType) throws InterruptedException {
        exchangeRates = chromeDriver.findElement(By.xpath(selectorExchangeRates));
        List<WebElement> tableHeaders = exchangeRates.findElements(By.xpath(selectorTableHeaders));
        List<WebElement> tableRows = exchangeRates.findElements(By.xpath(selectorTableRows));
        for (int i = 0; i < tableRows.size(); ++i) {
            Map<String, String> collectRow = new HashMap<>();
            for (int j = 0; j < tableHeaders.size(); ++j) {
                collectRow.put(
                        tableHeaders.get(j).getText(),
                        tableRows.get(i).findElement(By.xpath("./td[" + (j + 1) + "]")).getText()
                );
            }
            collectExchangeRates.add(collectRow);
        }
        System.out.println(collectExchangeRates);
        Assert.assertTrue(
                Double.valueOf(
                        collectExchangeRates.stream()
                                .filter(x -> x.get("Валюта обмена").equals(moneyType))
                                .findFirst()
                                .get().get("Банк покупает").replace(",", "."))
                        <
                        Double.valueOf(
                                collectExchangeRates.stream()
                                        .filter(x -> x.get("Валюта обмена").equals(moneyType))
                                        .findFirst()
                                        .get().get("Банк продает").replace(",", "."))
        );
    }

    @Then("закончить работу")
    public void закончить_работу() {
        закрытьХром();
    }
}