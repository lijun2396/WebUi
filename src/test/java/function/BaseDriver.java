package function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import util.PropertiesReader;

import java.io.File;

public class BaseDriver {
    /**
     *浏览器驱动
     */
    private WebDriver driver;
    public WebDriver startBrowser(){

        /* 驱动配置进环境变量 */
        // 驱动根路径
        String driverParentPath =this.getClass().getResource("/").getPath()+"driver"+ File.separator;
        String chromeDriverPath =driverParentPath+ PropertiesReader.readProperties().get("chromeDriver");
        // 系统变量设置谷歌驱动
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        /* 驱动可选项配置 */
        ChromeOptions chromeOptions = new ChromeOptions();
        // --no-sandbox
        chromeOptions.addArguments("--no-sandbox");
        // --disable-dev-shm-usage
        chromeOptions.addArguments("--disable-dev-shm-usage");
        // --chrome正受到自动测试软件的控制
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        /* 启动 WebDriver */
        return new ChromeDriver(chromeOptions);
    }
}
