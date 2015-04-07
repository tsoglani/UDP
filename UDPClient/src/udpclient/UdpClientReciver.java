/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LabUser
 */
public class UdpClientReciver extends Thread {

    private DatagramSocket socket;
    private int clientPort = 5000;
    UDPClient udp;

    public UdpClientReciver(UDPClient udp) {
        try {
            this.udp = udp;
            synchronized (this) {
                socket = new DatagramSocket(clientPort);
            }
        } catch (SocketException ex) {
            Logger.getLogger(UdpClientReciver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        byte[] data = new byte[2000];

        while (true) {
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);

                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
                String arrive = reader.readLine();
                System.out.println(packet.getAddress().getHostAddress() + " : " + arrive);
                System.out.println("waiting...");
                if (!arrive.equals("wrong pass")) {
                    udp.setNameFirts(false);
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
