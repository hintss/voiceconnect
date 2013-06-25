package tk.hintss.voiceconnect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    new CheckServerTask(sender, VoiceConnect.getInstance()).runTaskAsynchronously(VoiceConnect.getInstance());
                } else {
                	sender.sendMessage(ChatColor.RED + "[VoiceConnect] You do not have permission to use this!");
                }
            }
            return true;
        }
        return false;
    }
}
