package tk.hintss.voiceconnect;

import static org.junit.Assert.*;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

public class StringSubstitutionsTest {
    @Test
    public void testSubstituteMumble() {
        ServerQuery mockStatus = PowerMockito.mock(ServerQuery.class);
        PowerMockito.when(mockStatus.getType()).thenReturn(VoiceServerTypes.MUMBLE);
        
        String expResult = "Mumble";
        String result = StringSubstitutions.SubstituteString(mockStatus, "$type");
        assertEquals("substitution of $type with \"Mumble\"", expResult, result);
    }
    
    @Test
    public void testSubstituteTs3() {
        ServerQuery mockStatus = PowerMockito.mock(ServerQuery.class);
        PowerMockito.when(mockStatus.getType()).thenReturn(VoiceServerTypes.TS3);
        
        String expResult = "TS3";
        String result = StringSubstitutions.SubstituteString(mockStatus, "$type");
        assertEquals("substitution of $type with \"Mumble\"", expResult, result);
    }
    
    @Test
    public void testSubstituteIp() {
        ServerQuery status = new ServerQuery();
        String expResult = "localhost";
        String result = StringSubstitutions.SubstituteString(status, "$ip");
        assertEquals("substitution of $ip with inputted ip", expResult, result);
    }
    
    @Test
    public void testSubstitutePort() {
        ServerQuery status = new ServerQuery();
        String expResult = "80";
        String result = StringSubstitutions.SubstituteString(status, "$port");
        assertEquals("substitution of $port with inputted port", expResult, result);
    }
    
    @Test
    public void testSubstituteUsers() {
        ServerQuery status = new ServerQuery();
        String expResult = "0";
        String result = StringSubstitutions.SubstituteString(status, "$users");
        assertEquals("substitution of $users with user count", expResult, result);
    }
    
    @Test
    public void testSubstituteMaxUsers() {
        ServerQuery status = new ServerQuery();
        String expResult = "0";
        String result = StringSubstitutions.SubstituteString(status, "$max");
        assertEquals("substitution of $max with max user count", expResult, result);
    }
}