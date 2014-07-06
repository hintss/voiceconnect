package tk.hintss.voiceconnect;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class VoiceConnect extends JavaPlugin {
    
    private static VoiceConnect instance;
    private VoiceServerTypes type = VoiceServerTypes.UNKNOWN;
    private ServerQuery result;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadConfig();

        if (getConfig().getBoolean("auto-update")) {
            new Updater(this, 57725, getFile(), Updater.UpdateType.DEFAULT, true);
        }

        getCommand("voice").setExecutor(new VoiceCommand());
        getCommand("mumble").setExecutor(new VoiceCommand());
        getCommand("ts").setExecutor(new VoiceCommand());
        getCommand("teamspeak").setExecutor(new VoiceCommand());

        if (getConfig().getBoolean("stats")) {
            try {
                new Metrics(this).start();
            } catch (IOException ex) {
                getLogger().warning("Stats no werk! D:");
            }
        }
    }
    
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        instance = null;
    }
    
    public void loadConfig() {
        reloadConfig();
        type = VoiceServerTypes.valueOf(getConfig().getString("type").toUpperCase());
        result = null;
    }
    
    public VoiceServerTypes getType() {
        return type;
    }
    
    public synchronized ServerQuery getCached() {
        return result;
    }
    
    public synchronized void setCached(ServerQuery result) {
        this.result = result;
    }
    
    public static VoiceConnect getInstance() {
        return instance;
    }
}
