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
        ServerQuery status = new ServerQuery();
        status = new ServerQuery(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), plugin.getConfig().getInt("queryport"), plugin.getConfig().getString("queryusername"), plugin.getConfig().getString("querypassword"));

        plugin.setCached(status);
        
        List<String> response;
        
        if (status.getStatus() == VoiceServerStatuses.OK) {
            response = plugin.getConfig().getStringList("normalresponse");
        } else if (status.getStatus() == VoiceServerStatuses.EMPTY) {
            response = plugin.getConfig().getStringList("emptyresponse");
        } else if (status.getStatus() == VoiceServerStatuses.FULL) {
            response = plugin.getConfig().getStringList("fullresponse");
        } else if (status.getStatus() == VoiceServerStatuses.HOST_NOT_FOUND) {
            response = plugin.getConfig().getStringList("hostnotfoundresponse");
        } else if (status.getStatus() == VoiceServerStatuses.CONNECTION_REFUSED) {
            response = plugin.getConfig().getStringList("couldnotconnectresponse");
        } else if (status.getStatus() == VoiceServerStatuses.CONNECTION_TIMEOUT) {
            response = plugin.getConfig().getStringList("timeoutresponse");
        } else {
            response = plugin.getConfig().getStringList("internalerrorresponse");
        }
        
        for (String line : response) {
            if (line != null) {
                line = StringSubstitutions.SubstituteString(status, line);
                sender.sendMessage(line);
            }
        }
    }
}
