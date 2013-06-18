package tk.hintss.voiceconnect;

import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;

public class VoiceConnect extends JavaPlugin {
    
    private VoiceServerTypes type = VoiceServerTypes.UNKNOWN;
    private ServerQuery cachedresult;
    
    public void onEnable(){
        this.saveDefaultConfig();
        if (this.getConfig().getBoolean("auto-update")) {
            Updater updater = new Updater(this, "mumbleconnect", this.getFile(), Updater.UpdateType.DEFAULT, true);
        }
        if (!verifyConfig()) {
            getLogger().severe("invalid config, unloading plugin");
            getServer().getPluginManager().disablePlugin(this);
        }
        getCommand("voice").setExecutor(new VoiceCommand(this));
        getCommand("mumble").setExecutor(new VoiceCommand(this));
        getCommand("ts").setExecutor(new VoiceCommand(this));
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().warning("stats no werk :(");
        }
    }
    
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
    
    public Boolean verifyConfig() {
        if (!this.getConfig().getString("type").equalsIgnoreCase("mumble")) {
            if (!this.getConfig().getString("type").equalsIgnoreCase("ts3")) {
                return false;
            }
        }
        if (this.getConfig().getString("type").equalsIgnoreCase("mumble")) {
            type = VoiceServerTypes.MUMBLE;
        }
        if (this.getConfig().getString("type").equalsIgnoreCase("ts3")) {
            type = VoiceServerTypes.TS3;
        }
        return true;
    }
    
    public VoiceServerTypes getType() {
        return type;
    }
    
    public ServerQuery getCached() {
        return cachedresult;
    }
    
    public void setCached(ServerQuery result) {
        this.cachedresult = result;
    }
}
