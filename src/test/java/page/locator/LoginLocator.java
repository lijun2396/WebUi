package page.locator;

import org.openqa.selenium.By;

/**
 *阿里登录页面的元素定位
 */
public class LoginLocator {
    /**
     *阿里登录页的切换账户密码登录模式按钮定位
     */
    public static final By ACCOUNT_LOGIN_BUTTON=By.xpath("//*[@id='root']/div[2]/div/div[2]/div[1]/div/div[1]/div[2]");

    /**
     * 阿里登录页的用户名输入框
     */
    public static final By LOGIN_USERNAME_BUTTON=By.xpath("//*[@id='fm-login-id']");

    /**
     * 阿里登录页的密码输入框
     */
    public static final By LOGIN_PASSWORD_BUTTON=By.xpath("//*[@id='fm-login-password']");

    /**
     * 阿里登录页的登录按钮
     */
    public static final By LOGIN_BUTTON=By.xpath("//*[@id=\"login-form\"]/div[5]/button");

    /**
     * 阿里登录页的登录窗口子iframe
     */
    public static final By LOGIN_FRAME=By.id("alibaba-login-box");

    /**
     * 阿里登录页的拖拽动作条iframe
     */
    public static final By LOGIN_DROP_FRAME=By.id("baxia-dialog-content");

    /**
     * 阿里登录页的拖拽动作条元素名称
     */
    public static final By LOGIN_DROP_NAME=By.id("nc_1__scale_text");

    /**
     * 阿里登录页的拖拽动作条失败元素
     */
    public static final By LOGIN_DROP_FAIl_NAME=By.id("nc_1_refresh1");
    /**
     * 阿里登录页的拖拽起始点
     */
    public static final By LOGIN_DROP_BUTTON=By.id("nc_1_n1z");

    /**
     * 阿里登录页绑定邮箱按钮
     */
    public static final By LOGIN_BIND_BUTTON=By.xpath("/html/body/div[2]/div/a[1]");

    /**
     * 阿里登录页不绑定邮箱按钮
     */
    public static final By LOGIN_UNBIND_BUTTON=By.xpath("/html/body/div[2]/div/a[2]");


}
