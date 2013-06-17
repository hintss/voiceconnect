package tk.hintss.voiceconnect;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;


public class CheckServerTask extends BukkitRunnable {
    private final VoiceConnect plugin;
    private final CommandSender sender;
    
    public CheckServerTask(CommandSender checker, VoiceConnect voiceconnect) {
        this.plugin = voiceconnect;
        this.sender = checker;
    }
    
    public void run() {
        ServerQuery status = new ServerQuery(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), plugin.getConfig().getInt("queryport"), plugin.getConfig().getString("queryusername"), plugin.getConfig().getString("querypassword"));

        if (status.getStatus() == VoiceServerStatuses.OK) {
            List<String> response = plugin.getConfig().getStringList("normalresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status.getStatus() == VoiceServerStatuses.EMPTY) {
            List<String> response = plugin.getConfig().getStringList("emptyresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status.getStatus() == VoiceServerStatuses.FULL) {
            List<String> response = plugin.getConfig().getStringList("fullresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status.getStatus() == VoiceServerStatuses.INTERNAL_ERROR) {
            List<String> response = plugin.getConfig().getStringList("internalerrorresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status.getStatus() == VoiceServerStatuses.HOST_NOT_FOUND) {
            List<String> response = plugin.getConfig().getStringList("hostnotfoundresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status.getStatus() == VoiceServerStatuses.CONNECTION_REFUSED) {
            List<String> response = plugin.getConfig().getStringList("couldnotconnectresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status.getStatus() == VoiceServerStatuses.CONNECTION_TIMEOUT) {
            List<String> response = plugin.getConfig().getStringList("timeoutresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), status, line);
                    sender.sendMessage(line);
                }
            }
        }
    }
}
