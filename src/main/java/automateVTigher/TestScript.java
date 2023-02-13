package automateVTigher;

import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;



public class TestScript {
	//static WebWebUtil WebUtil;
	public static   void main(String[] args) {
		//WebUtil=new WebWebUtil();
		//validateUserNameLocation("userNameLocation","TC001","user_name","name","userName");
		//validateTitle("Home Page", "admin - My Home Page - Home - vtiger CRM 5 - Commercial Open Source CRM");
	}

	public  void validateLogin() {
		WebUtil.reportGenerate("login");
		WebUtil.initLogger("TC002");
		WebUtil.browserLaunch("chrome");
		WebUtil.openUrl("http:localhost:8888/");
		WebUtil.enterValue(WebUtil.searchElement("user_name", "name"), "username", "admin");
		WebUtil.enterValue(WebUtil.searchElement("user_password", "name"), "user_password", "admin");
		WebUtil.clickOnElement(WebUtil.searchElement("Login", "name"), "login");
		WebUtil.validateText(WebUtil.searchElement("//td[contains(text(),'My Home Page')]", "xpath"), "My Home Page ");
		WebUtil.flushReport();
	}

	public  void validateTitle(String titleOfPage, String expectedTitleOfPage) {
		WebUtil.reportGenerate("login");
		WebUtil.initLogger("TC003");
		WebUtil.browserLaunch("chrome");
		WebUtil.openUrl("http:localhost:8888/");
		WebUtil.enterValue(WebUtil.searchElement("user_name", "name"), "username", "admin");
		WebUtil.enterValue(WebUtil.searchElement("user_password", "name"), "user_password", "admin");
		WebUtil.clickOnElement(WebUtil.searchElement("Login", "name"), "login");
		// WebUtil.validateText(WebUtil.searchElement("//td[contains(text(),'My Home
		// Page')]", "xpath"), "My Home Page ");
		String expectedTitle = expectedTitleOfPage;
		if (WebUtil.takePageTitle().equalsIgnoreCase(expectedTitle)) {
			WebUtil.logger.log(Status.PASS,MarkupHelper.createLabel("title of the " + titleOfPage + " is match", ExtentColor.GREEN));
		} else {
			WebUtil.logger.log(Status.FAIL,
					MarkupHelper.createLabel("title of the " + titleOfPage + " is not match", ExtentColor.BLACK));

		}

		WebUtil.flushReport();
	}
	public  void validateUserNameLocation(String reportName,String testCase,String locatorValue,String locatorName,String elementName) {
		WebUtil.reportGenerate(reportName);
		WebUtil.initLogger(testCase);
		WebUtil.browserLaunch("chrome"); 
		WebUtil.openUrl("http://localhost:8888/");
		WebElement element=WebUtil.searchElement(locatorValue,locatorName);
		
		WebUtil.validateLocation(element, 632, 230,elementName);
		WebUtil.flushReport();
		
	}
	public  void validateNewLeadCreated() {
		WebUtil.reportGenerate("login");
		WebUtil.initLogger("TC002");
		WebUtil.browserLaunch("chrome");
		WebUtil.openUrl("http:localhost:8888/");
		WebUtil.enterValue(WebUtil.searchElement("user_name", "name"), "username", "admin");
		WebUtil.enterValue(WebUtil.searchElement("user_password", "name"), "user_password", "admin");
		WebUtil.clickOnElement(WebUtil.searchElement("Login", "name"), "login");
		WebUtil.validateText(WebUtil.searchElement("//td[contains(text(),'My Home Page')]", "xpath"), "My Home Page ");
		WebUtil.goToElementAction(WebUtil.searchElement("Marketing", "linkText"));
		WebElement element= WebUtil.searchElement("Leads", "linkText");
		WebUtil.goToElementAction(element);
        WebUtil.clickOnElement(element, "Leads");
        WebUtil.clickOnElement(WebUtil.searchElement("//img[@title='Create Lead...']", "xpath"), "CreateLeads");
        

		WebUtil.flushReport();
	}
	

}





