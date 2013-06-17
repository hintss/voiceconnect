package tk.hintss.voiceconnect;

import org.bukkit.ChatColor;

public class StringSubstitutions {
    public static String SubstituteString(ServerQuery status, String line) {
        line = line.replaceAll("\\$users", String.valueOf(status.getCurrentUsers()));
        line = line.replaceAll("\\$max", String.valueOf(status.getMaxUsers()));
        if (status.getType() == VoiceServerTypes.MUMBLE) {
            line = line.replaceAll("\\$type", "Mumble");
        } else if (status.getType() == VoiceServerTypes.TS3) {
            line = line.replaceAll("\\$type", "TS3");
        } else {
            line = line.replaceAll("\\$type", "UNKNOWN");
        }
        line = line.replaceAll("\\$port", String.valueOf(status.getPort()));
        line = line.replaceAll("\\$ip", status.getIp());
        line = ChatColor.translateAlternateColorCodes("$".charAt(0), line);
        return line;
    }
}
