// really really REALLY horrible thing that gets mumble/ts3 current/max user
// counts using the most horribly thought out methods possible

package tk.hintss.VoiceConnect;

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
    public static Integer[] QueryServer(VoiceServerTypes type, String serverIP, Integer clientport, Integer queryport, String username, String password) {
        
        // { error, users, max}
        // 0 = works, 1=internal error, 2=dns, 3=can't connect, 4=timeout
        Integer[] response = {1, 0, 0};
        
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
                response[0] = 0;
                response[1] = data.readInt();
                response[2] = data.readInt();
            } catch (SocketTimeoutException ex) {
                response[0] = 4;
            } catch (SocketException ex) {
                response[0] = 1;
            } catch (UnknownHostException ex) {
                response[0] = 2;
            } catch (IOException ex) {
                response[0] = 3;
            }
            return response;
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
                                    response[1] = java.lang.Integer.parseInt(param.substring(28));
                                }
                                if (param.startsWith("virtualserver_maxclients=")) {
                                    response[2] = java.lang.Integer.parseInt(param.substring(25));
                                    response[0] = 0;
                                    ts3socket.close();
                                    return response;
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                response[0] = 3;
                return response;
            }
            
        } else {
            response[0] = 1;
            return response;
        }
    }
}
