package tk.hintss.voiceconnect;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringSubstitutionsTest {
    @Test
    public void testSubstituteMumble() {
        ServerQuery status = new ServerQuery();
        String line = "$type";
        String expResult = "Mumble";
        String result = StringSubstitutions.SubstituteString(VoiceServerTypes.MUMBLE, "localhost", 80, status, line);
        assertEquals("substitution of $type with \"Mumble\"", expResult, result);
    }
    
    @Test
    public void testSubstituteTs3() {
        ServerQuery status = new ServerQuery();
        String line = "$type";
        String expResult = "TS3";
        String result = StringSubstitutions.SubstituteString(VoiceServerTypes.TS3, "localhost", 80, status, line);
        assertEquals("substitution of $type with \"TS3\"", expResult, result);
    }
    
    @Test
    public void testSubstituteIp() {
        ServerQuery status = new ServerQuery();
        String line = "$ip";
        String expResult = "localhost";
        String result = StringSubstitutions.SubstituteString(VoiceServerTypes.TS3, "localhost", 80, status, line);
        assertEquals("substitution of $ip with inputted ip", expResult, result);
    }
    
    @Test
    public void testSubstitutePort() {
        ServerQuery status = new ServerQuery();
        String line = "$port";
        String expResult = "80";
        String result = StringSubstitutions.SubstituteString(VoiceServerTypes.TS3, "localhost", 80, status, line);
        assertEquals("substitution of $port with inputted port", expResult, result);
    }
    
    @Test
    public void testSubstituteUsers() {
        ServerQuery status = new ServerQuery();
        String line = "$users";
        String expResult = "0";
        String result = StringSubstitutions.SubstituteString(VoiceServerTypes.TS3, "localhost", 80, status, line);
        assertEquals("substitution of $users with user count", expResult, result);
    }
    
    @Test
    public void testSubstituteMaxUsers() {
        ServerQuery status = new ServerQuery();
        String line = "$max";
        String expResult = "0";
        String result = StringSubstitutions.SubstituteString(VoiceServerTypes.TS3, "localhost", 80, status, line);
        assertEquals("substitution of $max with max user count", expResult, result);
    }
}