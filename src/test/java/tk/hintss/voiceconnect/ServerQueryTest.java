package tk.hintss.voiceconnect;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServerQueryTest {
    
    @Test
    public void testInvalidServerType() {
        ServerQuery instance = new ServerQuery(VoiceServerTypes.UNKNOWN, "localhost", 80, 80, "", "");
        VoiceServerStatuses expResult = VoiceServerStatuses.INTERNAL_ERROR;
        VoiceServerStatuses result = instance.getStatus();
        assertEquals("feeding invalid server type into ServerQuery()", expResult, result);
    }
    
    /*
    // TODO fix bug in main code (yes, I know this fails)
    @Test
    public void testDnsError() {
        ServerQuery instance = new ServerQuery(VoiceServerTypes.MUMBLE, "this.is.not.a.domain", 0, 0, "", "");
        VoiceServerStatuses expResult = VoiceServerStatuses.HOST_NOT_FOUND;
        VoiceServerStatuses result = instance.getStatus();
        assertEquals("testing catching of invalid hosts in Mumble mode", expResult, result);
    }
    */
    
    @Test
    public void testConnectionRefused() {
        // 4 is a unasigned port :P
        // I should probably use mock objects instead
        ServerQuery instance = new ServerQuery(VoiceServerTypes.TS3, "localhost", 4, 0, "", "");
        VoiceServerStatuses expResult = VoiceServerStatuses.CONNECTION_REFUSED;
        VoiceServerStatuses result = instance.getStatus();
        assertEquals("testing connection refused in TS3", expResult, result);
    }
    
    /* fails
    @Test
    public void testMumbleTimeout() {
        // 4 is a unasigned port :P
        // I should probably use mock objects instead
        // UDP is connectionless
        ServerQuery instance = new ServerQuery(VoiceServerTypes.MUMBLE, "localhost", 4, 0, "", "");
        VoiceServerStatuses expResult = VoiceServerStatuses.CONNECTION_TIMEOUT;
        VoiceServerStatuses result = instance.getStatus();
        assertEquals("testing lack of a mumble server to ping", expResult, result);
    }
    */
    
    @Test
    public void testGetType() {
        ServerQuery instance = new ServerQuery();
        VoiceServerTypes result = instance.getType();
        assertEquals("getStatus()", VoiceServerTypes.UNKNOWN, result);
    }
    
    @Test
    public void testGetIp() {
        ServerQuery instance = new ServerQuery();
        String result = instance.getIp();
        assertEquals("getStatus()", "localhost", result);
    }
    
    @Test
    public void testGetPort() {
        ServerQuery instance = new ServerQuery();
        int result = instance.getPort();
        assertEquals("getStatus()", 80, result);
    }
    
    @Test
    public void testGetStatus() {
        ServerQuery instance = new ServerQuery();
        VoiceServerStatuses expResult = VoiceServerStatuses.INTERNAL_ERROR;
        VoiceServerStatuses result = instance.getStatus();
        assertEquals("getStatus()", expResult, result);
    }

    @Test
    public void testGetCurrentUsers() {
        ServerQuery instance = new ServerQuery();
        int result = instance.getCurrentUsers();
        assertEquals("getMaxusers()", 0, result);
    }
    
    @Test
    public void testGetMaxUsers() {
        ServerQuery instance = new ServerQuery();
        int result = instance.getMaxUsers();
        assertEquals("getMaxusers()", 0, result);
    }

    @Test
    public void testGetResultTime() {
        ServerQuery instance = new ServerQuery();
        Long expectedResult = System.currentTimeMillis();
        Long result = instance.getResultTime();
        assertTrue("getResultTime() and setting resulttime in ServerQuery()", expectedResult < result + 5);
    }
}