package tk.hintss.voiceConnect;

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
        Integer[] status = {1,0,0};
        status = QueryServer.QueryServer(plugin.getType(), plugin.getConfig().getString("ip"), plugin.getConfig().getInt("port"), plugin.getConfig().getInt("queryport"), plugin.getConfig().getString("queryusername"), plugin.getConfig().getString("querypassword"));

        if (status[0] == 0) {
            if (status[1] == 0) {
                List<String> response = plugin.getConfig().getStringList("emptyresponse");
                for (String line : response) {
                    if (line != null) {
                        line = StringSubstitutions.SubstituteString(plugin, status, line);
                        sender.sendMessage(line);
                    }
                }
            } else if (status[1] >= status[2]) {
                List<String> response = plugin.getConfig().getStringList("fullresponse");
                for (String line : response) {
                    if (line != null) {
                        line = StringSubstitutions.SubstituteString(plugin, status, line);
                        sender.sendMessage(line);
                    }
                }
            } else {
                List<String> response = plugin.getConfig().getStringList("normalresponse");
                for (String line : response) {
                    if (line != null) {
                        line = StringSubstitutions.SubstituteString(plugin, status, line);
                        sender.sendMessage(line);
                    }
                }
            }
        } else if (status[0] == 1) {
            List<String> response = plugin.getConfig().getStringList("internalerrorresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin, status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status[0] == 2) {
            List<String> response = plugin.getConfig().getStringList("hostnotfoundresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin, status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status[0] == 3) {
            List<String> response = plugin.getConfig().getStringList("couldnotconnectresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin, status, line);
                    sender.sendMessage(line);
                }
            }
        } else if (status[0] == 4) {
            List<String> response = plugin.getConfig().getStringList("timeoutresponse");
            for (String line : response) {
                if (line != null) {
                    line = StringSubstitutions.SubstituteString(plugin, status, line);
                    sender.sendMessage(line);
                }
            }
        }
    }
}
