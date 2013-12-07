package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import static play.test.Helpers.testServer;
import models.ServerData;
import models.ServerDataTest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import play.libs.F.Callback;
import play.mvc.Result;
import play.test.TestBrowser;

public class ApplicationTest {

	private static void checkServerDataIsShown(TestBrowser browser,
			String stage1, String yellowStar, String m37Ga,
			String commonPort, String commonIP) {
		browser.goTo("http://localhost:9000/showAllServerData");
        
        assertThat(browser.pageSource()).contains(stage1);
        assertThat(browser.pageSource()).contains(yellowStar);
        assertThat(browser.pageSource()).contains(m37Ga);
        assertThat(browser.pageSource()).contains(commonPort);
        assertThat(browser.pageSource()).contains(commonIP);
	}

	private static void addServerDataThroughTheForm(TestBrowser browser,
			String stage1, String yellowStar, String m37Ga,
			String commonPort, String commonIP) {
		browser.goTo("http://localhost:9000/addServerDataForm");

        WebElement conventionalName  = browser.getDriver().findElement(By.name("conventionalName"));
        WebElement hostName  = browser.getDriver().findElement(By.name("hostName"));
        WebElement domain  = browser.getDriver().findElement(By.name("domain"));
        WebElement port  = browser.getDriver().findElement(By.name("port"));
        WebElement ipAddress  = browser.getDriver().findElement(By.name("ipAddress"));
        WebElement submitButton  = browser.getDriver().findElement(By.name("submitButton"));
        
        assertThat(conventionalName).isNotNull();
        assertThat(hostName).isNotNull();
        assertThat(domain).isNotNull();
        assertThat(port).isNotNull();
        assertThat(ipAddress).isNotNull();
        assertThat(submitButton).isNotNull();
        
		conventionalName.sendKeys(stage1);
		hostName.sendKeys(yellowStar);
		domain.sendKeys(m37Ga);
		port.sendKeys(commonPort);
		ipAddress.sendKeys(commonIP);
        submitButton.click();
	}

	@Test
	public void callIndex() {
		Result result = callAction(controllers.routes.ref.Application.index());
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result).contains(
				"<title>Here the current server data</title>"));
	}

	@Test
	public void showData() throws Exception {
		running(fakeApplication(), new Runnable() {
			public void run() {
				String conventionalName1 = "UAT_1";
				String hostName1 = "greenStar";
				String domain = "milkyway.ga";
				int portNumber1 = 1111;
				String ipAddress1 = "12.13.14.15";
				ServerData serverData1 = ServerDataTest.storeServerData(
						conventionalName1, hostName1, domain, portNumber1, ipAddress1);
				
				String conventionalName2 = "UAT_2";
				String hostName2 = "redStar";
				String ipAddress2 = "12.17.14.15";
				int portNumber2 = 2222;
				
				ServerData serverData2 = ServerDataTest.storeServerData(
						conventionalName2, hostName2, domain, portNumber2, ipAddress2);
				
				Result result = callAction(controllers.routes.ref.Application
						.showAllServerData());
				assertThat(contentType(result)).isEqualTo("text/html");

				assertThat(status(result)).isEqualTo(OK);
				assertResultData(result, conventionalName1, hostName1, domain,
						portNumber1, ipAddress1);

				assertResultData(result, conventionalName2, hostName2, domain,
						portNumber2, ipAddress2);

				serverData1.delete();
				serverData2.delete();
			}

			private void assertResultData(Result result,
					String conventionalName, String hostName, String domain,
					int portNumber, String ipAddress) {
				assertThat(contentAsString(result)).contains(conventionalName);
				assertThat(contentAsString(result)).contains(hostName);
				assertThat(contentAsString(result)).contains(domain);
				assertThat(contentAsString(result)).contains(
						String.valueOf(portNumber));
				assertThat(contentAsString(result)).contains(ipAddress);
			}
		});
	}
	
    @Test
    public void addServerDataFormExists() {
        running(testServer(9000, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:9000/addServerDataForm");

                assertThat(browser.pageSource()).contains("conventionalName");
                assertThat(browser.pageSource()).contains("hostName");
                assertThat(browser.pageSource()).contains("domain");
                assertThat(browser.pageSource()).contains("port");
                assertThat(browser.pageSource()).contains("ipAddress");
                assertThat(browser.pageSource()).contains("submit");
                
                WebElement conventionalName  = browser.getDriver().findElement(By.name("conventionalName"));
                WebElement hostName  = browser.getDriver().findElement(By.name("hostName"));
                WebElement domain  = browser.getDriver().findElement(By.name("domain"));
                WebElement port  = browser.getDriver().findElement(By.name("port"));
                WebElement ipAddress  = browser.getDriver().findElement(By.name("ipAddress"));
                WebElement submitButton  = browser.getDriver().findElement(By.name("submitButton"));
                
                assertThat(conventionalName).isNotNull();
                assertThat(hostName).isNotNull();
                assertThat(domain).isNotNull();
                assertThat(port).isNotNull();
                assertThat(ipAddress).isNotNull();
                assertThat(submitButton).isNotNull();
            }
        });
    }
	
    @Test
    public void iCanAddServerDataThroughTheForm() {
        running(testServer(9000, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
            	String stage1 = "Stage 1";
            	String yellowStar = "yellowStar";
            	String m37Ga = "m37.ga";
            	String commonPort = "12332";
            	String commonIP = "23.43.23.11";

            	addServerDataThroughTheForm(browser, stage1, yellowStar, m37Ga,
						commonPort, commonIP);
                
                checkServerDataIsShown(browser, stage1, yellowStar, m37Ga,
						commonPort, commonIP);
            }

        });
    }

    @Test
    public void iCanAddServerDataThroughTheFormAndDeleteIt() {
        running(testServer(9000, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
            	String stage1 = "UAT 1";
            	String yellowStar = "brightStar";
            	String m37Ga = "milkyway.ga";
            	String commonPort = "12332";
            	String commonIP = "23.45.23.11";

            	addServerDataThroughTheForm(browser, stage1, yellowStar, m37Ga,
						commonPort, commonIP);
                
                checkServerDataIsShown(browser, stage1, yellowStar, m37Ga,
						commonPort, commonIP);
                
                assertThat(browser.pageSource()).contains("deleteButton");
            }

        });
    }
}