package models;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import org.junit.Test;

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
        		
        		assertThat(serverData.Id).isNotNull();
        		assertThat(serverData.hostName).isEqualTo(hostName);
        		assertThat(serverData.domain).isEqualTo(domain);
        		assertThat(serverData.port).isEqualTo(portNumber);
        		assertThat(serverData.ipAddress).isEqualTo(ipAddress);
        		
        		serverData.delete();
            }
        });
    }	
	
}
