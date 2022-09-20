package testcase;

import function.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import page.page.LoginPage;

public class AliTest extends BaseTest {
    private LoginPage loginPage;
    @BeforeSuite
    public void excel(){

    }
    @Test
    public void loginTest() throws InterruptedException {
        //初始化登录页
        loginPage=new LoginPage(driver);
        loginPage.enterPage();
//        assert loginPage.loginByUi();
        Assert.assertTrue(loginPage.loginByUi());
        Thread.sleep(35000);
    }
}
