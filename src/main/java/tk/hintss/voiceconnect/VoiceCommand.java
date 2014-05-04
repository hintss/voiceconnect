package tk.hintss.voiceconnect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class VoiceCommand implements CommandExecutor {
 
    public VoiceCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mumble") || cmd.getName().equalsIgnoreCase("voice") || cmd.getName().equalsIgnoreCase("ts") || cmd.getName().equalsIgnoreCase("teamspeak")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("voiceconnect.reload") || !(sender instanceof Player)) {
                        VoiceConnect.getInstance().reloadConfig();
                        VoiceConnect.getInstance().loadConfig();
                        sender.sendMessage(ChatColor.GREEN + "[VoiceConnect] Config reloaded!");
                    } else {
                    sender.sendMessage(ChatColor.RED + "[VoiceConnect] You do not have permission to reload the config!");
                    }
                }
            } else {
                if (sender.hasPermission("voiceconnect.use") || !(sender instanceof Player)) {
                    ServerQuery status = new ServerQuery();
                    if (VoiceConnect.getInstance().getCached().getResultTime() + VoiceConnect.getInstance().getConfig().getInt("cachetime") < System.currentTimeMillis()) {
                        sender.sendMessage(ChatColor.YELLOW + "[VoiceConnect] querying " + VoiceConnect.getInstance().getConfig().getString("type") + " server...");
                        new CheckServerTask(sender, VoiceConnect.getInstance()).runTaskAsynchronously(VoiceConnect.getInstance());
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "[VoiceConnect] using cached response from " + String.valueOf((System.currentTimeMillis() - VoiceConnect.getInstance().getCached().getResultTime())/1000) + " seconds ago.");
                        status = VoiceConnect.getInstance().getCached();
                        
                        List<String> response;
                        
                        if (status.getStatus() == VoiceServerStatuses.OK) {
                            response = VoiceConnect.getInstance().getConfig().getStringList("normalresponse");
                        } else if (status.getStatus() == VoiceServerStatuses.EMPTY) {
                            response = VoiceConnect.getInstance().getConfig().getStringList("emptyresponse");
                        } else if (status.getStatus() == VoiceServerStatuses.FULL) {
                            response = VoiceConnect.getInstance().getConfig().getStringList("fullresponse");
                        } else if (status.getStatus() == VoiceServerStatuses.CONNECTION_REFUSED) {
                            response = VoiceConnect.getInstance().getConfig().getStringList("couldnotconnectresponse");
                        } else if (status.getStatus() == VoiceServerStatuses.CONNECTION_TIMEOUT) {
                            response = VoiceConnect.getInstance().getConfig().getStringList("timeoutresponse");
                        } else {
                            response = VoiceConnect.getInstance().getConfig().getStringList("internalerrorresponse");
                        }
                        
                        for (String line : response) {
                            if (line != null) {
                                line = StringSubstitutions.SubstituteString(status, line);
                                sender.sendMessage(line);
                            }
                        }
                    }
                } else {
                	sender.sendMessage(ChatColor.RED + "[VoiceConnect] You do not have permission to use this!");
                }
            }
            return true;
        }
        return false;
    }
}
