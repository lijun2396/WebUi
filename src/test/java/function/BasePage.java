package function;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import util.PropertiesReader;

import java.time.Duration;
import java.util.Set;
@Slf4j
public class BasePage {
    /**
     *驱动
     */
    private WebDriver driver;

    /**
     *动作
     */
    protected Actions actions;

    /**
     *脚本
     */
    protected JavascriptExecutor je;

    /**
     *显示等待
     */
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver=driver;
        this.actions=new Actions(driver);
        this.je= (JavascriptExecutor) driver;
        //显示等待时长
        long timeout=Long.parseLong(PropertiesReader.readProperties().get("driver.timeouts.webDriverWait"));
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    /**
     *跳转页面
     * @param url 网站
     */
    public void enterPage(String url){
        driver.get(url);
    }
    /*========================================定位元素========================================*/
    /**
     *通过元素定位拿到WebElement元素对象
     * @param locator  by类型元素定位
     * @return  定位到的元素
     */
    public WebElement locateElement(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
    * 如果定位的元素是一个select ，那么返回一个Select类的对象。参数是定位到select的id表达式
    *
    */
    public Select findSelect(By locator){
        Select select=new Select(driver.findElement(locator));
        return select;
    }

    /**
     *点击元素
     * @param locator by类型元素定位
     * @return  点击的元素
     */
    public WebElement clickButton(By locator) {
        WebElement buttonElement=locateElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        buttonElement.click();
        return buttonElement;
    }

    /**
     *输入框输入数据
     * @param locator   By 类型元素定位
     * @param content   输入的内容，支持多内容，可以键盘输入
     * @return  输入框元素
     */
    public WebElement sendInput(By locator,CharSequence...content){
        WebElement inputElement=locateElement(locator);
        inputElement.clear();
        inputElement.sendKeys(content);
        return inputElement;
    }

    /*============================== 切换 frame 结构 ==============================*/
    /**
     *根据iframeElement切换 frame 结构
     * @param locator   frame 定位
     * @return
     */
    public WebDriver switchHandleByElement(By locator){
        return driver.switchTo().frame(locateElement(locator));
    }

    /**
     * 切换父 frame 结构
     *
     * @return 驱动
     */
    public WebDriver switchParentFrame(){

        return driver.switchTo().parentFrame();
    }

    /**
     * 跳出 frame 结构
     *
     * @return 驱动
     */
    public WebDriver switchOutOfFrame() {
        return driver.switchTo().defaultContent();
    }

    /**
     * 拖拽指定元素
     *
     * @param fromLocator 从...元素
     * @param toLocator   至...元素
     */
    public void dragAndDropElement(By fromLocator,By toLocator){
        wait.until(ExpectedConditions.elementToBeClickable(fromLocator));
        actions.dragAndDrop(locateElement(fromLocator),locateElement(toLocator)).perform();
    }

    /**
     * 根据坐标轴，拖拽指定元素
     * @param fromLocator   从...元素
     * @param xOffset   左负右正
     * @param yOffset   上负下正
     */
    public void dragAndDropElement(By fromLocator,int xOffset,int yOffset){
        wait.until(ExpectedConditions.elementToBeClickable(fromLocator));
        actions.dragAndDropBy(locateElement(fromLocator),xOffset,yOffset).perform();

    }

    /**
     * 为防止太快速的拖拽完动作条，将拖拽动作进行拆分
     * @param fromLocator   从...元素
     * @param xOffset   左负右正
     * @param yOffset    上负下正
     * @throws InterruptedException
     */
    public void clickAndHold(By fromLocator,int xOffset,int yOffset) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(fromLocator));
        //确保每次拖动的像素不同，故而使用随机数
        actions.clickAndHold(locateElement(fromLocator)).moveByOffset((int)Math.random()*10+xOffset/2,(int)Math.random()*10+yOffset/2);
        Thread.sleep(100);
        actions.clickAndHold(locateElement(fromLocator)).moveByOffset((int)Math.random()*10+xOffset/2,(int)Math.random()*10+yOffset/2);
        Thread.sleep(100);
        //拖动完释放鼠标
        actions.moveToElement(locateElement(fromLocator)).release().perform();
    }

    /*============================== 切换窗口句柄 ==============================*/
    /**
     * 查找下一个句柄，若只有一个窗口则句柄不变
     *
     * @return 驱动
     */
    public WebDriver switchNextHandle(){
        //当前窗口句柄
        String currentHandle=driver.getWindowHandle();
        //所有窗口句柄
        Set<String> allHandleSet=driver.getWindowHandles();
        //寻找下一个句柄
        for (String handle:allHandleSet){
            if (!handle.equals(currentHandle)){
                return driver.switchTo().window(handle);
            }
        }
        return driver;
    }

    /**
     * 多窗口切换句柄，依据传入的句柄号码
     *
     * @param num 号码从 1 开始
     * @return 驱动
     */
    public WebDriver switchHandleByNum(int num){
        //所有窗口句柄
        Set<String> allHandleSet=driver.getWindowHandles();
        String [] allHandleArr=allHandleSet.toArray(new String[allHandleSet.size()]);
        //切换到指定num句柄
        return driver.switchTo().window(allHandleArr[num-1]);

    }

    /**
     * 多窗口切换句柄，依据传入的窗口标题
     *
     * @param title contains(窗口 title)
     * @return 驱动
     * @throws Exception 找不到指定窗口句柄异常
     */
    public WebDriver switchHandleByTitle(String title) throws Exception {
        //获取当前窗口句柄
        String currentHandle=driver.getWindowHandle();
        //获取所有窗口句柄
        Set<String> allHandleSet=driver.getWindowHandles();
        String[] allHandleArr=allHandleSet.toArray(new String[allHandleSet.size()]);
        // 寻找第一个 title 符合的句柄
        for (String handle:allHandleArr){
            driver.switchTo().window(handle);
            if (driver.getTitle().contains(title)){
                return driver;
            }
        }
        driver.switchTo().window(currentHandle);
        throw new Exception(title+"窗口的句柄不存在");
    }

    /**
     * 多窗口切换句柄，依据传入的窗口 url
     *
     * @param url contains(窗口 url)
     * @return 驱动
     * @throws Exception 找不到指定窗口句柄异常
     */
    public WebDriver switchHandleByUrl(String url) throws Exception {
        // 当前窗口句柄
        String currentHandle = driver.getWindowHandle();
        // 所有窗口句柄
        Set<String> allHandlesSet = driver.getWindowHandles();
        for (String handle : allHandlesSet) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().contains(url)) {
                return driver;
            }
        }
        driver.switchTo().window(currentHandle);
        throw new Exception(url + "窗口的句柄不存在");
        }
    /*============================== JS 操作 ==============================*/
    /**
     * 执行 JS 脚本
     *
     * @param script JS 脚本
     * @param args   对象元素数组
     */
    public void executeScript(String script,Object...args){
        je.executeScript(script,args);
    }

    /**
     * 执行 JS 脚本
     *
     * @param script JS 脚本
     */
    public void executeScript(String script) {
        je.executeScript(script);
    }

    /**
     * 滑动到页面最顶上
     */
    public void scrollToTop() {
        executeScript("window.scrollTo(window.pageXOffset, 0)");
    }

    /**
     * 滑动到页面最底部
     */
    public void scrollToBottom() {
        executeScript("window.scrollTo(window.pageXOffset, document.body.scrollHeight)");
    }

    /**
     * 滑动使得元素和窗口顶端对齐
     *
     * @param by 需要和页面顶端对齐的元素
     */
    public void scrollElementTopToTop(By by) {
        executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
    }

    /**
     * 滑动使得元素和窗口底部对齐
     *
     * @param by 需要和页面底端对齐的元素
     */
    public void scrollElementBottomToBottom(By by) {
        executeScript("arguments[0].scrollIntoView(false);", driver.findElement(by));
    }

    /**
     * 滑动到页面最右边
     */
    public void scrollToRight() {
        executeScript("window.scrollTo(document.body.scrollWidth, window.pageYOffset)");
    }

    /**
     * 滑动到页面最左边
     */
    public void scrollToLeft() {
        executeScript("0, window.pageYOffset)");
    }

    /*============================== 页面基本断言 ==============================*/

    /**
     * 判断当前页面标题是否是指定标题
     *
     * @param title 指定标题
     * @return 布尔值
     */
    public boolean ifTitleIs(String title){
        log.info("======当前页面标题:" +driver.getTitle() + "==================");
        return wait.until(ExpectedConditions.titleIs(title));
    }

    /**
     * 判断当前页面标题是否含有指定文本
     *
     * @param text 指定文本
     * @return 布尔值
     */
    public boolean ifTitleContains(String text){
        log.info("======当前页面标题:" +driver.getTitle() + "==================");
        return wait.until(ExpectedConditions.titleContains(text));
    }

    /**
     * 判断当前页面某个元素的文本值是否是指定文本
     *
     * @param locator 页面元素定位
     * @param text    指定文本
     * @return 布尔值
     */
    public boolean ifTextExists(By locator,String text){
        WebElement Element=locateElement(locator);
        log.info("=====拖拽条名称:======" + Element.getText());
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,text));
    }

    /**
     * 文字检查 根据WebElement对象的getText()方法取到的文字和预期是否相同
     * @param locator 是一个webelment对象，不需要取对象的文字
     * @param expected 输入预期的文字
    */
    public void check_text(By locator,String expected){
        WebElement Element=locateElement(locator);
        Assert.assertEquals(Element.getText(),expected);
    }
    public void check_text(String string,String expected){
        Assert.assertEquals(string,expected);
    }
}






