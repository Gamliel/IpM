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
        		serverData.setHostname(hostName);
        		serverData.setDomain(domain);
        		serverData.setPort(portNumber);
        		serverData.setIpAddress(ipAddress);
        		serverData.save();
        		
        		assertThat(serverData.getId()).isNotNull();
        		assertThat(serverData.getHostname()).isEqualTo(hostName);
        		assertThat(serverData.getDomain()).isEqualTo(domain);
        		assertThat(serverData.getPort()).isEqualTo(portNumber);
        		assertThat(serverData.getIpAddress()).isEqualTo(ipAddress);
        		serverData.delete();
            }
        });
    }	
	
}
