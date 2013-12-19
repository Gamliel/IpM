package models;

import static org.fest.assertions.Assertions.assertThat;
import static play.libs.Json.toJson;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import play.Logger;
import play.libs.F.Callback;
import play.libs.Yaml;
import play.test.TestBrowser;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerDataTest {
	
	public static ServerData storeServerData(
			String conventionalName, String hostName, String domain,
			int portNumber, String ipAddress) {
		ServerData serverData = new ServerData();
		serverData.conventionalName = conventionalName;
		serverData.hostName = hostName;
		serverData.domain = domain;
		serverData.port = portNumber;
		serverData.ipAddress = ipAddress;
		serverData.save();
		return serverData;
	}

	@Test
    public void storeAllData() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	String conventionalName = "Stage";
        		String hostName = "localhost";
        		String domain = "milyway.ga";
        		int portNumber = 7645;
        		String ipAddress = "12.3.4.5";
        		ServerData serverData = storeServerData(conventionalName, hostName, domain,
						portNumber, ipAddress);
        		
        		assertThat(serverData.id).isNotNull();
        		assertThat(serverData.hostName).isEqualTo(hostName);
        		assertThat(serverData.domain).isEqualTo(domain);
        		assertThat(serverData.port).isEqualTo(portNumber);
        		assertThat(serverData.ipAddress).isEqualTo(ipAddress);
        		
        		serverData.delete();
            }
        });
    }	
    @Test
    public void iCanAddServerDataThroughYamlAndEbeanAndTheyGetSaved() {
        running(testServer(9000, fakeApplication(inMemoryDatabase())), HTMLUNIT , new Callback<TestBrowser>() {
            @SuppressWarnings("unchecked")
			public void invoke(TestBrowser browser) throws JsonParseException, JsonMappingException, IOException {
				Map<String,List<ServerData>> all = (Map<String,List<ServerData>>)Yaml.load("initial-serverData.yml");

            	List<ServerData> serversDataList = all.get("serversData");
				Ebean.save(serversDataList);
				
				for (int i = 0; i < serversDataList.size(); i++){
					ServerData curServerData = serversDataList.get(i);
					JsonNode serverDataJson = toJson(curServerData);
					HashMap<String, Object> serverDataJsonMap = new ObjectMapper().readValue(serverDataJson.traverse(), HashMap.class);
					assertThat(!serverDataJsonMap.isEmpty());
					ServerData serverData = ServerData.find.where().allEq(serverDataJsonMap).findUnique();
					assertThat(serverData).isNotNull();
					assertThat(serverData.id.equals(curServerData.id)).isTrue();
				}
           }
        });
    }	
}