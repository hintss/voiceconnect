package tk.hintss.voiceconnect;

import org.bukkit.ChatColor;

public class StringSubstitutions {
    public static String SubstituteString(VoiceConnect plugin, ServerStatus status, String line) {
        line = line.replaceAll("\\$users", String.valueOf(status.getCurrentUsers()));
        line = line.replaceAll("\\$max", String.valueOf(status.getMaxUsers()));
        if (plugin.getType() == VoiceServerTypes.MUMBLE) {
            line = line.replaceAll("\\$type", "Mumble");
        } else if (plugin.getType() == VoiceServerTypes.TS3) {
            line = line.replaceAll("\\$type", "TS3");
        }
        line = line.replaceAll("\\$port", plugin.getConfig().getString("port"));
        line = line.replaceAll("\\$ip", plugin.getConfig().getString("ip"));
        line = ChatColor.translateAlternateColorCodes("$".charAt(0), line);
        return line;
    }
}
