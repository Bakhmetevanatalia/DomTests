import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;



public class DomTest {
    private static final Logger log = Logger.getLogger(String.valueOf(DomTest.class));
    private final String login = "wasej93407@dni8.com";
    private final String pas = "Natalia12345!";
    protected static WebDriver driver;
    ChromeOptions options = new ChromeOptions();


    @BeforeAll
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }


    @Test
    public void headlessMode(){
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.get("https://duckduckgo.com/");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("ОТУС", Keys.ENTER);
        String element = driver.findElement(By.xpath("//*[@id=\"r1-0\"]/div[2]/h2/a/span")).getText();
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным ...", element);

    }

    @Test
    public void fullScreenMode() throws Exception {
        By image = By.xpath("//img[@src='assets/images/p1.jpg']");
        options.addArguments("--kiosk");
        driver = new ChromeDriver(options);
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView()", driver.findElement(image));
        js.executeScript("arguments[0].click()", driver.findElement(image));
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='pp_hoverContainer']"))));

    }

    @Test
    public void maximizeTest(){
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        loginInOtus();
        log.info(driver.manage().getCookies().toString()) ;
    }

    private void loginInOtus(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".header3__button-sign-in"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearAndEnter(By.xpath("//input[@type='text' and contains (@placeholder, 'Электронная почта')]"), login);
        clearAndEnter(By.xpath("//input[@type='password' and contains (@placeholder, 'Введите пароль')]"), pas);
        driver.findElement(By.xpath("//*[normalize-space(text()) = 'Войти' and @type='submit']")).submit();
     }

    private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }
    @AfterEach
    public void close(){
        if (driver != null)
            driver.quit();
    }

}
