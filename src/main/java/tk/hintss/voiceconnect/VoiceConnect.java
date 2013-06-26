package tk.hintss.voiceconnect;

import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;

public class VoiceConnect extends JavaPlugin {
    
    private static VoiceConnect instance;
    private VoiceServerTypes type = VoiceServerTypes.UNKNOWN;
    private ServerQuery result = new ServerQuery();
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        if (getConfig().getBoolean("auto-update")) {
            new Updater(this, "mumbleconnect", getFile(), Updater.UpdateType.DEFAULT, true);
        }
        getCommand("voice").setExecutor(new VoiceCommand());
        getCommand("mumble").setExecutor(new VoiceCommand());
        getCommand("ts").setExecutor(new VoiceCommand());
        getCommand("teamspeak").setExecutor(new VoiceCommand());
        try {
            new Metrics(this).start();
        } catch (IOException ex) {
            getLogger().warning("Stats no werk! D:");
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
    
    public void loadConfig() {
        type = getConfig().getString("type").equalsIgnoreCase("mumble") ? VoiceServerTypes.MUMBLE : VoiceServerTypes.TS3;
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
