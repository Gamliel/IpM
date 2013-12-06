package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import models.ServerData;
import models.ServerDataTest;

import org.junit.Test;

import play.mvc.Result;

public class ApplicationTest {

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
				String hostName1 = "greenStar";
				String domain = "milkyway.ga";
				int portNumber1 = 1111;
				String ipAddress1 = "12.13.14.15";
				String ipAddress2 = "12.17.14.15";
				ServerData serverData1 = ServerDataTest.storeServerData(
						hostName1, domain, portNumber1, ipAddress1);
				int portNumber2 = 2222;
				String hostName2 = "redStar";
				ServerData serverData2 = ServerDataTest.storeServerData(
						hostName2, domain, portNumber2, ipAddress2);
				Result result = callAction(controllers.routes.ref.Application
						.showAllServerData());
				assertThat(contentType(result)).isEqualTo("text/html");

				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentAsString(result)).contains(hostName1);
				assertThat(contentAsString(result)).contains(domain);
				assertThat(contentAsString(result)).contains(
						String.valueOf(portNumber1));
				assertThat(contentAsString(result)).contains(ipAddress1);

				serverData1.delete();
				serverData2.delete();
			}
		});
	}
}