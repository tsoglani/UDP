/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

/**
 *
 * @author LabUser
 */
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;

public class UDPServer {

    private int serverPort = 10000;
    private DatagramSocket socket;
    private Hashtable<String, String> users;
    boolean swstoPass = true;

    public UDPServer() {
        try {
            socket = new DatagramSocket(serverPort);
            users = new Hashtable<String, String>();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void receiveData() {
        String m = null;
        byte[] data = new byte[2000];

        System.out.println("Waiting...");

        while (true) {

            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                socket.receive(packet);


                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                BufferedReader reader = new BufferedReader(new InputStreamReader(bais));

                String address = packet.getAddress().getHostAddress();
                String message = reader.readLine();

//
//                if (message.startsWith("Signin")) {
//                    swstoPass = false;
//                }
//                ;
//
//
//                if (message.startsWith("Signin epp") || message.startsWith("Signin nikos") && swstoPass == false) {
//                    System.out.println("wellcome niko");
//                    swstoPass = !swstoPass;
//
//
//
//
//                } else if (swstoPass == false) {
//                    System.out.println(address + "wrong pass");
//
//                    UdpServerSender sender = new UdpServerSender(address);
//
//                }

                if (swstoPass) {



                    if (message.startsWith("Signin")) {

                        m = message.replace("Signin", "");
                        
                        System.out.println("username " + m);
                        users.put(address, m);
                        System.out.println(users);
                        //users.put(address, m);
                    } else if (message.startsWith("Message")) {
                        UdpServerSender sender = new UdpServerSender(users, message, address);

                        System.out.println(message);
                    } else if (message.startsWith("sign out")) {

                        System.out.println(m + "  is  out");
                        users.remove(address);
                        System.out.println(users);
                        UdpServerSender sender = new UdpServerSender(users, message, address);

                    }





                    UdpServerSender sender = new UdpServerSender(users, m + "  is  out", address);
                    sender.start();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        UDPServer server = new UDPServer();
        server.receiveData();
    }
}
