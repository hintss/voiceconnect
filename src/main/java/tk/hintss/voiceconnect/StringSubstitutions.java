package tk.hintss.voiceconnect;

import org.bukkit.ChatColor;

public class StringSubstitutions {
    public static String SubstituteString(VoiceServerTypes type, String ip, int port, ServerQuery status, String line) {
        line = line.replaceAll("\\$users", String.valueOf(status.getCurrentUsers()));
        line = line.replaceAll("\\$max", String.valueOf(status.getMaxUsers()));
        if (type == VoiceServerTypes.MUMBLE) {
            line = line.replaceAll("\\$type", "Mumble");
        } else if (type == VoiceServerTypes.TS3) {
            line = line.replaceAll("\\$type", "TS3");
        } else {
            line = line.replaceAll("\\$type", "UNKNOWN");
        }
        line = line.replaceAll("\\$port", String.valueOf(port));
        line = line.replaceAll("\\$ip", ip);
        line = ChatColor.translateAlternateColorCodes("$".charAt(0), line);
        return line;
    }
}
