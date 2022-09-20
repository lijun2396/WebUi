package page.page;

import function.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import page.data.LoginData;
import page.locator.LoginLocator;
import util.ExcelUtils;

import java.util.Map;

@Slf4j
public class LoginPage extends BasePage {
    /**
     * 构造器
     *
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * 进入阿里登录页
     */
    public void enterPage() {
        log.info("进入阿里云登录页");
        super.enterPage(LoginData.URL);
    }


    /**
     * 登录操作
     */
    public boolean loginByUi() throws InterruptedException {
        Map<String, String[]> map = ExcelUtils.xlsGetLoginInfo();
        //切换到用户名密码登录窗口
//        clickButton(LoginLocator.ACCOUNT_LOGIN_BUTTON);
        //切换到登录frame
        switchHandleByElement(LoginLocator.LOGIN_FRAME);
        //输入用户名密码
        sendInput(LoginLocator.LOGIN_USERNAME_BUTTON, map.get("阿里")[0]);
        sendInput(LoginLocator.LOGIN_PASSWORD_BUTTON, map.get("阿里")[1]);
        Thread.sleep(500);
        //如果拖拽动作条不存在，继续执行登录
        //切换到拖拽动作条frame
        try {
            do {
                //切换到拖拽动作条frame
                switchHandleByElement(LoginLocator.LOGIN_DROP_FRAME);
                log.info("======点击拖拽条==========");
                clickButton(LoginLocator.LOGIN_DROP_NAME);
                //进行动作条拖拽
                dragAndDropElement(LoginLocator.LOGIN_DROP_BUTTON, 300, 0);
                //clickButton(LoginLocator.LOGIN_DROP_FRAME);
            }
            while (ifTextExists(LoginLocator.LOGIN_DROP_NAME, LoginData.DROP_TEXT)
                    && ifTextExists(LoginLocator.LOGIN_DROP_FAIl_NAME, LoginData.DROP_FAIL));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //回到父frame窗口
//            switchHandleByElement(LoginLocator.LOGIN_DROP_FRAME);
        switchParentFrame();
        Thread.sleep(500);
        //点击登录按钮
        clickButton(LoginLocator.LOGIN_BUTTON);
        //断言,判断是否进入登录页面
        Thread.sleep(1000);
        return ifTitleContains(LoginData.TEXT);
    }
}
