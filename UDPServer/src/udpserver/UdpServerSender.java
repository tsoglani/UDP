/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LabUser
 */
public class UdpServerSender extends Thread {

    String address;
    private DatagramSocket socket;
    private Hashtable<String, String> users;
    private String message;

    public UdpServerSender(Hashtable<String, String> users, String message,String address) {
        this.users = users;
        this.address=address;
        this.message = message;
        try {

            socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(UdpServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UdpServerSender(String address) {
        try {
            this.address = address;
            byte[] dataError = "wrong pass".getBytes();

            socket = new DatagramSocket();
            try {
                DatagramPacket packetError = new DatagramPacket(dataError, dataError.length, InetAddress.getByName(address), 5000);
                try {
                    socket.send(packetError);
                } catch (IOException ex) {
                    Logger.getLogger(UdpServerSender.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (UnknownHostException ex) {
                Logger.getLogger(UdpServerSender.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SocketException ex) {
            Logger.getLogger(UdpServerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        Iterator<String> iterator = users.keySet().iterator();
        while (iterator.hasNext()) {
           String address = iterator.next();
            String username = users.get(address);
            boolean sennder = true;
            DatagramPacket packet = null;
            try {
                String dataSendBack = username + "  success ";
                String data = username + "  said: " + message;
                byte[] dataToSend = data.getBytes();
                byte[] dataToSendBack = dataSendBack.getBytes();
                if (this.address.equals(address)) {

                    sennder = !sennder;
                    packet = new DatagramPacket(dataToSendBack, dataToSendBack.length, InetAddress.getByName(address), 5000);

                } else {
                    packet = new DatagramPacket(dataToSend, dataToSend.length, InetAddress.getByName(address), 5000);
                }
                socket.send(packet);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }



    }
}
