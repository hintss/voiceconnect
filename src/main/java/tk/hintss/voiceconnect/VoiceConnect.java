package tk.hintss.voiceconnect;

import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;

public class VoiceConnect extends JavaPlugin {
    
    private static VoiceConnect instance;
    private VoiceServerTypes type = VoiceServerTypes.UNKNOWN;
    private ServerQuery result;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        if (getConfig().getBoolean("auto-update")) {
            new Updater("mumbleconnect", getFile(), Updater.UpdateType.DEFAULT, true);
        }
        if (!loadConfig()) {
            getLogger().severe("Invalid configuration, disabling plugin! D:");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("voice").setExecutor(new VoiceCommand());
        getCommand("mumble").setExecutor(new VoiceCommand());
        getCommand("ts").setExecutor(new VoiceCommand());
        getCommand("teamspeak").setExecutor(new VoiceCommand());
        try {
            new Metrics(this).start();
        } catch (IOException ex) {
            getLogger().warning("Failed to start MCStats! :(");
        }
    }
    
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        instance = null;
    }
    
    public void setCached(ServerQuery result) {
        this.result = result;
    }
    
    private boolean loadConfig() {
        if (!getConfig().getString("type").equalsIgnoreCase("mumble") && !getConfig().getString("type").equalsIgnoreCase("ts3")) {
            return false;
        }
        type = getConfig().getString("type").equalsIgnoreCase("mumble") ? VoiceServerTypes.MUMBLE : VoiceServerTypes.TS3;w
        return true;
    }
    
    public VoiceServerTypes getType() {
        return type;
    }
    
    public ServerQuery getCached() {
        return result;
    }
    
    public static VoiceConnect getInstance() {
        return instance;
    }
}
