package tk.hintss.voiceconnect;

import static org.junit.Assert.*;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

public class StringSubstitutionsTest {
    @Test
    public void testSubstituteMumble() {
        ServerQuery mockStatus = PowerMockito.mock(ServerQuery.class);
        PowerMockito.when(mockStatus.getType()).thenReturn(VoiceServerTypes.MUMBLE);
        assertEquals("substitution of $type with \"Mumble\"", "Mumble", StringSubstitutions.SubstituteString(mockStatus, "$type"));
    }
    
    @Test
    public void testSubstituteTs3() {
        ServerQuery mockStatus = PowerMockito.mock(ServerQuery.class);
        PowerMockito.when(mockStatus.getType()).thenReturn(VoiceServerTypes.TS3);
        assertEquals("substitution of $type with \"Mumble\"", "TS3", StringSubstitutions.SubstituteString(mockStatus, "$type"));
    }
    
    @Test
    public void testSubstituteIp() {
        ServerQuery status = new ServerQuery();
        assertEquals("substitution of $ip with inputted ip", "localhost", StringSubstitutions.SubstituteString(status, "$ip"));
    }
    
    @Test
    public void testSubstitutePort() {
        ServerQuery status = new ServerQuery();
        assertEquals("substitution of $port with inputted port", "0", StringSubstitutions.SubstituteString(status, "$port"));
    }
    
    @Test
    public void testSubstituteUsers() {
        ServerQuery status = new ServerQuery();
        assertEquals("substitution of $users with user count", "0", StringSubstitutions.SubstituteString(status, "$users"));
    }
    
    @Test
    public void testSubstituteMaxUsers() {
        ServerQuery status = new ServerQuery();
        assertEquals("substitution of $max with max user count", "0", StringSubstitutions.SubstituteString(status, "$max"));
    }
}