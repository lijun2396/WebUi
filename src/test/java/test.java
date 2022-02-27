import function.BaseDriver;
import function.BaseTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.PropertiesReader;

import java.io.File;
import java.util.ResourceBundle;

public class test extends BaseTest{
    private static Logger log = Logger.getLogger(test.class);
    @BeforeClass
    public void  before(){
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        chromeOptions.addArguments("disable-infobars");
        // --no-sandbox
//        chromeOptions.addArguments("--no-sandbox");
    }
    @Test
    public void fun1() throws InterruptedException {
        log.info("启动chrome");
        String driverParentPath = this.getClass().getResource("/").getPath() + "driver" + File.separator;
        System.out.println(driverParentPath);
        String driver=PropertiesReader.readProperties().get("chromeDriver");
        System.setProperty("webdriver.chrome.driver",driverParentPath+driver);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        chromeOptions.addArguments("disable-infobars");
        // --no-sandbox
        chromeOptions.addArguments("--no-sandbox");

        WebDriver dr=new ChromeDriver(chromeOptions);
        dr.get("https://www.baidu.com");
        dr.manage().window().maximize();
        dr.findElement(By.id("su")).submit();
        Thread.sleep(3000);
        dr.quit();
    }
    @Test
    public void fun2() throws InterruptedException {
        WebDriver dr;
        BaseDriver bd= new BaseDriver();
        dr=bd.startBrowser();
        dr.get("https://www.baidu.com");
        dr.manage().window().maximize();
        dr.findElement(By.id("su")).submit();
        Thread.sleep(3000);
        dr.quit();
    }
    @Test
    public void fun3() throws InterruptedException {
        driver.get("https://www.baidu.com");
        driver.manage().window().maximize();
        driver.findElement(By.id("su")).submit();
        Thread.sleep(3000);

    }
}
