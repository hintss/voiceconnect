package tk.hintss.VoiceConnect;

import org.bukkit.ChatColor;

public class StringSubstitutions {
    public static String SubstituteString(VoiceConnect plugin, Integer[] status, String line) {
        line = line.replaceAll("\\$users", String.valueOf(status[1]));
        line = line.replaceAll("\\$max", String.valueOf(status[2]));
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
