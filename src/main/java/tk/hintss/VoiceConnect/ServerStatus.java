package tk.hintss.VoiceConnect;

public class ServerStatus {
    private VoiceServerStatuses status;
    private int currentusers;
    private int maxusers;
    
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
