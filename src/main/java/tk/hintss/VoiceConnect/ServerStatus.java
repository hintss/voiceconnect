package tk.hintss.voiceconnect;

public class ServerStatus {
    private VoiceServerStatuses status;
    private int currentusers = 0;
    private int maxusers = 0;
    
    public ServerStatus(VoiceServerStatuses status, int currentusers, int maxusers) {
        this.status = status;
        this.currentusers = currentusers;
        this.maxusers = maxusers;
        
        if (status == VoiceServerStatuses.OK && currentusers == 0) {
            this.status = VoiceServerStatuses.EMPTY;
        }
        
        if (status == VoiceServerStatuses.OK && currentusers >= maxusers) {
            this.status = VoiceServerStatuses.FULL;
        }
    }
    
    public ServerStatus(VoiceServerStatuses status) {
        this.status = status;
    }
    
    public VoiceServerStatuses getStatus() {
        return status;
    }
    
    public int getCurrentUsers() {
        return currentusers;
    }
    
    public int getMaxUsers() {
        return maxusers;
    }
}
