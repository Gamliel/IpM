package models;

import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class ServerDataTest {
	
    @Test
    public void storeAllData() {
        running(fakeApplication(), new Runnable() {
            public void run() {
        		ServerData serverData = new ServerData();
        		String hostName = "localhost";
        		String domain = "milyway.ga";
        		int portNumber = 7645;
        		String ipAddress = "12.3.4.5";
        		serverData.hostname = hostName;
        		serverData.domain = domain;
        		serverData.port = portNumber;
        		serverData.ipAddress = ipAddress;
        		serverData.save();
        		
        		assertThat(serverData.Id).isNotNull();
        		assertThat(serverData.hostname).isEqualTo(hostName);
        		assertThat(serverData.domain).isEqualTo(domain);
        		assertThat(serverData.port).isEqualTo(portNumber);
        		assertThat(serverData.ipAddress).isEqualTo(ipAddress);
        		serverData.delete();
            }
        });
    }	
	
}
