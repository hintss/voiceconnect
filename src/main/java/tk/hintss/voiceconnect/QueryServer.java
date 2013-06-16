// really really REALLY horrible thing that gets mumble/ts3 current/max user
// counts using the most horribly thought out methods possible

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

public class QueryServer {
    public static ServerStatus QueryServer(VoiceServerTypes type, String serverIP, Integer clientport, Integer queryport, String username, String password) {
        ServerStatus status;
        int currentusers = 0;
        int maxusers = 0;
        
        if (type == VoiceServerTypes.MUMBLE) {
            DatagramSocket clientsocket = null;
            try {
                clientsocket = new DatagramSocket();
                clientsocket.setSoTimeout(5000);
                
                InetAddress ipaddress = InetAddress.getByName(serverIP);
                
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
                
                currentusers = data.readInt();
                maxusers = data.readInt();
                
                status = new ServerStatus(VoiceServerStatuses.OK, currentusers, maxusers);
            } catch (SocketTimeoutException ex) {
                status = new ServerStatus(VoiceServerStatuses.CONNECTION_TIMEOUT);
            } catch (SocketException ex) {
                status = new ServerStatus(VoiceServerStatuses.INTERNAL_ERROR);
            } catch (UnknownHostException ex) {
                status = new ServerStatus(VoiceServerStatuses.HOST_NOT_FOUND);
            } catch (IOException ex) {
                status = new ServerStatus(VoiceServerStatuses.CONNECTION_REFUSED);
            }
            return status;
        } else if (type == VoiceServerTypes.TS3) {
            try {
                InetSocketAddress address = new InetSocketAddress(serverIP, queryport);
                
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
                                    currentusers = java.lang.Integer.parseInt(param.substring(28));
                                }
                                if (param.startsWith("virtualserver_maxclients=")) {
                                    maxusers = java.lang.Integer.parseInt(param.substring(25));
                                    status = new ServerStatus(VoiceServerStatuses.OK, currentusers, maxusers);
                                    ts3socket.close();
                                    return status;
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                status = new ServerStatus(VoiceServerStatuses.CONNECTION_REFUSED);
                return status;
            }
            
        } else {
            status = new ServerStatus(VoiceServerStatuses.INTERNAL_ERROR);
            return status;
        }
    }
}
