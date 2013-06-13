package tk.hintss.voiceConnect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class VoiceCommand implements CommandExecutor {
    private VoiceConnect plugin;
 
    public VoiceCommand(VoiceConnect plugin) {
            this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mumble") || cmd.getName().equalsIgnoreCase("voice") || cmd.getName().equalsIgnoreCase("ts")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("voiceconnect.reload") || !(sender instanceof Player)) {
                        plugin.reloadConfig();
                        if (plugin.verifyConfig()) {
                            sender.sendMessage(ChatColor.GREEN + "[VoiceConnect] Config reloaded!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Config invalid.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "[VoiceConnect] You do not have permission to reload the config!");
                    }
                }
            } else {
                if (sender.hasPermission("voiceconnect.use") || !(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.YELLOW + "[VoiceConnect] querying " + plugin.getConfig().getString("type") + " server...");
                    BukkitTask CommandTask = new CheckServerTask(sender, plugin).runTaskAsynchronously(plugin);
                }
            }
            return true;
        }
        return false;
    }
}
