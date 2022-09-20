package function;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import util.ExcelUtils;

import java.io.IOException;
import java.util.Map;

public class BaseTest {
//git@github.com:abcnull/webuitest4j.git
    /**
     * 驱动基类
     */
    private BaseDriver baseDriver;
    /**
     * 驱动
     * 对外暴露
     */
    public WebDriver driver;
    /**
     * 执行一个测试套之前执行
     * 进行测试配置文件的读取工作
     * 由于 BeforeSuite 不会多线程去执行，因此对于配置文件读取未使用线程安全的操作
     *
     * @throws IOException IOException
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(){
        //todo:看情况读取配置文件
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest(){
        /* 驱动配置
        *   设置驱动并且启动chromeDriver
        * */
        baseDriver =new BaseDriver();
        driver=baseDriver.startBrowser();

        // todo : 由于线程隔离设为 test，这里可以通过 new 一个对象来达到线程隔离的效果，可以做其他的扩展定制（看需要）
        // todo : 登录操作可以放在这里（看需要）

    }
    /**
     * 执行一个测试用例中的类方法之前执行
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // todo : 登录操作可以放在这里（看需要）

    }

    /**
     * 执行一个测试用例中的类方法之后执行
     */
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        // todo : 登录的注销或其他操作可以放在这里（看需要）
    }

    /**
     * 执行一个测试用例之后执行
     *
     * @throws InterruptedException sleep 休眠异常
     */
    @AfterTest(alwaysRun = true)
    public void afterTest() throws InterruptedException {
        driver.quit();
        // 驱动退出关闭浏览器
/*        baseDriver.closeBrowser();
        driver = null;
        // redis 连接回收
        redisUtil.returnJedis();
        // todo : 其他工具的释放操作（看需要）*/
    }
}
