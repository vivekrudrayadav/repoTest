package automateVTigher;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class WebUtil {
	public static WebDriver driver;
	public static ExtentReports eReport;
	public static ExtentTest logger;
	public static JavascriptExecutor js = (JavascriptExecutor) driver;

//	public  void main(String[] args) {
//		System.out.println(timestamp());
	// reportGenerate("autoPractise");
	// initLogger("login");
	//
	// browserLaunch("chrome");
	//
	// openUrl("http://localhost:8888/");
	// enterValue(searchElement("user_name", "name"), "user_name", "admin");
	// enterValue(searchElement("user_password", "name"), "user_password", "admin");
	// clickOnElement(searchElement("Login", "name"), "login");
	// goToElement(searchElement("Marketing", "linkText"));
	// goToElement(searchElement("Leads", "linkText"));
	// ClickByAction();
	// // TableData("// tr[@id='row_38']//a", "xpath");
	// editDataOfTable(33);
	// eReport.flush();
//	}

	/**
	 * this method is used to delete row data of the table
	 * 
	 * @param rowNomberw :row which want to delete
	 */
	public static void deleteDataFromTable(int rowNomberw) {
		try {
			WebElement element = searchElement("//tr[@id='row_" + rowNomberw + "']//a[text()='del']", "xpath");
			clickOnElement(element, "delete");
			// acceptAlert();
			dismissAlert();
			logger.log(Status.INFO, MarkupHelper.createLabel("row " + rowNomberw + " has been deleted successfully",
					ExtentColor.YELLOW));
		} catch (Exception e) {
			logger.log(Status.INFO, MarkupHelper.createLabel("row " + rowNomberw + " has not been deleted successfully",
					ExtentColor.RED));

			e.printStackTrace();
		}
	}

	/**
	 * this method is used to edit the row data of row of the table
	 * 
	 * @param rowNomberw :row which want to delete
	 */
	public static void editDataOfTable(int rowNomberw) {
		try {
			WebElement element = searchElement("//tr[@id='row_" + rowNomberw + "']//a[text()='edit']", "xpath");
			clickOnElement(element, "edit");
			// acceptAlert();
			dismissAlert();
			logger.log(Status.INFO, MarkupHelper.createLabel("edit button has been click of row number-- " + rowNomberw,
					ExtentColor.YELLOW));
		} catch (Exception e) {
			logger.log(Status.INFO,
					MarkupHelper.createLabel(
							"edit button has not been click of row number-- " + rowNomberw + ".Please check",
							ExtentColor.YELLOW));

			e.printStackTrace();
		}
	}

	/**
	 * this method is used to get the data of the specific row of the table
	 * 
	 * @param rowNomberw row number whose data want to get
	 */
	public static void TableData(int rowNomberw) {
		try {
			List<WebElement> elements = searchElements("//tr[@id='row_" + rowNomberw + "']//a[text()='edit']", "xpath");
			for (WebElement Element : elements) {
				String data = fetchInnere(Element);
				logger.log(Status.INFO, MarkupHelper.createLabel("data of table --" + data, ExtentColor.GREEN));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to get the time
	 * 
	 * @return it return the time in form of String
	 */
	public static String timestamp() {
		SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z	");
		String timeStamp = date.format(new Date());
		return timeStamp;
	}

	/**
	 * this method is used to generate the report
	 * 
	 * @param reportNam name of the report take as the parameter
	 */
	public static void reportGenerate(String reportNam) {
		ExtentSparkReporter spark = new ExtentSparkReporter("automation\\" + reportNam + ".html");
		eReport = new ExtentReports();
		eReport.attachReporter(spark);

	}

	/**
	 * this method is used to create the test case
	 * 
	 * @param TCName take test case name as the parameter
	 */
	public static void initLogger(String TCName) {
		logger = eReport.createTest(TCName);

	}

	public static void flushReport() {
		eReport.flush();
	}

	/**
	 * this method is used to launch the browser
	 * 
	 * @param browserName take browser name as the parameter
	 */
	public static void browserLaunch(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("WebDriver.chrome.driver",
						"C:\\Users\\pc\\Desktop\\vivekAutomation\\vTigerAutomation\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("safari")) {
				driver = new SafariDriver();
			} else if (browserName.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
			} else if (browserName.equalsIgnoreCase("internetExplorer")) {
				driver = new InternetExplorerDriver();
			}
			logger.log(Status.INFO, browserName + " browser has been launch");

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Status.FAIL, browserName + "browser has not  launch. Please check the broser");

		}
	}

	/**
	 * this method is used to open the specific URL
	 * 
	 * @param url take url as the parameter
	 */
	public static void openUrl(String url) {
		try {
			driver.get(url);
			logger.log(Status.INFO, url + " hit successfully.");
		} catch (Exception e) {
			driver.navigate().to(url);
			logger.log(Status.INFO, url + " hit successfully.");
		}
	}//// tr[@id='row_38']//a

	/**
	 * this method is used to find the WebElement on the webPage
	 * 
	 * @param locatorValue
	 * @param locatorName
	 * @return it retun the WebElement as it found the element on WebPage
	 */
	public static  WebElement searchElement(String locatorValue, String locatorName) {
		WebElement we = null;
		try {
			if (locatorName.equalsIgnoreCase("xpath")) {
				return we = driver.findElement(By.xpath(locatorValue));
			} else if (locatorName.equalsIgnoreCase("css")) {
				return we = driver.findElement(By.cssSelector(locatorValue));
			} else if (locatorName.equalsIgnoreCase("name")) {
				return we = driver.findElement(By.name(locatorValue));
			} else if (locatorName.equalsIgnoreCase("tagName")) {
				return we = driver.findElement(By.tagName(locatorValue));
			} else if (locatorName.equalsIgnoreCase("id")) {
				return we = driver.findElement(By.id(locatorValue));
			} else if (locatorName.equalsIgnoreCase("className")) {
				return we = driver.findElement(By.className(locatorValue));
			} else if (locatorName.equalsIgnoreCase("linkText")) {
				return we = driver.findElement(By.linkText(locatorValue));
			} else if (locatorName.equalsIgnoreCase("partialLinkText")) {
				return we = driver.findElement(By.partialLinkText(locatorValue));
			} else {
				logger.log(Status.FAIL, locatorName + " is invalid .Please check it");
			}
		} catch (NullPointerException e) {
			logger.log(Status.FAIL, " Element is not found on the UI page");
			e.printStackTrace();
		} catch (InvalidSelectorException e) {
			logger.log(Status.FAIL, locatorValue + " is invalid .Please check it");

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return we;
	}

	/**
	 * this method is used to find the list of WebElement on the webPage
	 * 
	 * @param locatorValue
	 * @param locatorName
	 * @return it retun the list of WebElement as it found the element on WebPage
	 */
	public static List<WebElement> searchElements(String locatorValue, String locatorName) {
		List<WebElement> we = null;
		try {
			if (locatorName.equalsIgnoreCase("xpath")) {
				return we = driver.findElements(By.xpath(locatorValue));
			} else if (locatorName.equalsIgnoreCase("css")) {
				return we = driver.findElements(By.cssSelector(locatorValue));
			} else if (locatorName.equalsIgnoreCase("name")) {
				return we = driver.findElements(By.name(locatorValue));
			} else if (locatorName.equalsIgnoreCase("tagName")) {
				return we = driver.findElements(By.tagName(locatorValue));
			} else if (locatorName.equalsIgnoreCase("id")) {
				return we = driver.findElements(By.id(locatorValue));
			} else if (locatorName.equalsIgnoreCase("className")) {
				return we = driver.findElements(By.className(locatorValue));
			} else if (locatorName.equalsIgnoreCase("linkText")) {
				return we = driver.findElements(By.linkText(locatorValue));
			} else if (locatorName.equalsIgnoreCase("partialLinkText")) {
				return we = driver.findElements(By.partialLinkText(locatorValue));
			} else {
				logger.log(Status.FAIL, locatorName + " is invalid .Please check it");
			}
		} catch (NullPointerException e) {
			logger.log(Status.FAIL, " Element is not found on the UI page");
			e.printStackTrace();
		} catch (InvalidSelectorException e) {
			logger.log(Status.FAIL, locatorValue + " is invalid .Please check it");

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return we;
	}

	/**
	 * this method is used to check that element is diplay on WebPage and enabled or
	 * not
	 * 
	 * @param element
	 * @return it return true if Webelement is dispaly and enabled on WebPage
	 */
	public static boolean elementStatus(WebElement element) {
		boolean status = false;
		try {
			if (element.isDisplayed() == true) {
				if (element.isEnabled() == true) {
					status = true;
					logger.log(Status.INFO, element + " is actionable");
				} else {
					logger.log(Status.INFO, element + " is not actionable");
				}
			} else {
				logger.log(Status.INFO, element + " is not displaing");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * this method is used to to set the time on Weddriver .so that it can wait
	 * before throwing no such element.
	 * 
	 * @param time how long it will wait
	 */
	public static void setImplicitWait(int timeInSecond) {
		try {
			timeInSecond = 1000 * timeInSecond;
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSecond));
		} catch (Exception e) {

		}
	}

	// public void explicitly(Duration.ofSeconds(time)) {
	// new WebDriverWait(driver,);
	// }

	/**
	 * @param element     this method is used to enter the value in the box.
	 * @param value       value which have to be entered in the element
	 * @param elementName name of element in which value will entered
	 */
	public static void enterValue(WebElement element, String value, String elementName) {
		try {
			if (elementStatus(element) == true) {
				element.clear();
				element.sendKeys(value);
				logger.log(Status.INFO, value + " is enter in the " + elementName + " successfully.");
			} else {
				logger.log(Status.INFO,
						value + " is not enter in the " + elementName + " .Please check the " + elementName);
			}

		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				new Actions(driver).sendKeys(value).build().perform();
				logger.log(Status.INFO, value + " is enter in the " + elementName + " successfully.");
			} catch (Exception ecp) {
				ecp.printStackTrace();
			}
		}
	}

	/**
	 * this method is used to get the size of the element
	 * 
	 * @param element whose size it will get
	 * @return it return the dimension of the element
	 */
	public static Dimension elementSize(WebElement element) {
		Dimension measurement = element.getSize();
		return measurement;
	}

	/**
	 * this method is used to click on the element
	 * 
	 * @param element     whome it will click
	 * @param elementName element name on which click will perform
	 */
	public  static void clickOnElement(WebElement element, String elementName) {
		try {
			if (elementStatus(element) == true) {
				element.click();
				logger.log(Status.INFO, elementName + " is clicked");

			} else {
				logger.log(Status.INFO, elementName + " is not clicked");

			}
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * this method is used to get the value of the attribute which
	 * 
	 * @param element       it take webElement as paremeter whose attribute value
	 *                      will be taken
	 * @param attributeName attribute name whose value have to be fetch
	 * @return it return the value of the attribute as String
	 */
	public static String fetchAttributevalue(WebElement element, String attributeName) {
		String text = null;
		try {
			text = element.getAttribute(attributeName);
			logger.log(Status.INFO, "value of the attribute " + attributeName + " has been taken successf");
		} catch (Exception e) {
			logger.log(Status.FAIL, "failed to take the value of the attribute " + attributeName);
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * this method is used to get the inner text of the element whose have to take
	 * 
	 * @param element it take webElement as paremeter whose inner text will be taken
	 * @return it return the inner text as String
	 */
	public static String fetchInnere(WebElement element) {
		String text = null;
		try {
			text = element.getText();
			logger.log(Status.INFO, "inner text of the element has been taken successfully");

		} catch (Exception e) {
			logger.log(Status.FAIL, "failed to take the inner text of the element");
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * this method is used to check that the checkbox is selected or not
	 * 
	 * @param element     it take webElement as paremeter whome it will check that
	 *                    it is checked` or not
	 * @param elementName name of the element on which it will perform
	 * @return
	 */
	public static boolean getCheckBoxStatus(WebElement element, String elementName) {
		boolean status = false;
		try {
			status = element.isSelected();
			logger.log(Status.INFO, elementName + " element is selected");

		} catch (Exception e) {
			logger.log(Status.INFO,
					MarkupHelper.createLabel(elementName + " element is not selected", ExtentColor.GREEN));
			e.printStackTrace();
		}
		return status;

	}

	/**
	 * this method is used to get the title of the page on which it currently
	 * present
	 * 
	 * @return it return the title of the page as String
	 */
	public static String takePageTitle() {
		String text = null;
		try {
			text = driver.getTitle();
			logger.log(Status.INFO, "title  has been taken successfully");

		} catch (Exception e) {
			logger.log(Status.FAIL, "title  has not  been taken successfully");
			e.printStackTrace();
		}
		return text;

	}

	/**
	 * this method is used to get the current url of the page
	 * 
	 * @return it will return the current url as the String
	 */
	public static String takeCurrentUrl() {
		String text = null;
		try {
			text = driver.getCurrentUrl();
			logger.log(Status.INFO, "current url  has been taken successfully");

		} catch (Exception e) {
			logger.log(Status.FAIL, "current url  has not  been taken successfully");
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * this method is used to close the window on which it is active
	 * 
	 */
	public static void closeWindow() {
		try {
			driver.close();
			logger.log(Status.INFO, "current working window has been closed successfully");

		} catch (Exception e) {
			logger.log(Status.FAIL, "current working window has not  been closed successfully");
			e.printStackTrace();
		}
	}

	/**
	 * this method is used select the option of the drop down by visible text
	 * 
	 * @param element     drop down in which its option have to be select
	 * @param visibleText text through which the option is select
	 */
	public static void chooseByVisibleText(WebElement element, String visibleText) {
		try {
			new Select(element).selectByVisibleText(visibleText);
			logger.log(Status.INFO, visibleText + " has been selected from the dropdown");
		} catch (Exception e) {
			logger.log(Status.INFO, visibleText + " has not been selected from the dropdown");
			e.getMessage();
		}
	}

	/**
	 * this method is used select the option of the drop down by value of the value
	 * attribute
	 * 
	 * @param element drop down in which its option have to be select
	 * @param value   value of the value attribute through which the option is
	 *                select
	 */
	public static void chooseByValue(WebElement element, String value) {
		try {
			new Select(element).selectByValue(value);
			logger.log(Status.INFO, value + " has been selected from the dropdown");
		} catch (Exception e) {
			logger.log(Status.INFO, value + " has not been selected from the dropdown");
			e.getMessage();
		}
	}

	/**
	 * this method is used to select the option of the drop down by the index
	 * 
	 * @param element drop down in which its option have to be select
	 * @param index   value of the option which have to be select
	 */
	public static void chooseByIndex(WebElement element, int index) {
		try {
			new Select(element).selectByIndex(index);
			logger.log(Status.INFO, " Option having index number " + index + " has been selected from the dropdown");
		} catch (Exception e) {
			logger.log(Status.INFO,
					" Option having index number " + index + " has not been selected from the dropdown");
			e.getMessage();
		}
	}

	/**
	 * THis method is used to take all selected option of the drop down
	 * 
	 * @param element this take webElement of the drop down as the parameter
	 * @return it return the list of WebElement which are selected in the drop down
	 */
	public static List<WebElement> takeAllSelectedOption(WebElement element) {
		List<WebElement> selectedOptions = null;
		try {
			selectedOptions = new Select(element).getAllSelectedOptions();
			logger.log(Status.INFO, " all the selected option has been taken successfullly.");
		} catch (Exception e) {
			logger.log(Status.INFO, " all the selected option has not  been taken successfullly.");
			e.getMessage();
		}
		return selectedOptions;
	}

	/**
	 * this method is used to take all option of the drop down
	 * 
	 * @param element this take webElement of the drop down as the parameter
	 * @return it return the list of the WebElement which are in the drop down
	 */
	public static List<WebElement> takeAllOption(WebElement element) {
		List<WebElement> selectedOptions = null;
		try {
			selectedOptions = new Select(element).getOptions();
			logger.log(Status.INFO, " all the  option has been taken successfullly.");
		} catch (Exception e) {
			logger.log(Status.INFO, " all the option has not  been taken successfullly.");
			e.getMessage();
		}
		return selectedOptions;
	}

	/**
	 * @param element
	 * @return
	 */
	public static WebElement takeFirstSelectedOptionDropDown(WebElement element) {
		WebElement selectedOptions = null;
		try {
			selectedOptions = new Select(element).getFirstSelectedOption();
			logger.log(Status.INFO, " First selected  option has been taken successfullly.");
		} catch (Exception e) {
			logger.log(Status.INFO, " First selected  option has not  been taken successfullly.");
			e.getMessage();
		}
		return selectedOptions;
	}

	public static boolean isMultipleOption(WebElement element) {
		boolean status = false;
		try {
			new Select(element).isMultiple();
			logger.log(Status.INFO, " multiple option select is avilable");
		} catch (Exception e) {
			logger.log(Status.INFO, " multiple option select is not avilable");
			e.getMessage();
		}

		return status;
	}

	public static void switchToFrame(int index) {
		try {
			driver.switchTo().frame(index);
			logger.log(Status.INFO, "driver switch to the frame having the index number " + index);
		} catch (Exception e) {
			logger.log(Status.INFO, "driver does not switch to the frame having the index number " + index);
			e.getMessage();
		}
	}

	public static void switchToFrame(String valueOrId) {
		try {
			driver.switchTo().frame(valueOrId);
			logger.log(Status.INFO, "driver switch to the frame having the attribute value  = " + valueOrId);
		} catch (Exception e) {
			logger.log(Status.INFO, "driver does not switch to the frame having the attribute value  = " + valueOrId);
			e.getMessage();
		}
	}

	public static void switchToFrame(WebElement element, String elementName) {
		try {
			driver.switchTo().frame(element);
			logger.log(Status.INFO, "driver switch to the " + elementName + "frame ");
		} catch (Exception e) {
			logger.log(Status.INFO, "driver does not switch to the " + elementName + "frame ");
			e.getMessage();
		}
	}

	public static void switchToParentFrame() {
		try {
			driver.switchTo().parentFrame();
			logger.log(Status.INFO, "driver is switch to the parent frame ");
		} catch (Exception e) {
			logger.log(Status.INFO, "driver does not switch to the parent frame ");
			e.getMessage();
		}
	}

	public static void switchToMainFrame() {
		try {
			driver.switchTo().defaultContent();
			logger.log(Status.INFO, "driver is switch to the main frame ");
		} catch (Exception e) {
			logger.log(Status.INFO, "driver does not switch to the main frame ");
			e.getMessage();
		}
	}

	public static void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
			logger.log(Status.INFO, "Alert has been accepted");
		} catch (Exception e) {
			logger.log(Status.INFO, "Alert has not been accepted");
			e.getMessage();
		}
	}

	public static void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			logger.log(Status.INFO, "Alert has been dismissed");
		} catch (Exception e) {
			logger.log(Status.INFO, "Alert has not been dismissed");
			e.getMessage();
		}
	}

	public static String alertMessage() {
		String message = null;
		try {
			message = driver.switchTo().alert().getText();
			logger.log(Status.INFO, "Alert message has been taken");
		} catch (Exception e) {
			logger.log(Status.INFO, "Alert message has not been taken");
			e.getMessage();
		}
		return message;
	}

	public static void alertMessage(String text) {

		try {
			driver.switchTo().alert().sendKeys(text);
			logger.log(Status.INFO, text + " Alert message has been entered");
		} catch (Exception e) {
			logger.log(Status.INFO, text + " Alert message has not been entered");
			e.getMessage();
		}

	}

	public static void clickByAction(WebElement element, String elementName) {
		try {
			new Actions(driver).click(element).build().perform();
			logger.log(Status.INFO, elementName + " is click successfully");
		} catch (Exception e) {
			logger.log(Status.INFO, elementName + " is not  click successfully. Plesse check ");
			e.getMessage();
		}
	}

	public static void enterValueByAction(WebElement element, String value, String elementName) {
		try {
			new Actions(driver).sendKeys(element, value).build().perform();
			logger.log(Status.INFO, value + " is send  successfully");
		} catch (Exception e) {
			logger.log(Status.INFO, value + " is not send successfully");
			e.getMessage();
		}
	}

	public static void goToElementAction(WebElement element) {
		try {
			new Actions(driver).moveToElement(element).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ClickByActionAction() {

		try {
			new Actions(driver).click().build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void ClicAndHoldAction() {

		try {
			new Actions(driver).clickAndHold().build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void doubleClickAction(WebElement element) {

		try {
			new Actions(driver).clickAndHold(element).build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void doubleClickAction() {

		try {
			new Actions(driver).clickAndHold().build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void validateText(WebElement element, String expectedText) {
		try {
			String actualText = fetchInnere(element);

			if (actualText.equalsIgnoreCase(expectedText)) {
				logger.log(Status.PASS, MarkupHelper.createLabel(
						"text validation is passed. Actual text--" + actualText + " Expected text--" + expectedText,
						ExtentColor.GREEN));
			} else {
				logger.log(Status.FAIL, MarkupHelper.createLabel(
						"text validation is failed.  Actual text--" + actualText + " Expected text--" + expectedText,
						ExtentColor.RED));

			}

		} catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(
					e + " exception has occured at the time of validating text of the element", ExtentColor.RED));

			e.printStackTrace();
		}

	}

	public static void validateAttributeValue(WebElement element, String expectedValue, String value) {
		try {
			String actualValue = fetchAttributevalue(element, value);

			if (actualValue.equalsIgnoreCase(expectedValue)) {
				logger.log(Status.PASS, MarkupHelper.createLabel("Attribute value validation is passed. Actual value--"
						+ actualValue + " Expected value--" + expectedValue, ExtentColor.GREEN));
			} else {
				logger.log(Status.FAIL, MarkupHelper.createLabel("Attribute value validation is failed.  Actual value--"
						+ actualValue + " Expected value--" + expectedValue, ExtentColor.RED));

			}
		} catch (Exception e) {

			logger.log(Status.FAIL, MarkupHelper.createLabel(
					e + " exception has occured at time of validating the attribute value", ExtentColor.BLACK));

			e.printStackTrace();
		}

	}

	public static void validateEnabled(WebElement element, String elementName) {
		try {
			boolean status = false;
			status = element.isEnabled();
			if (status == true) {
				logger.log(Status.PASS, MarkupHelper.createLabel(elementName + " is enabled", ExtentColor.GREEN));
			} else {
				logger.log(Status.FAIL, MarkupHelper.createLabel(elementName + " is  not enabled", ExtentColor.RED));

			}
		} catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(
					e + " exception has occured at time of validating the element is enabled ", ExtentColor.BLACK));

			e.printStackTrace();
		}
	}

	public static void validateDisabled(WebElement element, String elementName) {
		try {

			boolean status = false;
			status = element.isEnabled();
			if (status == false) {
				logger.log(Status.PASS, MarkupHelper.createLabel(elementName + " is disabled", ExtentColor.GREEN));
			} else {
				logger.log(Status.FAIL, MarkupHelper.createLabel(elementName + " is not disabled", ExtentColor.RED));

			}
		} catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(
					e + " exception has occured at time of validating the element is disabled ", ExtentColor.BLACK));
			e.printStackTrace();
		}
	}

	public static void validateDisplay(WebElement element, String elementName) {
		try {

			boolean status = false;
			status = element.isDisplayed();
			if (status == true) {
				logger.log(Status.PASS,
						MarkupHelper.createLabel(elementName + " is display on the UI page", ExtentColor.GREEN));
			} else {
				logger.log(Status.FAIL,
						MarkupHelper.createLabel(elementName + " is display on the UI page", ExtentColor.RED));

			}
		} catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(
					e + " exception has occured at time of validating the element is display ", ExtentColor.BLACK));
			e.printStackTrace();
		}
	}

	public static void validateHide(WebElement element, String elementName) {
		try {

			boolean status = false;
			status = element.isDisplayed();
			if (status == false) {
				logger.log(Status.PASS,
						MarkupHelper.createLabel(elementName + " is hide on the UI page", ExtentColor.GREEN));
			} else {
				logger.log(Status.FAIL,
						MarkupHelper.createLabel(elementName + " is hide on the UI page", ExtentColor.RED));

			}
		} catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(
					e + " exception has occured at time of validating the element is hide ", ExtentColor.BLACK));
			e.printStackTrace();
		}
	}

	public static Dimension getElementSize(WebElement element) {
		Dimension measurement = null;
		try {

			measurement = element.getSize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return measurement;
	}

	public static Point getElementLocation(WebElement element) {
		Point measurement = null;
		try {

			measurement = element.getLocation();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return measurement;
	}

	public static Point getElemenetLocation(WebElement element) {
		Point location = null;
		try {
			location = element.getLocation();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public static void validateLocation(WebElement element, int expected_x, int expected_y, String elementName) {
		try {
			Point p = getElemenetLocation(element);
			int actual_X = p.getX();
			int actual_Y = p.getY();
			if (actual_X == expected_x && actual_Y == expected_y) {
				logger.log(Status.PASS, "location of the " + elementName + " is at their position");
			} else {
				logger.log(Status.FAIL,
						"location of the " + elementName + " is not attheir position actual_X " + actual_X
								+ " expected_x " + expected_x + " actual_Y " + actual_Y + " expected_y " + expected_y);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Dimension getElemenetSize(WebElement element) {
		Dimension location = null;
		try {
			location = element.getSize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public static void validateSize(WebElement element, int expectedWidth, int expectedHeight, String elementName) {
		try {
			Point p = getElemenetLocation(element);
			int actual_width = p.getX();
			int actual_Height = p.getY();
			if (actual_width == expectedWidth && actual_Height == expectedHeight) {
				logger.log(Status.PASS, "Size of the " + elementName + " is Right ");
			} else {
				logger.log(Status.FAIL,
						"Size of the " + elementName + " is not at Right actual_width " + actual_width
								+ " expectedWidth " + expectedWidth + " actual_Height " + actual_Height
								+ " expectedHeight " + expectedHeight);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int colorOfElement(WebElement element, String text_backgroundColor) {
		int colorCode = 0;
		try {
			String rgbaOfElement = element.getCssValue(text_backgroundColor);
			colorCode = Color.fromString(rgbaOfElement).hashCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colorCode;

	}

	public static String takePageSourse() {
		String pageSourse = null;
		try {
			pageSourse = driver.getPageSource();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageSourse;
	}

	public static String takeWindowHandle() {
		String windowHandle = null;
		try {
			windowHandle = driver.getWindowHandle();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return windowHandle;
	}

	public static Set<String> takeWindowHandles() {
		Set<String> windowHandles = null;
		try {
			windowHandles = driver.getWindowHandles();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return windowHandles;
	}

	public static void handleMultipleWindow(String titleOrUrl, String titleOrUrlValue) {
		try {
			Set<String> windowHandles = takeWindowHandles();
			if (titleOrUrl.equalsIgnoreCase("title")) {
				for (String windowHandle : windowHandles) {
					if (driver.getTitle().equalsIgnoreCase(titleOrUrlValue)) {
						driver.switchTo().window(windowHandle);
						break;
					}
				}
			} else {
				for (String windowHandle : windowHandles) {
					if (driver.getCurrentUrl().equalsIgnoreCase(titleOrUrlValue)) {
						driver.switchTo().window(windowHandle);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeAllWindowExceptOne(String titleOrUrl, String titleOrUrlValue) {
		try {
			Set<String> windowHandles = takeWindowHandles();
			if (titleOrUrl.equalsIgnoreCase("title")) {
				for (String windowHandle : windowHandles) {
					if (!(driver.getTitle().equalsIgnoreCase(titleOrUrlValue))) {
						driver.switchTo().window(windowHandle);
						closeWindow();
					}
				}
			} else {
				for (String windowHandle : windowHandles) {
					if (!(driver.getCurrentUrl().equalsIgnoreCase(titleOrUrlValue))) {
						driver.switchTo().window(windowHandle);
						closeWindow();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method is used to enter the value on the element using the java script
	 * 
	 * @param element     webelement on which the value will entered
	 * @param value       tha value which have to be enter in the webelement
	 * @param elementName name of the element on which the value hgave to be enter
	 */
	public static void jsEnterValue(WebElement element, String value, String elementName) {
		try {
			js.executeScript("arguments[0].value='" + value + "'", element);
			logger.log(Status.INFO, elementName + " has been click  successfully");

		} catch (JavascriptException e) {
			logger.log(Status.FAIL, elementName + " has not been click  successfully");

			e.printStackTrace();
		} catch (Exception e) {
			logger.log(Status.FAIL, elementName + " has not  been click  successfully");

			e.printStackTrace();
		}

	}

	/**
	 * this method is used to get the title of the page using the java script
	 * 
	 * @return it retun the title of the page as the string
	 */
	public static String jsTakePageTitle() {
		String title = null;
		try {
			title = (String) js.executeScript("return document.title");
			logger.log(Status.INFO, "title of the page has been taken successfully");
		} catch (JavascriptException e) {
			logger.log(Status.FAIL, "title of the page has not been taken successfully");
			e.printStackTrace();
		} catch (Exception e) {
			logger.log(Status.FAIL, "title of the page has not  been taken successfully");
			e.printStackTrace();
		}

		return title;
	}

	/**
	 * click on the element by the java script
	 * 
	 * @param element     it take webelement as the parameter on which the click
	 *                    will perform
	 * @param elementName name of the element on which the click will perform
	 */
	public static void jsClick(WebElement element, String elementName) {
		try {
			js.executeAsyncScript("arguments[0].click;", element);
			logger.log(Status.INFO, elementName + "   has been click successfully");
		} catch (JavascriptException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void jsScrollByAmount(int horizontal, int vertical) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(" + horizontal + "," + vertical + ")");
			logger.log(Status.INFO, "");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void jsScrollByView(int horizontal, int vertical) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(" + horizontal + "," + vertical + ")");
			logger.log(Status.INFO, "");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
