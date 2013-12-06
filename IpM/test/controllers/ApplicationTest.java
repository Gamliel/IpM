package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
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
        assertThat(contentAsString(result).contains("<title>Here the current server data</title>"));
    }
    
    @Test
	public void showData() throws Exception {
    }

	@Test
    public void storeAllData() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	ServerData serverData1 = ServerDataTest.storeServerData("greenStar", "milkyway.ga", 1111, "12.13.14.15");
            	ServerData serverData2 = ServerDataTest.storeServerData("redStar", "milkyway.ga", 1111, "12.13.14.15");
            	Result result = callAction(controllers.routes.ref.Application.showAllServerData());
            	assertThat(status(result)).isEqualTo(OK);
            	serverData1.delete();
            	serverData2.delete();
            }
        });
    }	
}