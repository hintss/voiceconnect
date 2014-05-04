// it's bad, deal with it

package tk.hintss.voiceconnect;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ServerQuery {
    private VoiceServerTypes type = VoiceServerTypes.UNKNOWN;
    private String ip = "localhost";
    private int clientport = 0;
    
    private VoiceServerStatuses status = VoiceServerStatuses.INTERNAL_ERROR;
    private int currentusers = 0;
    private int maxusers = 0;
    private Long resulttime = 0L;
    
    public ServerQuery() {
        this.resulttime = System.currentTimeMillis();
    }
    
    public ServerQuery(VoiceServerTypes type, String ip, int clientport, int queryport, String username, String password) {
        this.type = type;
        this.ip = ip;
        this.clientport = clientport;
        
        if (type == VoiceServerTypes.MUMBLE) {
            try {
                DatagramSocket clientsocket = new DatagramSocket();
                clientsocket.setSoTimeout(5000);
                
                InetAddress ipaddress = InetAddress.getByName(ip);
                
                byte[] sendData = "\000\000\000\000\000\000\000\000\000\000\000\000".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipaddress, clientport);
                clientsocket.send(sendPacket);
                
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientsocket.receive(receivePacket);
                clientsocket.close();
                
                ByteArrayInputStream packets = new ByteArrayInputStream(receivePacket.getData());
                DataInputStream data = new DataInputStream(packets);
                
                data.readInt();
                data.readLong();
                
                this.currentusers = data.readInt();
                this.maxusers = data.readInt();
                
                this.status = VoiceServerStatuses.OK;
                if (this.currentusers == 0) {
                    this.status = VoiceServerStatuses.EMPTY;
                }
                if (this.currentusers >= this.maxusers) {
                    this.status = VoiceServerStatuses.FULL;
                }
            } catch (SocketTimeoutException ex) {
                this.status = VoiceServerStatuses.CONNECTION_TIMEOUT;
            } catch (UnknownHostException ex) {
                this.status = VoiceServerStatuses.CONNECTION_REFUSED;
            } catch (SocketException ex) {
                this.status = VoiceServerStatuses.INTERNAL_ERROR;
            } catch (IOException ex) {
                this.status = VoiceServerStatuses.CONNECTION_REFUSED;
            }
            this.resulttime = System.currentTimeMillis();
        } else if (type == VoiceServerTypes.TS3) {
            try {
                InetSocketAddress address = new InetSocketAddress(ip, queryport);
                
                Socket ts3socket = new Socket();
                ts3socket.connect(address, 5000);
                ts3socket.setSoTimeout(5000);
                
                InputStreamReader inputStreamReader = new InputStreamReader(ts3socket.getInputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ts3socket.getOutputStream());
                
                BufferedReader breader = new BufferedReader(inputStreamReader);
                BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
                
                bwriter.write("login client_login_name=" + username + " client_login_password=" + password + "\r\n");
                bwriter.write("serverlist\r\n");
                bwriter.flush();
                
                while (true) {
                    String reply = breader.readLine();
                    
                    String[] params = reply.split(" ");
                    if (params.length > 1) {
                        if (params[1].contains("virtualserver_port=" + String.valueOf(clientport))) {
                            for (String param : params) {
                                if (param.startsWith("virtualserver_clientsonline=")) {
                                    this.currentusers = java.lang.Integer.parseInt(param.substring(28));
                                }
                                if (param.startsWith("virtualserver_maxclients=")) {
                                    this.maxusers = java.lang.Integer.parseInt(param.substring(25));
                                    this.status = VoiceServerStatuses.OK;
                                    this.resulttime = System.currentTimeMillis();
                                    ts3socket.close();
                                    if (this.currentusers == 0) {
                                        this.status = VoiceServerStatuses.EMPTY;
                                    }
                                    if (this.currentusers >= this.maxusers) {
                                        this.status = VoiceServerStatuses.FULL;
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (UnknownHostException ex) {
                this.status = VoiceServerStatuses.CONNECTION_REFUSED;
            } catch (IOException ex) {
                this.status = VoiceServerStatuses.CONNECTION_REFUSED;
            }
            this.resulttime = System.currentTimeMillis();
            
        } else {
            this.status = VoiceServerStatuses.INTERNAL_ERROR;
            this.resulttime = System.currentTimeMillis();
        }
    }
    
    public VoiceServerTypes getType() {
        return type;
    }
    
    public String getIp() {
        return ip;
    }
    
    public int getPort() {
        return clientport;
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
    
    public Long getResultTime() {
        return resulttime;
    }
}
