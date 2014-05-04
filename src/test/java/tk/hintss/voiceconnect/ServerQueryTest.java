package tk.hintss.voiceconnect;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServerQueryTest {
    
    @Test
    public void testInvalidServerType() {
        ServerQuery instance = new ServerQuery(VoiceServerTypes.UNKNOWN, "localhost", 0, 0, "", "");
        assertEquals("feeding invalid server type into ServerQuery()", VoiceServerStatuses.INTERNAL_ERROR, instance.getStatus());
    }

    @Test
    public void testMumbleTimeout() {
        ServerQuery instance = new ServerQuery(VoiceServerTypes.MUMBLE, "localhost", 4, 0, "", "");
        assertEquals("testing lack of a mumble server to ping", VoiceServerStatuses.CONNECTION_TIMEOUT, instance.getStatus());
    }
    
    @Test
    public void testTs3ConnectionRefused() {
        // 4 is a unasigned port :P
        // I should probably use mock objects instead
        ServerQuery instance = new ServerQuery(VoiceServerTypes.TS3, "localhost", 4, 0, "", "");
        assertEquals("testing connection refused in TS3", VoiceServerStatuses.CONNECTION_REFUSED, instance.getStatus());
    }
    
    @Test
    public void testGetType() {
        ServerQuery instance = new ServerQuery();
        assertEquals("getStatus()", VoiceServerTypes.UNKNOWN, instance.getType());
    }
    
    @Test
    public void testGetIp() {
        ServerQuery instance = new ServerQuery();
        assertEquals("getStatus()", "localhost", instance.getIp());
    }
    
    @Test
    public void testGetPort() {
        ServerQuery instance = new ServerQuery();
        assertEquals("getStatus()", 0, instance.getPort());
    }
    
    @Test
    public void testGetStatus() {
        ServerQuery instance = new ServerQuery();
        assertEquals("getStatus()", VoiceServerStatuses.INTERNAL_ERROR, instance.getStatus());
    }

    @Test
    public void testGetCurrentUsers() {
        ServerQuery instance = new ServerQuery();
        assertEquals("getMaxusers()", 0, instance.getCurrentUsers());
    }
    
    @Test
    public void testGetMaxUsers() {
        ServerQuery instance = new ServerQuery();
        assertEquals("getMaxusers()", 0, instance.getMaxUsers());
    }

    @Test
    public void testGetResultTime() {
        ServerQuery instance = new ServerQuery();
        assertTrue("getResultTime() and setting resulttime in ServerQuery()", System.currentTimeMillis() < instance.getResultTime() + 5);
    }
}
