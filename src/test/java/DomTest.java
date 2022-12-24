import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.logging.Logger;



public class DomTest extends BaseTest{
    private static final Logger log = Logger.getLogger(String.valueOf(DomTest.class));
    private String login = "wasej93407@dni8.com";
    private String pas = "Natalia12345!";


    @Test
    public void headlessMode(){
    // chooseMode("headless");
    // Открыть Chrome в headless режиме
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
    // Перейти на https://duckduckgo.com/
        driver.get("https://duckduckgo.com/");
    // В поисковую строку ввести ОТУС
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("ОТУС", Keys.ENTER);
    // Проверить что в поисковой выдаче первый результат Онлайн‑курсы для профессионалов, дистанционное обучение
        String element = driver.findElement(By.xpath("//*[@id=\"r1-0\"]/div[2]/h2/a/span")).getText();
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", element);
    //  log.info("Ожидаемый результат: Онлайн‑курсы для профессионалов, дистанционное обучение современным ..."+ "\n Фактический результат: " +element);

    }


    @Test
    public void fullScreenMode() throws InterruptedException {
        By image = By.xpath("//img[@src='assets/images/p1.jpg']");
    //  Открыть Chrome в режиме киоска
        driver.manage().window().fullscreen();
    //  Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
    //  Нажать на любую картинку
    //  wait.until(ExpectedConditions.invisibilityOfElementLocated(image));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView()", driver.findElement(image));
        js.executeScript("arguments[0].click()", driver.findElement(image));
    //  Проверить что картинка открылась в модальном окне
        Thread.sleep(2000);
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class='pp_hoverContainer']")).isDisplayed());
    }


    @Test
    public void maximizeTest(){
    //  Открыть https://otus.ru
        driver.quit();
        driver = new ChromeDriver();
    //  Не киоск
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
    //  Авторизоваться на сайте
        loginInOtus();
    //  Вывести в лог все cookie
        log.info(driver.manage().getCookies().toString()) ;
    }

    private void loginInOtus(){
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearAndEnter(By.xpath("//*[@class='new-log-reg__form js-login']/descendant::*[@placeholder='Электронная почта']"), login);
        clearAndEnter(By.xpath("//*[@class='new-log-reg__form js-login']/descendant::*[@placeholder='Введите пароль']"), pas);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

}
