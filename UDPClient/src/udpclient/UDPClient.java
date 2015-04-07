/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclient;

/**
 *
 * @author LabUser
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClient {

    boolean nameFirts = true;
String msgs ;
    public boolean isNameFirts() {
        return nameFirts;
    }

    public void setNameFirts(boolean nameFirts) {
        this.nameFirts = nameFirts;
    }
    String name1 = "Signin ";
    String message = "Message :";
    private static InetAddress serverAddress;
    private static int serverPort = 10000;
    private DatagramSocket socket;

    public UDPClient() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void  receiveData() {

        UdpClientReciver receiver = new UdpClientReciver(this);

        receiver.start();
    }

    public void sendData() {

        System.out.println("wtire name");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {


            try {
               msgs = br.readLine();
                String data = null;

                if (nameFirts) {

                    data = name1 + msgs;



                } else if (!msgs.equals("sign out")) {
                    data = message + msgs;
                } else if (msgs.equals("sign out")) {
                    data = msgs;
                }

                byte[] dataToSend = data.getBytes();




                DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, serverAddress, serverPort);


                socket.send(packet);
                if (msgs.equals("sign out")) {
                   
nameFirts=!nameFirts;
br=null;
msgs=null;
break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: you forgot <server IP>");
            return;
        }

        String serverIP = args[0];

        try {
            serverAddress = InetAddress.getByName(serverIP);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        }

        UDPClient client = new UDPClient();
        client.receiveData();
        client.sendData();
       while (true){
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String msgs = br.readLine();
                if(msgs.equals("sign in")){
         br=null;
         msgs=null;
//        String data= "sing in";
//
//
//           byte[] dataToSend = data.getBytes();
//
//
//
//
//                DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, serverAddress, serverPort);
//
//
//                client.socket.send(packet);
         


        client.sendData();

}
            } catch (IOException ex) {
                Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
   
       }
    }
}
