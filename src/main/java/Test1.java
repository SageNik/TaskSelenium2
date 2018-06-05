import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by Ник on 05.06.2018.
 */
public class Test1 {


    public static void main(String[] args) throws InterruptedException {

        EventFiringWebDriver webDriver =
                new EventFiringWebDriver(initChromeDriver());
        webDriver.register(new WebDriverLogger());

        webDriver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        WebElement emailField = webDriver.findElement(By.id("email"));
        emailField.sendKeys("webinar.test@gmail.com");
        WebElement passwordField = webDriver.findElement(By.id("passwd"));
        passwordField.sendKeys("Xcg7299bnSmMuRLp9ITw");
        WebElement submitButton = webDriver.findElement(By.name("submitLogin"));
        submitButton.click();

        (new WebDriverWait(webDriver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("subtab-AdminCatalog") ));

        WebElement mainMenuCatalog = webDriver.findElement(By.id("subtab-AdminCatalog"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(mainMenuCatalog).build().perform();

        WebElement subMenuCategory = webDriver.findElement(By.id("subtab-AdminCategories"));
        subMenuCategory.click();

                (new WebDriverWait(webDriver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("page-header-desc-category-new_category") ));
        WebElement addCategory = webDriver.findElement(By.id("page-header-desc-category-new_category"));
        addCategory.click();

                (new WebDriverWait(webDriver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("name_1") ));
        WebElement nameCategoryField = webDriver.findElement(By.id("name_1"));
        nameCategoryField.sendKeys("Tricks");
        WebElement btnSaveCategory = webDriver.findElement(By.id("category_form_submit_btn"));
        btnSaveCategory.click();
        WebElement alertSuccessCreated = webDriver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/div"));
        String text = alertSuccessCreated.getText().trim();
        if(text.contains("Создано")) System.out.println("New Category has been created");
        else System.out.println("New Category hasn't been created");

        WebElement findCategoryNameField = webDriver.findElement(By.name("categoryFilter_name"));
        findCategoryNameField.sendKeys("Tricks");
        WebElement btnSearchCategory = webDriver.findElement(By.id("submitFilterButtoncategory"));
        btnSearchCategory.click();

        (new WebDriverWait(webDriver,10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"table-category\"]") ));

        List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"table-category\"]/tbody/*[@class=\" odd\"]"));
        if(elements.isEmpty()) System.out.println("New Category hasn't been added to category list");
        else System.out.println("New Category has been added to category list");

        webDriver.close();
        webDriver.quit();
    }

    public static WebDriver initChromeDriver(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        return new ChromeDriver();
    }
}
